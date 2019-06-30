package nl.infrabim.yacser.graphQLserver.graphql.objects;

public class UpdateRequirementInput {
	private String requirementId; // Requirement ID.
	private String updateName; // If present: new name, "" will remove the name.
	private String updateDescription; // If present: new description, "" will remove the description.
	private String updateMinValue; // If present: new minimal value.
	private String updateMaxValue; // If present: new maximal value.

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

	public String getUpdateMinValue() {
		return updateMinValue;
	}

	public void setUpdateMinValue(String updateMinValue) {
		this.updateMinValue = updateMinValue;
	}

	public String getUpdateMaxValue() {
		return updateMaxValue;
	}

	public void setUpdateMaxValue(String updateMaxValue) {
		this.updateMaxValue = updateMaxValue;
	}

}
