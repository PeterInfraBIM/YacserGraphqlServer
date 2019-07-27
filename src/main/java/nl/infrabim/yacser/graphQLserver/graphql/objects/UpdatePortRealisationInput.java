package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.util.List;

public class UpdatePortRealisationInput {
	private String portRealisationId; // Function ID.
	private String updateName; // If present: new name, "" will remove the name.
	private String updateDescription; // If present: new description, "" will remove the description.
	private String updateInterface; // If present: new system interface reference, "" will remove the reference.
	private String updatePort; // If present: new realisation port reference, "" will remove the reference.
	private String updateAssembly; // If present: new assembly, "" will remove the reference.
	private List<String> addParts; // If present: add parts.
	private List<String> removeParts; // If present: remove parts.

	public UpdatePortRealisationInput() {
	}

	public String getPortRealisationId() {
		return portRealisationId;
	}

	public void setPortRealisationId(String portRealisationId) {
		this.portRealisationId = portRealisationId;
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

	public String getUpdateInterface() {
		return updateInterface;
	}

	public void setUpdateInterface(String updateInterface) {
		this.updateInterface = updateInterface;
	}

	public String getUpdatePort() {
		return updatePort;
	}

	public void setUpdatePort(String updatePort) {
		this.updatePort = updatePort;
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
