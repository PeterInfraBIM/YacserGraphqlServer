package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.util.List;

public class UpdateSystemSlotInput {
	private String systemSlotId; // SystemSlot ID.
	private String updateName; // If present: new name, "" will remove the name.
	private String updateDescription; // If present: new description, "" will remove the description.
	private List<String> addFunctions; // If present: add functions.
	private List<String> removeFunctions; // If present: remove functions.
	private List<String> replaceFunctions; // If present: Replace all functions.

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

	public List<String> getReplaceFunctions() {
		return replaceFunctions;
	}

	public void setReplaceFunctions(List<String> replaceFunctions) {
		this.replaceFunctions = replaceFunctions;
	}

}
