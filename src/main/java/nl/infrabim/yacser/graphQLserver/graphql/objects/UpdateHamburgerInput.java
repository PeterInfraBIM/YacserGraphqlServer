package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.util.List;

public class UpdateHamburgerInput {
	private String hamburgerId; // Hamburger ID.
	private String updateName; // If present: new name, "" will remove the name.
	private String updateDescription; // If present: new description, "" will remove the description.
	private String updateFunctionalUnit; // If present: new functional unit, "" will remove the functional unit.
	private String updateTechnicalSolution; // If present: new technical solution, "" will remove the technical
											// solution.
	private List<String> addPorts; // If present: add ports.
	private List<String> removePorts; // If present: remove ports.
	private String updateAssembly; // If present: new assembly, "" will only remove the old assembly.
	private List<String> addParts; // If present: add parts.
	private List<String> removeParts; // If present: remove parts.

	public UpdateHamburgerInput() {
	}

	public String getHamburgerId() {
		return hamburgerId;
	}

	public void setHamburgerId(String hamburgerId) {
		this.hamburgerId = hamburgerId;
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

	public String getUpdateFunctionalUnit() {
		return this.updateFunctionalUnit;
	}

	public void setUpdateFunctionalUnit(String updateFunctionalUnit) {
		this.updateFunctionalUnit = updateFunctionalUnit;
	}

	public String getUpdateTechnicalSolution() {
		return updateTechnicalSolution;
	}

	public void setUpdateTechnicalSolution(String updateTechnicalSolution) {
		this.updateTechnicalSolution = updateTechnicalSolution;
	}

	public List<String> getAddPorts() {
		return addPorts;
	}

	public void setAddPorts(List<String> addPorts) {
		this.addPorts = addPorts;
	}

	public List<String> getRemovePorts() {
		return removePorts;
	}

	public void setRemovePorts(List<String> removePorts) {
		this.removePorts = removePorts;
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
