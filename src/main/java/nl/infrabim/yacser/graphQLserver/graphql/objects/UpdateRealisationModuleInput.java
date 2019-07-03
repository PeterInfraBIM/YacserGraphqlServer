package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.util.List;

public class UpdateRealisationModuleInput {
	private String realisationModuleId; // RealisationModule ID.
	private String updateName; // If present: new name, "" will remove the name.
	private String updateDescription; // If present: new description, "" will remove the description.
	private List<String> addPerformances; // If present: add performances.

	public UpdateRealisationModuleInput() {
	}

	public String getRealisationModuleId() {
		return realisationModuleId;
	}

	public void setRealisationModule(String realisationModuleId) {
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

}
