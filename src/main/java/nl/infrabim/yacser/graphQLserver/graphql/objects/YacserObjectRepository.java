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
	public static final String YACSER_HAS_FUNCTION = SparqlServer.YACSER_URI + "#hasFunction";
	public static final String YACSER_HAS_MAX_VALUE = SparqlServer.YACSER_URI + "#hasMaxValue";
	public static final String YACSER_HAS_MIN_VALUE = SparqlServer.YACSER_URI + "#hasMinValue";
	public static final String YACSER_HAS_REQUIREMENT = SparqlServer.YACSER_URI + "#hasRequirement";
	public static final String YACSER_SYSTEM_SLOT_0 = SparqlServer.YACSER_URI + "#systemSlot0";
	public static final String YACSER_SYSTEM_SLOT_1 = SparqlServer.YACSER_URI + "#systemSlot1";
	public static final String SKOS_PREF_LABEL = SparqlServer.SKOS_URI + "#prefLabel";
	public static final String DB_DESCRIPTION = SparqlServer.DBC_URI + "description";

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
	public YacserObject createObject(String modelId, YacserObjectType type, Optional<String> objectName,
			Optional<String> objectDescription) throws IOException {
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
		if (objectDescription.isPresent()) {
			queryStr.append("    ?object db:description \"" + objectDescription.get() + "\"@en . ");
		}
		queryStr.append("  } ");
		queryStr.append("} ");
		queryStr.append("WHERE {} ");

		SparqlServer.instance.update(queryStr);

		return build(type, objectId);
	}

	public static YacserObject build(YacserObjectType type, String objectId) {
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
			yacserObject = new Requirement(objectId);
			break;
		case SystemInterface:
			yacserObject = new SystemInterface(objectId);
			break;
		case SystemSlot:
			yacserObject = new SystemSlot(objectId);
			break;
		case Value:
			yacserObject = new Value(objectId);
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
		queryStr.append("	   	UNION ");
		queryStr.append("	   	{ OPTIONAL { ?type rdfs:subClassOf bs:Property . } } ");
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

	public static String getRelatedObject(String subjectId, String relationId) throws IOException {
		String modelId = subjectId.substring(0, subjectId.indexOf('#'));

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("graph", modelId);
		queryStr.setIri("subject", subjectId);
		queryStr.setIri("predicate", relationId);
		queryStr.append("SELECT ?object { ");
		queryStr.append("  GRAPH ?graph { ");
		queryStr.append("    ?subject ?predicate ?object . ");
		queryStr.append("  } ");
		queryStr.append("} ");

		JsonNode responseNodes = SparqlServer.instance.query(queryStr);
		if (responseNodes.size() > 0) {
			for (JsonNode node : responseNodes) {
				JsonNode objectNode = node.get("object");
				if (objectNode != null) {
					return objectNode.get("value").asText();
				}
			}
		}
		return null;
	}

	private void addRelatedObjects(String subjectId, String relationId, List<String> objectIds) throws IOException {
		String modelId = subjectId.substring(0, subjectId.indexOf('#'));

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("graph", modelId);
		queryStr.setIri("subject", subjectId);
		queryStr.setIri("predicate", relationId);
		queryStr.append("INSERT { ");
		queryStr.append("  GRAPH ?graph { ");
		int index = 1;
		for (String objectId : objectIds) {
			queryStr.setIri("object" + index, objectId);
			queryStr.append("    ?subject ?predicate ?object" + index + " . ");
			index++;
		}
		queryStr.append("  } ");
		queryStr.append("} ");
		queryStr.append("WHERE {} ");

		SparqlServer.instance.update(queryStr);
	}

	private void updateRelatedObject(String subjectId, String relationId, String objectId) throws IOException {
		String modelId = subjectId.substring(0, subjectId.indexOf('#'));

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("graph", modelId);
		queryStr.setIri("subject", subjectId);
		queryStr.setIri("predicate", relationId);
		queryStr.setIri("object", objectId);
		queryStr.append("INSERT { ");
		queryStr.append("  GRAPH ?graph { ");
		queryStr.append("    ?subject ?predicate ?object . ");
		queryStr.append("  } ");
		queryStr.append("} ");
		queryStr.append("WHERE {} ");

		SparqlServer.instance.update(queryStr);
	}

	public static void updateStringLiteral(String subjectId, String relationId, String newLiteral) throws IOException {
		int indexOfHashMark = subjectId.indexOf('#');
		String modelId = indexOfHashMark != -1 ? subjectId.substring(0, indexOfHashMark) : subjectId;

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("graph", modelId);
		queryStr.setIri("subject", subjectId);
		queryStr.setIri("predicate", relationId);
		queryStr.setLiteral("newLiteral", newLiteral, "en");
		queryStr.append("DELETE { ");
		queryStr.append("  GRAPH ?graph { ");
		queryStr.append("    ?subject ?predicate ?oldLiteral . ");
		queryStr.append("  } ");
		queryStr.append("} ");
		if (newLiteral.length() > 0) {
			queryStr.append("INSERT { ");
			queryStr.append("  GRAPH ?graph { ");
			queryStr.append("    ?subject ?predicate ?newLiteral . ");
			queryStr.append("  } ");
			queryStr.append("} ");
		}
		queryStr.append("WHERE { ");
		queryStr.append("  GRAPH ?graph { ");
		queryStr.append("    ?subject ?predicate ?oldLiteral . ");
		queryStr.append("  } ");
		queryStr.append("} ");

		SparqlServer.instance.update(queryStr);
	}

	/**
	 * Update Function object
	 * 
	 * @param functionId        Function ID.
	 * @param updateName        If present, updated name.
	 * @param updateDescription If present, updated description.
	 * @param addRequirements   If present, additional requirements.
	 * @return Updated Function object.
	 * @throws IOException
	 */
	public Function updateFunction(String functionId, Optional<String> updateName, Optional<String> updateDescription,
			Optional<List<String>> addRequirements) throws IOException {

		if (updateName.isPresent()) {
			updateStringLiteral(functionId, SKOS_PREF_LABEL, updateName.get());
		}

		if (updateDescription.isPresent()) {
			updateStringLiteral(functionId, DB_DESCRIPTION, updateDescription.get());
		}

		if (addRequirements.isPresent()) {
			addRelatedObjects(functionId, YACSER_HAS_REQUIREMENT, addRequirements.get());
		}

		return (Function) build(YacserObjectType.Function, functionId);
	}

	/**
	 * Update Requirement object
	 * 
	 * @param requirementId     Requirement ID.
	 * @param updateName        If present, updated name.
	 * @param updateDescription If present, updated description.
	 * @param updateMinValue    If present, updated minimal value.
	 * @param updateMaxValue    If present, updated maximal value.
	 * @return Updated Requirement object.
	 * @throws IOException
	 */
	public Requirement updateRequirement(String requirementId, Optional<String> updateName,
			Optional<String> updateDescription, Optional<String> updateMinValue, Optional<String> updateMaxValue)
			throws IOException {

		if (updateName.isPresent()) {
			updateStringLiteral(requirementId, SKOS_PREF_LABEL, updateName.get());
		}

		if (updateDescription.isPresent()) {
			updateStringLiteral(requirementId, DB_DESCRIPTION, updateDescription.get());
		}

		if (updateMinValue.isPresent()) {
			updateRelatedObject(requirementId, YACSER_HAS_MIN_VALUE, updateMinValue.get());
		}

		if (updateMaxValue.isPresent()) {
			updateRelatedObject(requirementId, YACSER_HAS_MAX_VALUE, updateMaxValue.get());
		}

		return (Requirement) build(YacserObjectType.Requirement, requirementId);
	}

	/**
	 * Update SystemInterface
	 * 
	 * @param systemInterfaceId
	 * @param updateName
	 * @param updateSystemSlot0
	 * @param updateSystemSlot1
	 * @return SystemInterface object
	 * @throws IOException
	 */
	public SystemInterface updateSystemInterface(String systemInterfaceId, Optional<String> updateName,
			Optional<String> updateDescription, Optional<String> updateSystemSlot0, Optional<String> updateSystemSlot1)
			throws IOException {

		if (updateName.isPresent()) {
			updateStringLiteral(systemInterfaceId, SKOS_PREF_LABEL, updateName.get());
		}

		if (updateDescription.isPresent()) {
			updateStringLiteral(systemInterfaceId, DB_DESCRIPTION, updateDescription.get());
		}

		if (updateSystemSlot0.isPresent()) {
			updateRelatedObject(systemInterfaceId, YACSER_SYSTEM_SLOT_0, updateSystemSlot0.get());
		}

		if (updateSystemSlot1.isPresent()) {
			updateRelatedObject(systemInterfaceId, YACSER_SYSTEM_SLOT_1, updateSystemSlot1.get());
		}

		return (SystemInterface) build(YacserObjectType.SystemInterface, systemInterfaceId);
	}

	/**
	 * Update SystemSlot
	 * 
	 * @param systemSlotId
	 * @param updateName
	 * @param addFunctions
	 * @return SystemSlot object
	 * @throws IOException
	 */
	public SystemSlot updateSystemSlot(String systemSlotId, Optional<String> updateName,
			Optional<String> updateDescription, Optional<List<String>> addFunctions) throws IOException {

		if (updateName.isPresent()) {
			updateStringLiteral(systemSlotId, SKOS_PREF_LABEL, updateName.get());
		}

		if (updateDescription.isPresent()) {
			updateStringLiteral(systemSlotId, DB_DESCRIPTION, updateDescription.get());
		}

		if (addFunctions.isPresent()) {
			addRelatedObjects(systemSlotId, YACSER_HAS_FUNCTION, addFunctions.get());
		}

		return (SystemSlot) build(YacserObjectType.SystemSlot, systemSlotId);
	}

}
