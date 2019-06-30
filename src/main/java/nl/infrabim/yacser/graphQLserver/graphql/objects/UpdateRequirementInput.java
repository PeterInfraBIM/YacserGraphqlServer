package nl.infrabim.yacser.graphQLserver.graphql.objects;

public class UpdateRequirementInput {
	private String requirementId; // Requirement ID.
	private String updateName; // If present: new name, "" will remove the name.
	private String updateDescription; // If present: new description, "" will remove the description.

	public UpdateRequirementInput() {
	}

	public String getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(String functionId) {
		this.requirementId = functionId;
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
