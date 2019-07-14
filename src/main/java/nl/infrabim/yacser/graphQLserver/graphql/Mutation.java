package nl.infrabim.yacser.graphQLserver.graphql;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import nl.infrabim.yacser.graphQLserver.graphql.objects.Function;
import nl.infrabim.yacser.graphQLserver.graphql.objects.Hamburger;
import nl.infrabim.yacser.graphQLserver.graphql.objects.Performance;
import nl.infrabim.yacser.graphQLserver.graphql.objects.RealisationModule;
import nl.infrabim.yacser.graphQLserver.graphql.objects.Requirement;
import nl.infrabim.yacser.graphQLserver.graphql.objects.SystemInterface;
import nl.infrabim.yacser.graphQLserver.graphql.objects.SystemSlot;
import nl.infrabim.yacser.graphQLserver.graphql.objects.UpdateFunctionInput;
import nl.infrabim.yacser.graphQLserver.graphql.objects.UpdateHamburgerInput;
import nl.infrabim.yacser.graphQLserver.graphql.objects.UpdatePerformanceInput;
import nl.infrabim.yacser.graphQLserver.graphql.objects.UpdateRealisationModuleInput;
import nl.infrabim.yacser.graphQLserver.graphql.objects.UpdateRequirementInput;
import nl.infrabim.yacser.graphQLserver.graphql.objects.UpdateSystemInterfaceInput;
import nl.infrabim.yacser.graphQLserver.graphql.objects.UpdateSystemSlotInput;
import nl.infrabim.yacser.graphQLserver.graphql.objects.UpdateValueInput;
import nl.infrabim.yacser.graphQLserver.graphql.objects.Value;
import nl.infrabim.yacser.graphQLserver.graphql.objects.YacserObject;
import nl.infrabim.yacser.graphQLserver.graphql.objects.YacserObjectRepository;
import nl.infrabim.yacser.graphQLserver.graphql.objects.YacserObjectType;
import nl.infrabim.yacser.graphQLserver.sparql.SparqlServer;

@Component
public class Mutation implements GraphQLMutationResolver {

	private final YacserModelRepository yacserModelRepository;
	private final YacserObjectRepository yacserObjectRepository;

	/**
	 * Mutation constructor injection
	 * 
	 * @param yacserModelRepository
	 */
	public Mutation(YacserModelRepository yacserModelRepository, YacserObjectRepository yacserObjectRepository) {
		this.yacserModelRepository = yacserModelRepository;
		this.yacserObjectRepository = yacserObjectRepository;
	}

	/**
	 * @param modelId   Base URI of the model.
	 * @param modelName Optional name of the model.
	 * @return
	 */
	public YacserModel createModel(String modelId, Optional<String> modelName, Optional<String> modelDescription) {
		return yacserModelRepository.createModel(modelId, modelName, modelDescription);
	}

	/**
	 * @param modelId    Base URI of the model.
	 * @param type       Subtype of YACSER object.
	 * @param objectName Optional name of the object.
	 * @return
	 * @throws IOException
	 */
	public YacserObject createObject(String modelId, YacserObjectType type, Optional<String> objectName,
			Optional<String> objectDescription) throws IOException {
		return yacserObjectRepository.createObject(modelId, type, objectName, objectDescription);
	}

	/**
	 * @param input Input arguments for updating the Function object
	 * @return Updated Function object
	 * @throws IOException
	 */
	public Function updateFunction(UpdateFunctionInput input) throws IOException {
		return yacserObjectRepository.updateFunction(input.getFunctionId(), Optional.ofNullable(input.getUpdateName()),
				Optional.ofNullable(input.getUpdateDescription()), Optional.ofNullable(input.getAddRequirements()),
				Optional.ofNullable(input.getUpdateInput()), Optional.ofNullable(input.getUpdateOutput()));
	}

	/**
	 * @param input Input arguments for updating the Hamburger object
	 * @return Updated Hamburger object
	 * @throws IOException
	 */
	public Hamburger updateHamburger(UpdateHamburgerInput input) throws IOException {
		return yacserObjectRepository.updateHamburger(input.getHamburgerId(),
				Optional.ofNullable(input.getUpdateName()), Optional.ofNullable(input.getUpdateDescription()),
				Optional.ofNullable(input.getUpdateFunctionalUnit()),
				Optional.ofNullable(input.getUpdateTechnicalSolution()));
	}

