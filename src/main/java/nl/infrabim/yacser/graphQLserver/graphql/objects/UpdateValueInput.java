package nl.infrabim.yacser.graphQLserver.graphql.objects;

public class UpdateValueInput {
	private String valueId; // Value ID.
	private String updateName; // If present: new name, "" will remove the name.
	private String updateDescription; // If present: new description, "" will remove the description.
	private String updateUnit; // If present: new unit, "" will remove the unit.
	private Double updateValue; // If present: new value.

	public UpdateValueInput() {
	}

	public String getValueId() {
		return valueId;
	}

	public void setValueId(String valueId) {
		this.valueId = valueId;
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

	public String getUpdateUnit() {
		return updateUnit;
	}

	public void setUpdateUnit(String updateUnit) {
		this.updateUnit = updateUnit;
	}

	public Double getUpdateValue() {
		return updateValue;
	}

	public void setUpdateValue(Double updateValue) {
		this.updateValue = updateValue;
	}

}
