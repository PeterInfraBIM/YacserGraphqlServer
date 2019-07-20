package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.util.List;

public class UpdateRealisationModuleInput {
	private String realisationModuleId; // RealisationModule ID.
	private String updateName; // If present: new name, "" will remove the name.
	private String updateDescription; // If present: new description, "" will remove the description.
	private List<String> addPerformances; // If present: add performances.
	private String updateAssembly; // If present: new assembly, "" will remove the reference.
	private List<String> addParts; // If present: add parts.
	private List<String> removeParts; // If present: remove parts.

	public UpdateRealisationModuleInput() {
	}

	public String getRealisationModuleId() {
		return realisationModuleId;
	}

	public void setRealisationModuleId(String realisationModuleId) {
		this.realisationModuleId = realisationModuleId;
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

	public List<String> getAddPerformances() {
		return addPerformances;
	}

	public void setAddPerformances(List<String> addPerformanes) {
		this.addPerformances = addPerformanes;
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
