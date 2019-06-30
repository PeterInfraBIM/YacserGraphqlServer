package nl.infrabim.yacser.graphQLserver.graphql.objects;

public class Value extends YacserObject {
	private String valueId;

	public Value() {
	}

	public Value(String valueId) {
		this();
		this.valueId = valueId;
	}

	public String getValueId() {
		return valueId;
	}

	public void setValueId(String valueId) {
		this.valueId = valueId;
	}

}
