package nl.infrabim.yacser.graphQLserver.graphql;

import java.io.IOException;

import org.apache.jena.query.ParameterizedSparqlString;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.fasterxml.jackson.databind.JsonNode;

import nl.infrabim.yacser.graphQLserver.sparql.SparqlServer;

@Component
public class YacserModelResolver implements GraphQLResolver<YacserModel> {

	public YacserModelResolver() {
	}

	/**
	 * Get the name of the YACSER model.
	 * 
	 * @param yacserModel the YACSER model instance
	 * @return the name string
	 * @throws IOException
	 */
	public String getName(YacserModel yacserModel) throws IOException {
		String modelId = yacserModel.getId();
		
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("model", modelId);
		queryStr.setIri("graph", modelId);
		queryStr.append("SELECT ?label ");
		queryStr.append("WHERE {");
		queryStr.append("	GRAPH ?graph {");
		queryStr.append("	   	OPTIONAL {	?model skos:prefLabel ?label . } ");
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
	 * Get the description of the YACSER object.
	 * 
	 * @param yacserModel the YACSER object instance
	 * @return the description string
	 * @throws IOException
	 */
	public String getDescription(YacserModel yacserModel) throws IOException {
		String modelId = yacserModel.getId();

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("object", yacserModel.getId());
		queryStr.setIri("model", modelId);
		queryStr.append("SELECT ?description ");
		queryStr.append("WHERE {");
		queryStr.append("	GRAPH ?model { ");
		queryStr.append("	    ?object db:description ?description . ");
		queryStr.append("	}");
		queryStr.append("}");

		JsonNode responseNodes = SparqlServer.instance.query(queryStr);
		if (responseNodes.size() > 0) {
			for (JsonNode node : responseNodes) {
				JsonNode descriptionNode = node.get("description");
				if (descriptionNode != null) {
					return descriptionNode.get("value").asText();
				}
			}
		}

		return null;
	}

}
