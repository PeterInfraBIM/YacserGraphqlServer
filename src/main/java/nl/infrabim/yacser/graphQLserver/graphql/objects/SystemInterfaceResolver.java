package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;

import org.apache.jena.query.ParameterizedSparqlString;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.fasterxml.jackson.databind.JsonNode;

import nl.infrabim.yacser.graphQLserver.sparql.SparqlServer;

@Component
public class SystemInterfaceResolver extends YacserObjectResolver implements GraphQLResolver<SystemInterface> {

	public SystemInterfaceResolver() {
	}

	public String getName(SystemInterface systemInterface) throws IOException {
		return super.getName(systemInterface);
	}

	public YacserObjectType getType(SystemInterface systemInterface) throws IOException {
		return super.getType(systemInterface);
	}

	public SystemSlot getSystemSlot0(SystemInterface systemInterface) throws IOException {
		return getSystemSlot(systemInterface, "systemSlot0");
	}

	public SystemSlot getSystemSlot1(SystemInterface systemInterface) throws IOException {
		return getSystemSlot(systemInterface, "systemSlot1");
	}

	private SystemSlot getSystemSlot(SystemInterface systemInterface, String systemSlot) throws IOException {
		String modelId = systemInterface.getId().substring(0, systemInterface.getId().indexOf('#'));

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("object", systemInterface.getId());
		queryStr.setIri("model", modelId);
		queryStr.append("SELECT ?systemSlot ");
		queryStr.append("WHERE { ");
		queryStr.append("	GRAPH ?model { ");
		queryStr.append("	    ?object yacser:" + systemSlot + " ?systemSlot . ");
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
