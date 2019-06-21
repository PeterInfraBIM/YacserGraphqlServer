package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;

import org.apache.jena.query.ParameterizedSparqlString;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import nl.infrabim.yacser.graphQLserver.sparql.SparqlServer;

@Component
public class YacserObjectResolver {

	public YacserObjectResolver() {
	}

	/**
	 * Get the name of the YACSER object.
	 * 
	 * @param yacserObject the YACSER object instance
	 * @return the name string
	 * @throws IOException
	 */
	public String getName(YacserObject yacserObject) throws IOException {
		String modelId = yacserObject.getId().substring(0, yacserObject.getId().indexOf('#'));

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("object", yacserObject.getId());
		queryStr.setIri("model", modelId);
		queryStr.append("SELECT ?label ");
		queryStr.append("WHERE {");
		queryStr.append("	GRAPH ?model { ");
		queryStr.append("	    ?object skos:prefLabel ?label . ");
		queryStr.append("	}");
		queryStr.append("}");

		JsonNode responseNodes = SparqlServer.instance.query(queryStr);
		if (responseNodes.size() > 0) {
			for (JsonNode node : responseNodes) {
				JsonNode labelNode = node.get("label");
				if (labelNode != null) {
					return labelNode.get("value").asText();
				}
			}
		}

		return null;
	}

	/**
	 * Get the type of the YACSER object.
	 * 
	 * @param yacserObject the YACSER object instance
	 * @return the type
	 * @throws IOException
	 */
	public YacserObjectType getType(YacserObject yacserObject) throws IOException {
		String modelId = yacserObject.getId().substring(0, yacserObject.getId().indexOf('#'));

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("object", yacserObject.getId());
		queryStr.setIri("model", modelId);
		queryStr.append("SELECT ?type ");
		queryStr.append("WHERE {");
		queryStr.append("	GRAPH ?model { ");
		queryStr.append("	    ?object rdf:type ?type . ");
		queryStr.append("	}");
		queryStr.append("}");

		JsonNode responseNodes = SparqlServer.instance.query(queryStr);
		if (responseNodes.size() > 0) {
			for (JsonNode node : responseNodes) {
				JsonNode typeNode = node.get("type");
				if (typeNode != null) {
					return YacserObjectType.getType(typeNode.get("value").asText());
				}
			}
		}

		return null;
	}

}
