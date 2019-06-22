package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.jena.query.ParameterizedSparqlString;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import nl.infrabim.yacser.graphQLserver.sparql.SparqlServer;

@Component
public class YacserObjectRepository {

	/**
	 * Constructor
	 */
	public YacserObjectRepository() {
	}

	/**
	 * Create a YACSER object.
	 * 
	 * @param modelId    Model base URI
	 * @param type       YACSER object type
	 * @param objectName Optional object name
	 * @return the new YACSER object
	 * @throws IOException
	 */
	public YacserObject createObject(String modelId, YacserObjectType type, Optional<String> objectName)
			throws IOException {
		String objectId = modelId + "#" + UUID.randomUUID().toString();
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("graph", modelId);
		queryStr.setIri("object", objectId);
		queryStr.setIri("class", SparqlServer.YACSER_URI + "#" + type.name());
		queryStr.append("INSERT { ");
		queryStr.append("  GRAPH ?graph { ");
		queryStr.append("    ?object rdf:type ?class . ");
		if (objectName.isPresent()) {
			queryStr.append("    ?object skos:prefLabel \"" + objectName.get() + "\"@en . ");
		}
		queryStr.append("  } ");
		queryStr.append("} ");
		queryStr.append("WHERE {} ");

		SparqlServer.instance.update(queryStr);

		return build(type, objectId);
	}

	static YacserObject build(YacserObjectType type, String objectId) {
		YacserObject yacserObject = null;
		switch (type) {
		case Function:
			yacserObject = new Function(objectId);
			break;
		case Hamburger:
			yacserObject = new Hamburger(objectId);
			break;
		case Performance:
			break;
		case PortRealisation:
			break;
		case RealisationModule:
			break;
		case RealisationPort:
			break;
		case Requirement:
			break;
		case SystemInterface:
			break;
		case SystemSlot:
			yacserObject = new SystemSlot(objectId);
			break;
		case Value:
			break;
		default:
			break;
		}

		return yacserObject;
	}

	/**
	 * Get all YACSER objects of the specified model
	 * 
	 * @param modelId Model base URI
	 * @return all YACSER objects
	 * @throws IOException
	 */
	public List<YacserObject> getAllObjects(String modelId) throws IOException {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("model", modelId);
		queryStr.setIri("yacser", SparqlServer.YACSER_URI);
		queryStr.append("SELECT ?object ?name ?type ");
		queryStr.append("WHERE {");
		queryStr.append("	GRAPH ?model { ");
		queryStr.append("	   	?object rdf:type ?type .  ");
		queryStr.append("	   	OPTIONAL { ?object skos:prefLabel ?name . } ");
		queryStr.append("	}");
		queryStr.append("	GRAPH ?yacser { ");
		queryStr.append("	   	{ OPTIONAL { ?type rdfs:subClassOf bs:Activity . } } ");
		queryStr.append("	   	UNION ");
		queryStr.append("	   	{ OPTIONAL { ?type rdfs:subClassOf bs:InformationObject . } } ");
		queryStr.append("	   	UNION ");
		queryStr.append("	   	{ OPTIONAL { ?type rdfs:subClassOf bs:PhysicalObject . } } ");
		queryStr.append("	}");
		queryStr.append("}");
		queryStr.append("ORDER BY ?name ");

		JsonNode responseNodes = SparqlServer.instance.query(queryStr);

		List<YacserObject> objects = null;
		if (responseNodes.size() > 0) {
			objects = new ArrayList<>();
			for (JsonNode node : responseNodes) {
				YacserObjectType type = null;
				JsonNode typeNode = node.get("type");
				if (typeNode != null) {
					type = YacserObjectType.getType(typeNode.get("value").asText());
				}
				JsonNode objectNode = node.get("object");
				if (objectNode != null && type != null) {
					String objectUri = objectNode.get("value").asText();
					objects.add(build(type, objectUri));
				}
			}
		}

		return objects;
	}

	/**
	 * Get YACSER object by ID and type.
	 * 
	 * @param type     YACSER Object type
	 * @param objectId Object id
	 * @return YACSER object
	 */
	public YacserObject getObject(YacserObjectType type, String objectId) {
		return build(type, objectId);
	}

}
