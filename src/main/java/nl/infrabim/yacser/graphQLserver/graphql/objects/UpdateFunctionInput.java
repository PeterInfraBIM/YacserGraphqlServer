package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.util.List;

public class UpdateFunctionInput {
	private String functionId; // Function ID.
	private String updateName; // If present: new name, "" will remove the name.
	private List<String> addRequirements; // If present: add requirements.

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

	public List<String> getAddRequirements() {
		return addRequirements;
	}

	public void setAddRequirements(List<String> addRequirements) {
		this.addRequirements = addRequirements;
	}

}
