package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;

import org.apache.jena.query.ParameterizedSparqlString;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import nl.infrabim.yacser.graphQLserver.sparql.SparqlServer;

@Component
public class YacserObjectResolver {

	public YacserObjectResolver() {
	}

	/**
	 * Get the name of the YACSER object.
	 * 
	 * @param yacserObject the YACSER object instance
	 * @return the name string
	 * @throws IOException
	 */
	public String getName(YacserObject yacserObject) throws IOException {
		return (String) YacserObjectRepository.getLiteral(yacserObject.getId(), YacserObjectRepository.SKOS_PREF_LABEL);
	}

	/**
	 * Get the description of the YACSER object.
	 * 
	 * @param yacserObject the YACSER object instance
	 * @return the description string
	 * @throws IOException
	 */
	public String getDescription(YacserObject yacserObject) throws IOException {
		return (String) YacserObjectRepository.getLiteral(yacserObject.getId(), YacserObjectRepository.DB_DESCRIPTION);
	}

	/**
	 * Get the type of the YACSER object.
	 * 
	 * @param yacserObject the YACSER object instance
	 * @return the type
	 * @throws IOException
	 */
	public YacserObjectType getType(YacserObject yacserObject) throws IOException {
		String typeId = YacserObjectRepository.getType(yacserObject.getId());
		return typeId != null ? YacserObjectType.getType(typeId) : null;
	}

}
