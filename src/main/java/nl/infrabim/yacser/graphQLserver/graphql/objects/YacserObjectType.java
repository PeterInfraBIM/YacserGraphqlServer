package nl.infrabim.yacser.graphQLserver.graphql.objects;

public enum YacserObjectType {
	Function, Hamburger, Performance, PortRealisation, RealisationModule, RealisationPort, Requirement, SystemInterface,
	SystemSlot, Value;

	/**
	 * Transform uri string to YacserObjectType value.
	 * 
	 * @param uri Object type uri
	 * @return YacserObjectType value
	 */
	public static YacserObjectType getType(String uri) {
		return YacserObjectType.valueOf(uri.substring(uri.indexOf('#') + 1));
	}
}
