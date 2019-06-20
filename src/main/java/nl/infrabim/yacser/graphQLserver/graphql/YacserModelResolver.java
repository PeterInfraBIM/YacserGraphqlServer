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

	public String getName(YacserModel yacserModel) throws IOException {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("model", yacserModel.getId());
		queryStr.setIri("graph", yacserModel.getId());
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

}