package nl.infrabim.yacser.graphQLserver.graphql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import nl.infrabim.yacser.graphQLserver.graphql.objects.YacserObjectRepository;
import nl.infrabim.yacser.graphQLserver.sparql.SparqlServer;

@Component
public class YacserModelRepository {

	public YacserModelRepository() {
	}

	public List<String> getAllModelFiles() throws IOException {
		List<String> list = new ArrayList<>();
		Files.newDirectoryStream(Paths.get("."), path -> path.toString().endsWith(".ttl"))
				.forEach((file) -> list.add(file.getFileName().toString()));
		return list;
	}

	public List<YacserModel> getAllModels() throws IOException {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.append("SELECT ?model ");
		queryStr.append("WHERE {");
		queryStr.append("	GRAPH ?graph { ");
		queryStr.append("	   	?model rdf:type owl:Ontology .  ");
		queryStr.append("	}");
		queryStr.append("}");

		JsonNode responseNodes = SparqlServer.instance.query(queryStr);

		List<YacserModel> models = null;
		if (responseNodes.size() > 0) {
			models = new ArrayList<>();
			for (JsonNode node : responseNodes) {
				JsonNode modelNode = node.get("model");
				if (modelNode != null) {
					String modelUri = modelNode.get("value").asText();
					if (!modelUri.equals(SparqlServer.YACSER_URI) && !modelUri.equals(SparqlServer.BS_URI)) {
						models.add(new YacserModel(modelUri));
					}
				}
			}
		}

		return models;
	}
	
	public YacserModel getModel(String modelId) throws IOException {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("model", modelId);
		queryStr.append("SELECT ?graph ");
		queryStr.append("WHERE {");
		queryStr.append("	GRAPH ?graph { ");
		queryStr.append("	   	?model rdf:type owl:Ontology . ");
		queryStr.append("	}");
		queryStr.append("}");

		JsonNode responseNodes = SparqlServer.instance.query(queryStr);

		if (responseNodes.size() > 0) {
			for (JsonNode node : responseNodes) {
				JsonNode graphNode = node.get("graph");
				if (graphNode != null) {
					String modelUri = graphNode.get("value").asText();
					if (!modelUri.equals(SparqlServer.YACSER_URI) && !modelUri.equals(SparqlServer.BS_URI)) {
						return new YacserModel(modelUri);
					}
				}
			}
		}

		return null;
	}

	public YacserModel createModel(String id, Optional<String> modelName, Optional<String> modelDescription) {
		YacserModel yacserModel = new YacserModel(id);
		create(yacserModel, modelName, modelDescription);
		return yacserModel;
	}

	private void create(YacserModel yacserModel, Optional<String> name, Optional<String> description) {
		Model model = ModelFactory.createDefaultModel();

		model.setNsPrefix("bs", "https://w3id.org/def/basicsemantics#");
		model.setNsPrefix("cdt", "https://w3id.org/def/simple_custom_datatypes#");
		model.setNsPrefix("dc", "http://purl.org/dc/elements/1.1/");
		model.setNsPrefix("dct", "http://purl.org/dc/terms/");
		model.setNsPrefix("owl", OWL.NS);
		model.setNsPrefix("rdf", RDF.uri);
		model.setNsPrefix("rdfs", RDFS.uri);
		model.setNsPrefix("skos", "http://www.w3.org/2004/02/skos/core#");
		model.setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");
		model.setNsPrefix("yacser", SparqlServer.YACSER_URI + '#');
		model.setNsPrefix("", yacserModel.getId() + '#');

		Resource ontology = model.createResource(yacserModel.getId());
		Resource yacser = model.createResource(SparqlServer.YACSER_URI);
		model.add(model.createStatement(ontology, RDF.type, OWL.Ontology));
		model.add(model.createStatement(ontology, OWL.imports, yacser));

		if (name.isPresent()) {
			Property prefLabel = model.createProperty("http://www.w3.org/2004/02/skos/core#prefLabel");
			Literal label = model.createLiteral(name.get(), "en");
			model.add(model.createStatement(ontology, prefLabel, label));
		}

		if (description.isPresent()) {
			Property descr = model.createProperty("http://purl.org/dc/elements/1.1/description");
			Literal label = model.createLiteral(description.get(), "en");
			model.add(model.createStatement(ontology, descr, label));
		}

		SparqlServer.instance.addNamedModel(yacserModel.getId(), model);
	}

	/**
	 * Update YacserModel object
	 * 
	 * @param modelId           Model ID.
	 * @param updateName        If present, updated name.
	 * @param updateDescription If present, updated description.
	 * @return Updated Function object.
	 * @throws IOException
	 */
	public YacserModel updateModel(String modelId, Optional<String> updateName, Optional<String> updateDescription)
			throws IOException {

		if (updateName.isPresent()) {
			YacserObjectRepository.updateLiteral(modelId, YacserObjectRepository.SKOS_PREF_LABEL, updateName.get());
		}

		if (updateDescription.isPresent()) {
			YacserObjectRepository.updateLiteral(modelId, YacserObjectRepository.DB_DESCRIPTION,
					updateDescription.get());
		}

		return new YacserModel(modelId);
	}

}
