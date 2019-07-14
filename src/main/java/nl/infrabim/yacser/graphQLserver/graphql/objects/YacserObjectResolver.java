package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

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

	/**
	 * Get the ID of the assembly object.
	 * 
	 * @param yacserObject the YACSER object instance
	 * @return the assembly ID or null
	 * @throws IOException
	 */
	public String getAssemblyId(YacserObject yacserObject) throws IOException {
		return YacserObjectRepository.getRelatedSubject(yacserObject.getId(), YacserObjectRepository.DCT_HAS_PART);
	}

	/**
	 * Get the IDs of the part objects.
	 * 
	 * @param yacserObject the YACSER object instance
	 * @return the part IDs or null
	 * @throws IOException
	 */
	public List<String> getPartIds(YacserObject yacserObject) throws IOException {
		return YacserObjectRepository.getRelatedObjects(yacserObject.getId(), YacserObjectRepository.DCT_HAS_PART);
	}
}
