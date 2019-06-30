package nl.infrabim.yacser.graphQLserver.graphql;

public class UpdateYacserModelInput {
	private String modelId; // Model ID.
	private String updateName; // If present: new name, "" will remove the name.
	private String updateDescription; // If present: new description, "" will remove the description.

	public UpdateYacserModelInput() {
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getUpdateDescription() {
		return updateDescription;
	}

	public void setUpdateDescription(String updateDescription) {
		this.updateDescription = updateDescription;
	}

}
