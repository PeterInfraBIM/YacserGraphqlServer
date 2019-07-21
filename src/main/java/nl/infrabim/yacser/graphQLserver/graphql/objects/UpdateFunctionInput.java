package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.util.List;

public class UpdateFunctionInput {
	private String functionId; // Function ID.
	private String updateName; // If present: new name, "" will remove the name.
	private String updateDescription; // If present: new description, "" will remove the description.
	private List<String> addRequirements; // If present: add requirements.
	private List<String> removeRequirements; // If present: remove requirements.
	private String updateInput; // If present: new input system interface, "" will remove the reference.
	private String updateOutput; // If present: new output system interface, "" will remove the reference.
	private String updateAssembly; // If present: new assembly, "" will remove the reference.
	private List<String> addParts; // If present: add parts.
	private List<String> removeParts; // If present: remove parts.

	public UpdateFunctionInput() {
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
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

	public List<String> getAddRequirements() {
		return addRequirements;
	}

	public void setAddRequirements(List<String> addRequirements) {
		this.addRequirements = addRequirements;
	}

	public List<String> getRemoveRequirements() {
		return removeRequirements;
	}

	public void setRemoveRequirements(List<String> removeRequirements) {
		this.removeRequirements = removeRequirements;
	}

	public String getUpdateInput() {
		return updateInput;
	}

	public void setUpdateInput(String updateInput) {
		this.updateInput = updateInput;
	}

	public String getUpdateOutput() {
		return updateOutput;
	}

	public void setUpdateOutput(String updateOutput) {
		this.updateOutput = updateOutput;
	}

	public String getUpdateAssembly() {
		return updateAssembly;
	}

	public void setUpdateAssembly(String updateAssembly) {
		this.updateAssembly = updateAssembly;
	}

	public List<String> getAddParts() {
		return addParts;
	}

	public void setAddParts(List<String> addParts) {
		this.addParts = addParts;
	}

	public List<String> getRemoveParts() {
		return removeParts;
	}

	public void setRemoveParts(List<String> removeParts) {
		this.removeParts = removeParts;
	}

}
