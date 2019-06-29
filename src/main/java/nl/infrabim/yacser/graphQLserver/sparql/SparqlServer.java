package nl.infrabim.yacser.graphQLserver.sparql;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import org.apache.jena.fuseki.embedded.FusekiServer;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SparqlServer {
	private static final Logger LOGGER = Logger.getLogger(SparqlServer.class.getName());
	public static final String QUERY_URL = "http://localhost:3330/rdf/query";
	public static final String UPDATE_URL = "http://localhost:3330/rdf/update";
	public static final String YACSER_URI = "http://www.infrabim.nl/coins/bs/yacser";
	private static final String YACSER_PATH = "src/main/resources/static/yacser.ttl";
	public static final String BS_URI = "https://w3id.org/def/basicsemantics";
	private static final String BS_PATH = "src/main/resources/static/basicsemantics.ttl";
	public static final String DBC_URI = "http://purl.org/dc/elements/1.1/";	
	public static final String SKOS_URI = "http://www.w3.org/2004/02/skos/core";

	public static SparqlServer instance;
	public static FusekiServer sparql;
	private static Dataset ds;
	private static PrefixMapping prefixMapping;

	public SparqlServer() throws IOException {
		instance = this;
		startServer();
	}

	private void startServer() throws IOException {
		ds = DatasetFactory.create();
		addNamedModel(YACSER_URI, YACSER_PATH);
		addNamedModel(BS_URI, BS_PATH);
		sparql = FusekiServer.create().add("/rdf", ds, true).build();
		sparql.start();
	}

	public static PrefixMapping getPrefixMapping() {
		if (prefixMapping == null) {
			prefixMapping = PrefixMapping.Factory.create();
			prefixMapping.setNsPrefix("rdf", RDF.uri);
			prefixMapping.setNsPrefix("rdfs", RDFS.uri);
			prefixMapping.setNsPrefix("owl", OWL.NS);
			prefixMapping.setNsPrefix("xml", "http://www.w3.org/XML/1998/namespace/");
			prefixMapping.setNsPrefix("yacser", YACSER_URI + '#');
			prefixMapping.setNsPrefix("bs", BS_URI + '#');
			prefixMapping.setNsPrefix("skos", SKOS_URI + '#');
			prefixMapping.setNsPrefix("db", DBC_URI);
			prefixMapping.setNsPrefix("expr", "https://w3id.org/express#");
			prefixMapping.setNsPrefix("usr", "http://www.infrabim.nl/bimbots-psd-repository/users#");
		}
		return prefixMapping;
	}

	public void addNamedModel(String uri, String filePath) throws IOException {
		FileSystemResource fileResource = new FileSystemResource(filePath);
		Model model = ModelFactory.createDefaultModel();
		LOGGER.info("Reading " + fileResource.getFilename());
		model.read(fileResource.getInputStream(), null, "TURTLE");
		ds.addNamedModel(uri, model);
	}

	public void addNamedModel(String uri, Model model) {
		ds.addNamedModel(uri, model);
	}

	public void saveNamedModel(String uri, String filePath) throws IOException {
		Model namedModel = ds.getNamedModel(uri);
		FileSystemResource fileResource = new FileSystemResource(filePath);
		FileOutputStream fileOut = new FileOutputStream(fileResource.getFile());
		namedModel.write(fileOut, "TURTLE", uri);
		fileOut.close();
	}

	public String loadNamedModel(String filePath) throws IOException {
		FileSystemResource fileResource = new FileSystemResource(filePath);
		Model namedModel = ModelFactory.createDefaultModel();
		InputStream inputStream = fileResource.getInputStream();
		namedModel.read(inputStream, "", "TURTLE");
		inputStream.close();
		StmtIterator listStatements = namedModel.listStatements((Resource) null, RDF.type, OWL.Ontology);
		if (listStatements.hasNext()) {
			Statement statement = listStatements.nextStatement();
			String uri = statement.getSubject().getURI();
			addNamedModel(uri, namedModel);
			return uri;
		}
		return null;
	}

	public JsonNode query(ParameterizedSparqlString queryStr) throws IOException {
		String query = queryStr.toString();
		HttpURLConnection con = getQueryConnection();
		JsonNode bindings = sendQuery(con, query);
		return bindings;
	}

	private HttpURLConnection getQueryConnection() throws IOException {
		URL obj = new URL(QUERY_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/sparql-query");
		return con;
	}

	private JsonNode sendQuery(HttpURLConnection con, String query) throws IOException {
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(query);
		wr.flush();
		wr.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jTree = mapper.readTree(in);
		JsonNode bindings = jTree.findValue("bindings");
		return bindings;
	}

	public void update(ParameterizedSparqlString queryStr) throws IOException {
		String query = queryStr.toString();
		HttpURLConnection con = getUpdateConnection();
		sendUpdate(con, query);
		int responseCode = con.getResponseCode();
		LOGGER.info("Sending 'POST' request to URL : " + SparqlServer.class);
		LOGGER.info("Post parameters : " + query);
		LOGGER.info("Response Code : " + responseCode);
	}

	private HttpURLConnection getUpdateConnection() throws IOException {
		URL obj = new URL(UPDATE_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/sparql-update");
		return con;
	}

	private void sendUpdate(HttpURLConnection con, String query) throws IOException {
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(query);
		wr.flush();
		wr.close();
	}
}
