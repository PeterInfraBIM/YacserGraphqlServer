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

	public String getDescription(SystemSlot systemSlot) throws IOException {
		return super.getDescription(systemSlot);
	}

	public YacserObjectType getType(SystemSlot systemSlot) throws IOException {
		return super.getType(systemSlot);
	}

	public List<Function> getFunctions(SystemSlot systemSlot) throws IOException {
		List<String> functionIds = YacserObjectRepository.getRelatedObjects(systemSlot.getId(),
				YacserObjectRepository.YACSER_HAS_FUNCTION);
		if (functionIds != null) {
			List<Function> functions = new ArrayList<>();
			for (String functionId : functionIds) {
				functions
						.add((Function) YacserObjectRepository.build(YacserObjectType.Function, functionId));
			}
			return functions;
		}
		return null;
	}
	
	public List<SystemInterface> getInterfaces(SystemSlot systemSlot) throws IOException {
		List<SystemInterface> interfaces = null;
		String modelId = systemSlot.getId().substring(0, systemSlot.getId().indexOf('#'));

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("object", systemSlot.getId());
		queryStr.setIri("model", modelId);
		queryStr.append("SELECT ?interface ?label ");
		queryStr.append("WHERE { ");
		queryStr.append("	GRAPH ?model { ");
		queryStr.append("	  { ");
		queryStr.append("	    OPTIONAL { ?interface yacser:systemSlot0 ?object . } ");
		queryStr.append("	      } UNION { ");
		queryStr.append("	    OPTIONAL { ?interface yacser:systemSlot1 ?object . } ");
		queryStr.append("	  } ");
		queryStr.append("	    OPTIONAL { ?interface skos:prefLabel ?label . } ");
		queryStr.append("	} ");
		queryStr.append("} ");
		queryStr.append("ORDER BY ?label ");

		JsonNode responseNodes = SparqlServer.instance.query(queryStr);
		if (responseNodes.size() > 0) {
			interfaces = new ArrayList<>();
			for (JsonNode node : responseNodes) {
				JsonNode interfaceNode = node.get("interface");
				if (interfaceNode != null) {
					interfaces.add((SystemInterface) YacserObjectRepository.build(YacserObjectType.SystemInterface,
							interfaceNode.get("value").asText()));
				}
			}
		}

		return interfaces;
	}
	
	public List<Hamburger> getHamburgers(SystemSlot systemSlot) throws IOException {
		List<Hamburger> hamburgers = null;
		List<String> hamburgerIds = YacserObjectRepository.getRelatedSubjects(systemSlot.getId(),
				YacserObjectRepository.YACSER_FUNCTIONAL_UNIT);
		if (hamburgerIds != null) {
			hamburgers = new ArrayList<>();
			for (String hamburgerId : hamburgerIds) {
				hamburgers.add((Hamburger) YacserObjectRepository.build(YacserObjectType.Hamburger, hamburgerId));
			}
		}
		return hamburgers;
	}
}
