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
	public static final String YACSER_FUNCTIONAL_UNIT = SparqlServer.YACSER_URI + "#functionalUnit";
	public static final String YACSER_HAS_FUNCTION = SparqlServer.YACSER_URI + "#hasFunction";
	public static final String YACSER_HAS_INPUT = SparqlServer.YACSER_URI + "#hasInput";
	public static final String YACSER_HAS_MAX_VALUE = SparqlServer.YACSER_URI + "#hasMaxValue";
	public static final String YACSER_HAS_MIN_VALUE = SparqlServer.YACSER_URI + "#hasMinValue";
	public static final String YACSER_HAS_OUTPUT = SparqlServer.YACSER_URI + "#hasOutput";
	public static final String YACSER_HAS_PERFORMANCE = SparqlServer.YACSER_URI + "#hasPerformance";
	public static final String YACSER_HAS_PORT = SparqlServer.YACSER_URI + "#hasPort";
	public static final String YACSER_HAS_PORT_REALISATION = SparqlServer.YACSER_URI + "#hasPortRealisation";
	public static final String YACSER_HAS_REQUIREMENT = SparqlServer.YACSER_URI + "#hasRequirement";
	public static final String YACSER_HAS_VALUE = SparqlServer.YACSER_URI + "#hasValue";
	public static final String YACSER_INTERFACE = SparqlServer.YACSER_URI + "#interface";
	public static final String YACSER_PORT = SparqlServer.YACSER_URI + "#port";
	public static final String YACSER_SYSTEM_SLOT_0 = SparqlServer.YACSER_URI + "#systemSlot0";
	public static final String YACSER_SYSTEM_SLOT_1 = SparqlServer.YACSER_URI + "#systemSlot1";
	public static final String YACSER_TECHNICAL_SOLUTION = SparqlServer.YACSER_URI + "#technicalSolution";
	public static final String SKOS_PREF_LABEL = SparqlServer.SKOS_URI + "#prefLabel";
	public static final String DB_DESCRIPTION = SparqlServer.DBC_URI + "description";
	public static final String DCT_HAS_PART = SparqlServer.DCT_URI + "hasPart";
	public static final String BS_HAS_UNIT = SparqlServer.BS_URI + "#hasUnit";
	public static final String BS_HAS_VALUE = SparqlServer.BS_URI + "#hasValue";

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
			yacserObject = new Performance(objectId);
			break;
		case PortRealisation:
			yacserObject = new PortRealisation(objectId);
			break;
		case RealisationModule:
			yacserObject = new RealisationModule(objectId);
			break;
		case RealisationPort:
			yacserObject = new RealisationPort(objectId);
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

	public static String getType(String subjectId) throws IOException {
		String modelId = getModelId(subjectId);

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("graph", modelId);
		queryStr.setIri("subject", subjectId);
		queryStr.append("SELECT ?type { ");
		queryStr.append("  GRAPH ?graph { ");
		queryStr.append("    ?subject rdf:type ?type . ");
		queryStr.append("  } ");
		queryStr.append("} ");

		JsonNode responseNodes = SparqlServer.instance.query(queryStr);
		if (responseNodes.size() > 0) {
			for (JsonNode node : responseNodes) {
				JsonNode typeNode = node.get("type");
				if (typeNode != null) {
					return typeNode.get("value").asText();
				}
			}
		}
		return null;
	}

	public static String getRelatedObject(String subjectId, String relationId) throws IOException {
		String modelId = getModelId(subjectId);

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

	public static List<String> getRelatedObjects(String subjectId, String relationId) throws IOException {
		List<String> objectIds = null;
		String modelId = getModelId(subjectId);

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("graph", modelId);
		queryStr.setIri("subject", subjectId);
		queryStr.setIri("predicate", relationId);
		queryStr.append("SELECT ?object ?label ");
		queryStr.append("WHERE { ");
		queryStr.append("	GRAPH ?graph { ");
		queryStr.append("	    ?subject ?predicate ?object . ");
		queryStr.append("	    OPTIONAL { ?object skos:prefLabel ?label . } ");
		queryStr.append("	} ");
		queryStr.append("} ");
		queryStr.append("ORDER BY ?label ");

		JsonNode responseNodes = SparqlServer.instance.query(queryStr);
		if (responseNodes.size() > 0) {
			objectIds = new ArrayList<>();
			for (JsonNode node : responseNodes) {
				JsonNode objectNode = node.get("object");
				if (objectNode != null) {
					objectIds.add(objectNode.get("value").asText());
				}
			}
		}

		return objectIds;
	}

	public static String getRelatedSubject(String objectId, String relationId) throws IOException {
		String modelId = getModelId(objectId);

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("graph", modelId);
		queryStr.setIri("object", objectId);
		queryStr.setIri("predicate", relationId);
		queryStr.append("SELECT ?subject { ");
		queryStr.append("  GRAPH ?graph { ");
		queryStr.append("    ?subject ?predicate ?object . ");
		queryStr.append("  } ");
		queryStr.append("} ");

		JsonNode responseNodes = SparqlServer.instance.query(queryStr);
		if (responseNodes.size() > 0) {
			for (JsonNode node : responseNodes) {
				JsonNode subjectNode = node.get("subject");
				if (subjectNode != null) {
					return subjectNode.get("value").asText();
				}
			}
		}
		return null;
	}

	public static List<String> getRelatedSubjects(String objectId, String relationId) throws IOException {
		List<String> subjectIds = null;
		String modelId = getModelId(objectId);

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("graph", modelId);
		queryStr.setIri("object", objectId);
		queryStr.setIri("predicate", relationId);
		queryStr.append("SELECT ?subject ?label ");
		queryStr.append("WHERE { ");
		queryStr.append("	GRAPH ?graph { ");
		queryStr.append("	    ?subject ?predicate ?object . ");
		queryStr.append("	    OPTIONAL { ?subject skos:prefLabel ?label . } ");
		queryStr.append("	} ");
		queryStr.append("} ");
		queryStr.append("ORDER BY ?label ");

		JsonNode responseNodes = SparqlServer.instance.query(queryStr);
		if (responseNodes.size() > 0) {
			subjectIds = new ArrayList<>();
			for (JsonNode node : responseNodes) {
				JsonNode subjectNode = node.get("subject");
				if (subjectNode != null) {
					subjectIds.add(subjectNode.get("value").asText());
				}
			}
		}

		return subjectIds;
	}

	public static void addRelatedObjects(String subjectId, String relationId, List<String> objectIds)
			throws IOException {
		String modelId = getModelId(subjectId);

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

	public static void removeRelatedObjects(String subjectId, String relationId, List<String> objectIds)
			throws IOException {
		String modelId = getModelId(subjectId);

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("graph", modelId);
		queryStr.setIri("subject", subjectId);
		queryStr.setIri("predicate", relationId);
		queryStr.append("DELETE { ");
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

	public static void updateRelatedObject(String subjectId, String relationId, String objectId) throws IOException {
		String modelId = getModelId(subjectId);

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("graph", modelId);
		queryStr.setIri("subject", subjectId);
		queryStr.setIri("predicate", relationId);
		queryStr.append("DELETE { ");
		queryStr.append("  GRAPH ?graph { ");
		queryStr.append("    ?subject ?predicate ?oldObject . ");
		queryStr.append("  } ");
		queryStr.append("} ");
		if (!objectId.isEmpty()) {
			queryStr.setIri("object", objectId);
			queryStr.append("INSERT { ");
			queryStr.append("  GRAPH ?graph { ");
			queryStr.append("    ?subject ?predicate ?object . ");
			queryStr.append("  } ");
			queryStr.append("} ");
		}
		queryStr.append("WHERE { ");
		queryStr.append("  GRAPH ?graph { ");
		queryStr.append("    OPTIONAL { ?subject ?predicate ?oldObject . } ");
		queryStr.append("  } ");
		queryStr.append("} ");

		SparqlServer.instance.update(queryStr);
	}

	public static void removeRelatedObject(String subjectId, String relationId, String objectId) throws IOException {
		String modelId = getModelId(subjectId);

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("graph", modelId);
		queryStr.setIri("subject", subjectId);
		queryStr.setIri("predicate", relationId);
		queryStr.setIri("object", objectId);
		queryStr.append("DELETE { ");
		queryStr.append("  GRAPH ?graph { ");
		queryStr.append("    ?subject ?predicate ?object . ");
		queryStr.append("  } ");
		queryStr.append("} ");
		queryStr.append("WHERE {} ");

		SparqlServer.instance.update(queryStr);
	}

	public static Object getLiteral(String subjectId, String relationId) throws IOException {
		String modelId = getModelId(subjectId);

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("graph", modelId);
		queryStr.setIri("subject", subjectId);
		queryStr.setIri("predicate", relationId);
		queryStr.append("SELECT ?literal ");
		queryStr.append("WHERE { ");
		queryStr.append("  GRAPH ?graph { ");
		queryStr.append("    ?subject ?predicate ?literal . ");
		queryStr.append("  } ");
		queryStr.append("} ");

		SparqlServer.instance.query(queryStr);
		JsonNode responseNodes = SparqlServer.instance.query(queryStr);
		if (responseNodes.size() > 0) {
			for (JsonNode node : responseNodes) {
				JsonNode literalNode = node.get("literal");
				if (literalNode != null) {
					JsonNode datatypeNode = literalNode.get("datatype");
					if (datatypeNode != null
							&& datatypeNode.asText().equals("http://www.w3.org/2001/XMLSchema#double")) {
						return literalNode.get("value").asDouble();
					} else
						return literalNode.get("value").asText();
				}
			}
		}

		return null;
	}

	private static String getModelId(String subjectId) {
		int indexOfHashMark = subjectId.indexOf('#');
		return indexOfHashMark != -1 ? subjectId.substring(0, indexOfHashMark) : subjectId;
	}

	public static void updateLiteral(String subjectId, String relationId, Object newLiteral) throws IOException {
		String modelId = getModelId(subjectId);

		ParameterizedSparqlString queryStr = new ParameterizedSparqlString(SparqlServer.getPrefixMapping());
		queryStr.setIri("graph", modelId);
		queryStr.setIri("subject", subjectId);
		queryStr.setIri("predicate", relationId);
		if (newLiteral instanceof String) {
			queryStr.setLiteral("newLiteral", newLiteral.toString(), "en");
		} else if (newLiteral instanceof Double) {
			queryStr.setLiteral("newLiteral", ((Double) newLiteral).doubleValue());
		}
		queryStr.append("DELETE { ");
		queryStr.append("  GRAPH ?graph { ");
		queryStr.append("    ?subject ?predicate ?oldLiteral . ");
		queryStr.append("  } ");
		queryStr.append("} ");
		if ((newLiteral instanceof String && ((String) newLiteral).length() > 0) || newLiteral instanceof Double) {
			queryStr.append("INSERT { ");
			queryStr.append("  GRAPH ?graph { ");
			queryStr.append("    ?subject ?predicate ?newLiteral . ");
			queryStr.append("  } ");
			queryStr.append("} ");
		}
		queryStr.append("WHERE { ");
		queryStr.append("  GRAPH ?graph { ");
		queryStr.append("    OPTIONAL { ?subject ?predicate ?oldLiteral . } ");
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
	 * @param updateInput       If present, updated input system interface.
	 * @param updateOutput      If present, updated output system interface.
	 * @param updateAssembly    If present, updated assembly function.
	 * @param addParts          If present, additional part functions.
	 * @param removeParts       If present, remove part functions.
	 * @return Updated Function object.
	 * @throws IOException
	 */
	public Function updateFunction(String functionId, Optional<String> updateName, Optional<String> updateDescription,
			Optional<List<String>> addRequirements, Optional<List<String>> removeRequirements,
			Optional<String> updateInput, Optional<String> updateOutput, Optional<String> updateAssembly,
			Optional<List<String>> addParts, Optional<List<String>> removeParts) throws IOException {

		if (updateName.isPresent()) {
			updateLiteral(functionId, SKOS_PREF_LABEL, updateName.get());
		}

		if (updateDescription.isPresent()) {
			updateLiteral(functionId, DB_DESCRIPTION, updateDescription.get());
		}

		if (addRequirements.isPresent()) {
			addRelatedObjects(functionId, YACSER_HAS_REQUIREMENT, addRequirements.get());
		}

		if (removeRequirements.isPresent()) {
			removeRelatedObjects(functionId, YACSER_HAS_REQUIREMENT, removeRequirements.get());
		}

		if (updateInput.isPresent()) {
			updateRelatedObject(functionId, YACSER_HAS_INPUT, updateInput.get());
		}

		if (updateOutput.isPresent()) {
			updateRelatedObject(functionId, YACSER_HAS_OUTPUT, updateOutput.get());
		}

		if (updateAssembly.isPresent()) {
			List<String> parts = new ArrayList<>();
			String oldAssemblyId = getRelatedSubject(functionId, DCT_HAS_PART);
			if (oldAssemblyId != null) {
				removeRelatedObject(oldAssemblyId, DCT_HAS_PART, functionId);
			}
			if (!updateAssembly.get().isEmpty()) {
				parts.add(functionId);
				addRelatedObjects(updateAssembly.get(), DCT_HAS_PART, parts);
			}
		}

		if (addParts.isPresent()) {
			addRelatedObjects(functionId, DCT_HAS_PART, addParts.get());
		}

		if (removeParts.isPresent()) {
			removeRelatedObjects(functionId, DCT_HAS_PART, removeParts.get());
		}

		return (Function) build(YacserObjectType.Function, functionId);
	}

	/**
	 * Update Hamburger object
	 * 
	 * @param hamburgerId             Hamburger ID.
	 * @param updateName              If present, updated name.
	 * @param updateDescription       If present, additional requirements
	 * @param updateFunctionalUnit    If present, updated functional unit reference.
	 * @param updateTechnicalSolution If present, updated technical solution
	 *                                reference.
	 * @param addPorts                If present, add realisation ports.
	 * @param removePorts             If present, remove realisation ports.
	 * @param updateAssembly          If present, updated assembly function.
	 * @param addParts                If present, add part hamburgers.
	 * @param removeParts             If present, remove part hamburgers.
	 * @return Updated Hamburger object
	 * @throws IOException
	 */
	public Hamburger updateHamburger(String hamburgerId, Optional<String> updateName,
			Optional<String> updateDescription, Optional<String> updateFunctionalUnit,
			Optional<String> updateTechnicalSolution, Optional<List<String>> addPorts,
			Optional<List<String>> removePorts, Optional<String> updateAssembly, Optional<List<String>> addParts,
			Optional<List<String>> removeParts) throws IOException {

		if (updateName.isPresent()) {
			updateLiteral(hamburgerId, SKOS_PREF_LABEL, updateName.get());
		}

		if (updateDescription.isPresent()) {
			updateLiteral(hamburgerId, DB_DESCRIPTION, updateDescription.get());
		}

		if (updateFunctionalUnit.isPresent()) {
			updateRelatedObject(hamburgerId, YACSER_FUNCTIONAL_UNIT, updateFunctionalUnit.get());
		}

		if (updateTechnicalSolution.isPresent()) {
			updateRelatedObject(hamburgerId, YACSER_TECHNICAL_SOLUTION, updateTechnicalSolution.get());
		}

		if (addPorts.isPresent()) {
			addRelatedObjects(hamburgerId, YACSER_HAS_PORT_REALISATION, addPorts.get());
		}

		if (removePorts.isPresent()) {
			removeRelatedObjects(hamburgerId, YACSER_HAS_PORT_REALISATION, removePorts.get());
		}

		if (updateAssembly.isPresent()) {
			List<String> parts = new ArrayList<>();
			String oldAssemblyId = getRelatedSubject(hamburgerId, DCT_HAS_PART);
			if (oldAssemblyId != null) {
				removeRelatedObject(oldAssemblyId, DCT_HAS_PART, hamburgerId);
			}
			if (!updateAssembly.get().isEmpty()) {
				parts.add(hamburgerId);
				addRelatedObjects(updateAssembly.get(), DCT_HAS_PART, parts);
			}
		}

		if (addParts.isPresent()) {
			addRelatedObjects(hamburgerId, DCT_HAS_PART, addParts.get());
		}

		if (removeParts.isPresent()) {
			removeRelatedObjects(hamburgerId, DCT_HAS_PART, removeParts.get());
		}

		return (Hamburger) build(YacserObjectType.Hamburger, hamburgerId);
	}

	/**
	 * Update Performance object
	 * 
	 * @param performanceId     Performance ID.
	 * @param updateName        If present, updated name.
	 * @param updateDescription If present, updated description.
	 * @param updateValue       If present, updated value.
	 * @return Updated Performance object.
	 * @throws IOException
	 */
	public Performance updatePerformance(String performanceId, Optional<String> updateName,
			Optional<String> updateDescription, Optional<String> updateValue) throws IOException {

		if (updateName.isPresent()) {
			updateLiteral(performanceId, SKOS_PREF_LABEL, updateName.get());
		}

		if (updateDescription.isPresent()) {
			updateLiteral(performanceId, DB_DESCRIPTION, updateDescription.get());
		}

		if (updateValue.isPresent()) {
			updateRelatedObject(performanceId, YACSER_HAS_VALUE, updateValue.get());
		}

		return (Performance) build(YacserObjectType.Performance, performanceId);
	}

	/**
	 * Update PortRealisation
	 * 
	 * @param portRealisationId PortRealisation ID.
	 * @param updateName        If present, updated name.
	 * @param updateDescription If present, updated description.
	 * @param updateInterface   If present, updated system interface reference.
	 * @param updatePort        If present, updated realisation port reference.
	 * @param updateAssembly    If present, updated assembly port realisation.
	 * @param addParts          If present, additional part port realisations.
	 * @param removeParts       If present, remove part port realisations.
	 * @return PortRealisation object
	 * @throws IOException
	 */
	public PortRealisation updatePortRealisation(String portRealisationId, Optional<String> updateName,
			Optional<String> updateDescription, Optional<String> updateInterface, Optional<String> updatePort,
			Optional<String> updateAssembly, Optional<List<String>> addParts, Optional<List<String>> removeParts)
			throws IOException {

		if (updateName.isPresent()) {
			updateLiteral(portRealisationId, SKOS_PREF_LABEL, updateName.get());
		}

		if (updateDescription.isPresent()) {
			updateLiteral(portRealisationId, DB_DESCRIPTION, updateDescription.get());
		}

		if (updateInterface.isPresent()) {
			updateRelatedObject(portRealisationId, YACSER_INTERFACE, updateInterface.get());
		}

		if (updatePort.isPresent()) {
			updateRelatedObject(portRealisationId, YACSER_PORT, updatePort.get());
		}

		if (updateAssembly.isPresent()) {
			List<String> parts = new ArrayList<>();
			String oldAssemblyId = getRelatedSubject(portRealisationId, DCT_HAS_PART);
			if (oldAssemblyId != null) {
				removeRelatedObject(oldAssemblyId, DCT_HAS_PART, portRealisationId);
			}
			if (!updateAssembly.get().isEmpty()) {
				parts.add(portRealisationId);
				addRelatedObjects(updateAssembly.get(), DCT_HAS_PART, parts);
			}
		}

		if (addParts.isPresent()) {
			addRelatedObjects(portRealisationId, DCT_HAS_PART, addParts.get());
		}

		if (removeParts.isPresent()) {
			removeRelatedObjects(portRealisationId, DCT_HAS_PART, removeParts.get());
		}

		return (PortRealisation) build(YacserObjectType.PortRealisation, portRealisationId);
	}

	/**
	 * Update RealisationModule
	 * 
	 * @param realisationModuleId RealisationModule ID.
	 * @param updateName          If present, updated name.
	 * @param updateDescription   If present, updated description.
	 * @param addPerformances     If present, additional performances.
	 * @param updateAssembly      If present, updated assembly realisation module.
	 * @param addParts            If present, additional part realisation modules.
	 * @return RealisationModule object
	 * @throws IOException
	 */
	public RealisationModule updateRealisationModule(String realisationModuleId, Optional<String> updateName,
			Optional<String> updateDescription, Optional<List<String>> addPerformances,
			Optional<List<String>> removePerformances, Optional<List<String>> addPorts,
			Optional<List<String>> removePorts, Optional<String> updateAssembly, Optional<List<String>> addParts,
			Optional<List<String>> removeParts) throws IOException {

		if (updateName.isPresent()) {
			updateLiteral(realisationModuleId, SKOS_PREF_LABEL, updateName.get());
		}

		if (updateDescription.isPresent()) {
			updateLiteral(realisationModuleId, DB_DESCRIPTION, updateDescription.get());
		}

		if (addPerformances.isPresent()) {
			addRelatedObjects(realisationModuleId, YACSER_HAS_PERFORMANCE, addPerformances.get());
		}

		if (removePerformances.isPresent()) {
			removeRelatedObjects(realisationModuleId, YACSER_HAS_PERFORMANCE, removePerformances.get());
		}

		if (addPorts.isPresent()) {
			addRelatedObjects(realisationModuleId, YACSER_HAS_PORT, addPorts.get());
		}

		if (removePorts.isPresent()) {
			removeRelatedObjects(realisationModuleId, YACSER_HAS_PORT, removePorts.get());
		}

		if (updateAssembly.isPresent()) {
			List<String> parts = new ArrayList<>();
			String oldAssemblyId = getRelatedSubject(realisationModuleId, DCT_HAS_PART);
			if (oldAssemblyId != null) {
				removeRelatedObject(oldAssemblyId, DCT_HAS_PART, realisationModuleId);
			}
			if (!updateAssembly.get().isEmpty()) {
				parts.add(realisationModuleId);
				addRelatedObjects(updateAssembly.get(), DCT_HAS_PART, parts);
			}
		}

		if (addParts.isPresent()) {
			addRelatedObjects(realisationModuleId, DCT_HAS_PART, addParts.get());
		}

		if (removeParts.isPresent()) {
			removeRelatedObjects(realisationModuleId, DCT_HAS_PART, removeParts.get());
		}

		return (RealisationModule) build(YacserObjectType.RealisationModule, realisationModuleId);
	}

	/**
	 * Update RealisationPort
	 * 
	 * @param realisationPortId RealisationPort ID.
	 * @param updateName        If present, updated name.
	 * @param updateDescription If present, updated description.
	 * @param updateAssembly    If present, updated assembly realisation port.
	 * @param addParts          If present, additional part realisation ports.
	 * @param removeParts       If present, remove part realisation ports.
	 * @return RealisationPort object
	 * @throws IOException
	 */
	public RealisationPort updateRealisationPort(String realisationPortId, Optional<String> updateName,
			Optional<String> updateDescription, Optional<String> updateAssembly, Optional<List<String>> addParts,
			Optional<List<String>> removeParts) throws IOException {

		if (updateName.isPresent()) {
			updateLiteral(realisationPortId, SKOS_PREF_LABEL, updateName.get());
		}

		if (updateDescription.isPresent()) {
			updateLiteral(realisationPortId, DB_DESCRIPTION, updateDescription.get());
		}

		if (updateAssembly.isPresent()) {
			List<String> parts = new ArrayList<>();
			String oldAssemblyId = getRelatedSubject(realisationPortId, DCT_HAS_PART);
			if (oldAssemblyId != null) {
				removeRelatedObject(oldAssemblyId, DCT_HAS_PART, realisationPortId);
			}
			if (!updateAssembly.get().isEmpty()) {
				parts.add(realisationPortId);
				addRelatedObjects(updateAssembly.get(), DCT_HAS_PART, parts);
			}
		}

		if (addParts.isPresent()) {
			addRelatedObjects(realisationPortId, DCT_HAS_PART, addParts.get());
		}

		if (removeParts.isPresent()) {
			removeRelatedObjects(realisationPortId, DCT_HAS_PART, removeParts.get());
		}

		return (RealisationPort) build(YacserObjectType.RealisationPort, realisationPortId);
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
			updateLiteral(requirementId, SKOS_PREF_LABEL, updateName.get());
		}

		if (updateDescription.isPresent()) {
			updateLiteral(requirementId, DB_DESCRIPTION, updateDescription.get());
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
	 * @param updateAssembly    If present, updated assembly system interface.
	 * @param addParts          If present, additional part system interfaces.
	 * @return SystemInterface object
	 * @throws IOException
	 */
	public SystemInterface updateSystemInterface(String systemInterfaceId, Optional<String> updateName,
			Optional<String> updateDescription, Optional<String> updateSystemSlot0, Optional<String> updateSystemSlot1,
			Optional<String> updateAssembly, Optional<List<String>> addParts, Optional<List<String>> removeParts)
			throws IOException {

		if (updateName.isPresent()) {
			updateLiteral(systemInterfaceId, SKOS_PREF_LABEL, updateName.get());
		}

		if (updateDescription.isPresent()) {
			updateLiteral(systemInterfaceId, DB_DESCRIPTION, updateDescription.get());
		}

		if (updateSystemSlot0.isPresent()) {
			updateRelatedObject(systemInterfaceId, YACSER_SYSTEM_SLOT_0, updateSystemSlot0.get());
		}

		if (updateSystemSlot1.isPresent()) {
			updateRelatedObject(systemInterfaceId, YACSER_SYSTEM_SLOT_1, updateSystemSlot1.get());
		}

		if (updateAssembly.isPresent()) {
			List<String> parts = new ArrayList<>();
			String oldAssemblyId = getRelatedSubject(systemInterfaceId, DCT_HAS_PART);
			if (oldAssemblyId != null) {
				removeRelatedObject(oldAssemblyId, DCT_HAS_PART, systemInterfaceId);
			}
			if (!updateAssembly.get().isEmpty()) {
				parts.add(systemInterfaceId);
				addRelatedObjects(updateAssembly.get(), DCT_HAS_PART, parts);
			}
		}

		if (addParts.isPresent()) {
			addRelatedObjects(systemInterfaceId, DCT_HAS_PART, addParts.get());
		}

		if (removeParts.isPresent()) {
			removeRelatedObjects(systemInterfaceId, DCT_HAS_PART, removeParts.get());
		}

		return (SystemInterface) build(YacserObjectType.SystemInterface, systemInterfaceId);
	}

	/**
	 * Update SystemSlot
	 * 
	 * @param systemSlotId
	 * @param updateName
	 * @param addFunctions
	 * @param updateAssembly If present, updated assembly system slot.
	 * @param addParts       If present, additional part system slots.
	 * @return SystemSlot object
	 * @throws IOException
	 */
	public SystemSlot updateSystemSlot(String systemSlotId, Optional<String> updateName,
			Optional<String> updateDescription, Optional<List<String>> addFunctions,
			Optional<List<String>> removeFunctions, Optional<String> updateAssembly, Optional<List<String>> addParts,
			Optional<List<String>> removeParts) throws IOException {

		if (updateName.isPresent()) {
			updateLiteral(systemSlotId, SKOS_PREF_LABEL, updateName.get());
		}

		if (updateDescription.isPresent()) {
			updateLiteral(systemSlotId, DB_DESCRIPTION, updateDescription.get());
		}

		if (addFunctions.isPresent()) {
			addRelatedObjects(systemSlotId, YACSER_HAS_FUNCTION, addFunctions.get());
		}

		if (removeFunctions.isPresent()) {
			removeRelatedObjects(systemSlotId, YACSER_HAS_FUNCTION, removeFunctions.get());
		}

		if (updateAssembly.isPresent()) {
			List<String> parts = new ArrayList<>();
			String oldAssemblyId = getRelatedSubject(systemSlotId, DCT_HAS_PART);
			if (oldAssemblyId != null) {
				removeRelatedObject(oldAssemblyId, DCT_HAS_PART, systemSlotId);
			}
			if (!updateAssembly.get().isEmpty()) {
				parts.add(systemSlotId);
				addRelatedObjects(updateAssembly.get(), DCT_HAS_PART, parts);
			}
		}

		if (addParts.isPresent()) {
			addRelatedObjects(systemSlotId, DCT_HAS_PART, addParts.get());
		}

		if (removeParts.isPresent()) {
			removeRelatedObjects(systemSlotId, DCT_HAS_PART, removeParts.get());
		}

		return (SystemSlot) build(YacserObjectType.SystemSlot, systemSlotId);
	}

	/**
	 * Update Value
	 * 
	 * @param valueId
	 * @param updateName
	 * @param updateDescription
	 * @param updateUnit
	 * @param updateValue
	 * @return Updated Value
	 * @throws IOException
	 */
	public Value updateValue(String valueId, Optional<String> updateName, Optional<String> updateDescription,
			Optional<String> updateUnit, Optional<Double> updateValue) throws IOException {
		if (updateName.isPresent()) {
			updateLiteral(valueId, SKOS_PREF_LABEL, updateName.get());
		}

		if (updateDescription.isPresent()) {
			updateLiteral(valueId, DB_DESCRIPTION, updateDescription.get());
		}

		if (updateUnit.isPresent()) {
			updateLiteral(valueId, BS_HAS_UNIT, updateUnit.get());
		}

		if (updateValue.isPresent()) {
			updateLiteral(valueId, BS_HAS_VALUE, updateValue.get());
		}

		return (Value) build(YacserObjectType.Value, valueId);
	}

}
