package nl.infrabim.yacser.graphQLserver.graphql.objects;

public class UpdateHamburgerInput {
	private String hamburgerId; // Hamburger ID.
	private String updateName; // If present: new name, "" will remove the name.
	private String updateDescription; // If present: new description, "" will remove the description.
	private String updateFunctionalUnit; // If present: new functional unit, "" will remove the functional unit.
	private String updateTechnicalSolution; // If present: new technical solution, "" will remove the technical
											// solution.

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

}
