package nl.infrabim.yacser.graphQLserver.graphql;

public class YacserModel {
	private String id;

	public YacserModel() {
	}

	public YacserModel(String id) {
		this();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	void setId(String id) {
		this.id = id;
	}

}
