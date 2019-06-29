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
public class FunctionResolver extends YacserObjectResolver implements GraphQLResolver<Function> {

	public FunctionResolver() {
	}

	public String getName(Function function) throws IOException {
		return super.getName(function);
	}

	public String getDescription(Function function) throws IOException {
		return super.getDescription(function);
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

	public List<Requirement> getRequirements(Function function) throws IOException {
		List<Requirement> requirements = null;
		String modelId = function.getId().substring(0, function.getId().indexOf('#'));

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("object", function.getId());
		queryStr.setIri("model", modelId);
		queryStr.append("SELECT ?requirement ?label ");
		queryStr.append("WHERE { ");
		queryStr.append("	GRAPH ?model { ");
		queryStr.append("	    ?object yacser:hasRequirement ?requirement . ");
		queryStr.append("	    OPTIONAL { ?object skos:prefLabel ?label . } ");
		queryStr.append("	} ");
		queryStr.append("} ");
		queryStr.append("ORDER BY ?label ");

		JsonNode responseNodes = SparqlServer.instance.query(queryStr);
		if (responseNodes.size() > 0) {
			requirements = new ArrayList<>();
			for (JsonNode node : responseNodes) {
				JsonNode requirementNode = node.get("requirement");
				if (requirementNode != null) {
					requirements.add((Requirement) YacserObjectRepository.build(YacserObjectType.Requirement,
							requirementNode.get("value").asText()));
				}
			}
		}

		return requirements;
	}
}
