package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;

import org.apache.jena.query.ParameterizedSparqlString;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.fasterxml.jackson.databind.JsonNode;

import nl.infrabim.yacser.graphQLserver.sparql.SparqlServer;

@Component
public class FunctionResolver extends YacserObjectResolver implements GraphQLResolver<Function> {

	public FunctionResolver() {
	}

	public String getName(Function function) throws IOException {
		return super.getName(function);
	}

	public YacserObjectType getType(Function function) throws IOException {
		return super.getType(function);
	}

	public SystemSlot getOwner(Function function) throws IOException {
		String modelId = function.getId().substring(0, function.getId().indexOf('#'));

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("object", function.getId());
		queryStr.setIri("model", modelId);
		queryStr.append("SELECT ?systemSlot ");
		queryStr.append("WHERE { ");
		queryStr.append("	GRAPH ?model { ");
		queryStr.append("	    ?systemSlot yacser:hasFunction ?object . ");
		queryStr.append("	} ");
		queryStr.append("} ");

		JsonNode responseNodes = SparqlServer.instance.query(queryStr);
		if (responseNodes.size() > 0) {
			for (JsonNode node : responseNodes) {
				JsonNode systemSlotNode = node.get("systemSlot");
				if (systemSlotNode != null) {
					return ((SystemSlot) YacserObjectRepository.build(YacserObjectType.SystemSlot,
							systemSlotNode.get("value").asText()));
				}
			}
		}

		return null;
	}
}
