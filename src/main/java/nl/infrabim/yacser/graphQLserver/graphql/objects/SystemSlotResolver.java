package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.ParameterizedSparqlString;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.fasterxml.jackson.databind.JsonNode;

import nl.infrabim.yacser.graphQLserver.sparql.SparqlServer;

@Component
public class SystemSlotResolver extends YacserObjectResolver implements GraphQLResolver<SystemSlot> {

	public SystemSlotResolver() {
	}

	public String getName(SystemSlot systemSlot) throws IOException {
		return super.getName(systemSlot);
	}

	public YacserObjectType getType(SystemSlot systemSlot) throws IOException {
		return super.getType(systemSlot);
	}

	public List<Function> getFunctions(SystemSlot systemSlot) throws IOException {
		List<Function> functions = null;
		String modelId = systemSlot.getId().substring(0, systemSlot.getId().indexOf('#'));

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("object", systemSlot.getId());
		queryStr.setIri("model", modelId);
		queryStr.append("SELECT ?function ");
		queryStr.append("WHERE {");
		queryStr.append("	GRAPH ?model { ");
		queryStr.append("	    ?object yacser:hasFunction ?function . ");
		queryStr.append("	}");
		queryStr.append("}");

		JsonNode responseNodes = SparqlServer.instance.query(queryStr);
		if (responseNodes.size() > 0) {
			functions = new ArrayList<>();
			for (JsonNode node : responseNodes) {
				JsonNode functionNode = node.get("function");
				if (functionNode != null) {
					functions.add((Function) YacserObjectRepository.build(YacserObjectType.Function,
							functionNode.get("value").asText()));
				}
			}
		}

		return functions;
	}

}
