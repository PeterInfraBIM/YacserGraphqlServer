package nl.infrabim.yacser.graphQLserver.graphql.objects;

public class UpdatePerformanceInput {
	private String performanceId; // Performance ID.
	private String updateName; // If present: new name, "" will remove the name.
	private String updateDescription; // If present: new description, "" will remove the description.
	private String updateValue; // If present: new value.

	public UpdatePerformanceInput() {
	}

	public String getPerformanceId() {
		return performanceId;
	}

	public void setPerformanceId(String functionId) {
		this.performanceId = functionId;
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

	public String getUpdateValue() {
		return updateValue;
	}

	public void setUpdateValue(String updateValue) {
		this.updateValue = updateValue;
	}

}
