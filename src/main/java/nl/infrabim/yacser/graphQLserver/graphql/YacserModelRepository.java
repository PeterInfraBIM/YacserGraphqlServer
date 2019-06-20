package nl.infrabim.yacser.graphQLserver.graphql;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import nl.infrabim.yacser.graphQLserver.sparql.SparqlServer;

@Component
public class YacserModelRepository {

	public YacserModelRepository() {
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

	public YacserModel createModel(String id, String name) {
		YacserModel yacserModel = new YacserModel(id);
		create(yacserModel, name);
		return yacserModel;
	}

	private void create(YacserModel yacserModel, String name) {
		Model model = ModelFactory.createDefaultModel();

		model.setNsPrefix("bs", "https://w3id.org/def/basicsemantics#");
		model.setNsPrefix("cdt", "https://w3id.org/def/simple_custom_datatypes#");
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

		if (name != null) {
			Property prefLabel = model.createProperty("http://www.w3.org/2004/02/skos/core#prefLabel");
			Literal label = model.createLiteral(name, "en");
			model.add(model.createStatement(ontology, prefLabel, label));
		}

		SparqlServer.instance.addNamedModel(yacserModel.getId(), model);
	}

}
