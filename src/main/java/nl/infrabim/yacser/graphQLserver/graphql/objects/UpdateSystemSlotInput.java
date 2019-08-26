package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.util.List;

public class UpdateSystemSlotInput {
	private String systemSlotId; // SystemSlot ID.
	private String updateName; // If present: new name, "" will remove the name.
	private String updateDescription; // If present: new description, "" will remove the description.
	private List<String> addFunctions; // If present: add functions.
	private List<String> removeFunctions; // If present: remove functions.
	private List<String> addRequirements; // If present: add requirements.
	private List<String> removeRequirements; // If present: remove requirements.
	private String updateAssembly; // If present: new assembly, "" will remove the reference.
	private List<String> addParts; // If present: add parts.
	private List<String> removeParts; // If present: remove parts.

	public UpdateSystemSlotInput() {
	}

	public String getSystemSlotId() {
		return systemSlotId;
	}

	public void setSystemSlotId(String systemSlotId) {
		this.systemSlotId = systemSlotId;
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

	public List<String> getAddFunctions() {
		return addFunctions;
	}

	public void setAddFunctions(List<String> addFunctions) {
		this.addFunctions = addFunctions;
	}

	public List<String> getRemoveFunctions() {
		return removeFunctions;
	}

	public void setRemoveFunctions(List<String> removeFunctions) {
		this.removeFunctions = removeFunctions;
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
