package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;

import org.apache.jena.query.ParameterizedSparqlString;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.fasterxml.jackson.databind.JsonNode;

import nl.infrabim.yacser.graphQLserver.sparql.SparqlServer;

@Component
public class RequirementResolver extends YacserObjectResolver implements GraphQLResolver<Requirement> {

	public RequirementResolver() {
	}

	public String getName(Requirement requirement) throws IOException {
		return super.getName(requirement);
	}

	public String getDescription(Requirement requirement) throws IOException {
		return super.getDescription(requirement);
	}

	public YacserObjectType getType(Requirement requirement) throws IOException {
		return super.getType(requirement);
	}

	public YacserObject getOwner(Requirement requirement) throws IOException {
		String modelId = requirement.getId().substring(0, requirement.getId().indexOf('#'));

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("object", requirement.getId());
		queryStr.setIri("model", modelId);
		queryStr.append("SELECT ?owner ?ownerType ");
		queryStr.append("WHERE { ");
		queryStr.append("	GRAPH ?model { ");
		queryStr.append("	    ?owner yacser:hasRequirement ?object . ");
		queryStr.append("	    ?owner rdf:type ?ownerType . ");
		queryStr.append("	} ");
		queryStr.append("} ");

		JsonNode responseNodes = SparqlServer.instance.query(queryStr);
		if (responseNodes.size() > 0) {
			for (JsonNode node : responseNodes) {
				JsonNode ownerTypeNode = node.get("ownerType");
				if (ownerTypeNode != null) {
					String ownerType = ownerTypeNode.get("value").asText();
					YacserObjectType type = YacserObjectType.getType(ownerType);
					JsonNode ownerNode = node.get("owner");
					if (ownerNode != null) {
						String ownerId = ownerNode.get("value").asText();
						return YacserObjectRepository.build(type, ownerId);
					}
				}
			}
		}

		return null;
	}

	public Value getMinValue(Requirement requirement) {
		return null;
	}
	
	public Value getMaxValue(Requirement requirement) {
		return null;
	}
}
