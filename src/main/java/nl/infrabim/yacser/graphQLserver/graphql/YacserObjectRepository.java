package nl.infrabim.yacser.graphQLserver.graphql;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.apache.jena.query.ParameterizedSparqlString;
import org.springframework.stereotype.Component;

import nl.infrabim.yacser.graphQLserver.sparql.SparqlServer;

@Component
public class YacserObjectRepository {

	public YacserObjectRepository() {
	}

	public YacserObject createObject(String modelId, YacserObjectType type, Optional<String> objectName) throws IOException {
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

		YacserObject yacserObject = new YacserObject(objectId);

		return yacserObject;
	}

}