	/**
	 * @param input Input arguments for updating the YacserModel object
	 * @return Updated YacserModel object
	 * @throws IOException
	 */
	public YacserModel updateModel(UpdateYacserModelInput input) throws IOException {
		return yacserModelRepository.updateModel(input.getModelId(), Optional.ofNullable(input.getUpdateName()),
				Optional.ofNullable(input.getUpdateDescription()));
	}

	/**
	 * @param input Input arguments for updating the Performance object
	 * @return Updated Performance object
	 * @throws IOException
	 */
	public Performance updatePerformance(UpdatePerformanceInput input) throws IOException {
		return yacserObjectRepository.updatePerformance(input.getPerformanceId(),
				Optional.ofNullable(input.getUpdateName()), Optional.ofNullable(input.getUpdateDescription()),
				Optional.ofNullable(input.getUpdateValue()));
	}

	/**
	 * @param input Input arguments for updating the RealisationModule object
	 * @return Updated RealisationModule object
	 * @throws IOException
	 */
	public RealisationModule updateRealisationModule(UpdateRealisationModuleInput input) throws IOException {
		return yacserObjectRepository.updateRealisationModule(input.getRealisationModuleId(),
				Optional.ofNullable(input.getUpdateName()), Optional.ofNullable(input.getUpdateDescription()),
				Optional.ofNullable(input.getAddPerformances()), Optional.ofNullable(input.getUpdateAssembly()),
				Optional.ofNullable(input.getAddParts()));
	}

	/**
	 * @param input Input arguments for updating the Requirement object
	 * @return Updated Requirement object
	 * @throws IOException
	 */
	public Requirement updateRequirement(UpdateRequirementInput input) throws IOException {
		return yacserObjectRepository.updateRequirement(input.getRequirementId(),
				Optional.ofNullable(input.getUpdateName()), Optional.ofNullable(input.getUpdateDescription()),
				Optional.ofNullable(input.getUpdateMinValue()), Optional.ofNullable(input.getUpdateMaxValue()));
	}

	/**
	 * @param input Input arguments for updating the SystemInterface object
	 * @return Updated SystemInterface object
	 * @throws IOException
	 */
	public SystemInterface updateSystemInterface(UpdateSystemInterfaceInput input) throws IOException {
		return yacserObjectRepository.updateSystemInterface(input.getSystemInterfaceId(),
				Optional.ofNullable(input.getUpdateName()), Optional.ofNullable(input.getUpdateDescription()),
				Optional.ofNullable(input.getUpdateSystemSlot0()), Optional.ofNullable(input.getUpdateSystemSlot1()));
	}

	/**
	 * @param input Input arguments for updating the SystemSlot object
	 * @return Updated SystemSlot object
	 * @throws IOException
	 */
	public SystemSlot updateSystemSlot(UpdateSystemSlotInput input) throws IOException {
		return yacserObjectRepository.updateSystemSlot(input.getSystemSlotId(),
				Optional.ofNullable(input.getUpdateName()), Optional.ofNullable(input.getUpdateDescription()),
				Optional.ofNullable(input.getAddFunctions()));
	}

	/**
	 * @param input Input arguments for updating the Value object
	 * @return Updated Value object
	 * @throws IOException
	 */
	public Value updateValue(UpdateValueInput input) throws IOException {
		return yacserObjectRepository.updateValue(input.getValueId(), Optional.ofNullable(input.getUpdateName()),
				Optional.ofNullable(input.getUpdateDescription()), Optional.ofNullable(input.getUpdateUnit()),
				Optional.ofNullable(input.getUpdateValue()));
	}

	/**
	 * Save model
	 * 
	 * @param modelId  Base URI of the model.
	 * @param filePath File path
	 * @return Success?
	 */
	public boolean saveModel(String modelId, String filePath) {
		try {
			SparqlServer.instance.saveNamedModel(modelId, filePath);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public YacserModel loadModel(String filePath) {
		try {
			String modelId = SparqlServer.instance.loadNamedModel(filePath);
			if (modelId != null) {
				return new YacserModel(modelId);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
