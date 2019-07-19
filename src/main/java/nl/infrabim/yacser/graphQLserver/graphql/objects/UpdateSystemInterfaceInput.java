package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.util.List;

public class UpdateSystemInterfaceInput {
	private String systemInterfaceId; // SystemInterface ID.
	private String updateName; // If present: new name, "" will remove the name.
	private String updateDescription; // If present: new description, "" will remove the description.
	private String updateSystemSlot0; // If present: update SystemSlot 0, "" will remove the reference.
	private String updateSystemSlot1; // If present: update SystemSlot 1, "" will remove the reference.
	private String updateAssembly; // If present: new assembly, "" will remove the reference.
	private List<String> addParts; // If present: add parts.

	public UpdateSystemInterfaceInput() {
	}

	public String getSystemInterfaceId() {
		return systemInterfaceId;
	}

	public void setSystemInterfaceId(String systemSlotId) {
		this.systemInterfaceId = systemSlotId;
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

	public String getUpdateSystemSlot0() {
		return updateSystemSlot0;
	}

	public void setUpdateSystemSlot0(String updateSystemSlot0) {
		this.updateSystemSlot0 = updateSystemSlot0;
	}

	public String getUpdateSystemSlot1() {
		return updateSystemSlot1;
	}

	public void setUpdateSystemSlot1(String updateSystemSlot1) {
		this.updateSystemSlot1 = updateSystemSlot1;
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

}
