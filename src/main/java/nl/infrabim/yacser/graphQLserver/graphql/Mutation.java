package nl.infrabim.yacser.graphQLserver.graphql;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import nl.infrabim.yacser.graphQLserver.graphql.objects.SystemInterface;
import nl.infrabim.yacser.graphQLserver.graphql.objects.SystemSlot;
import nl.infrabim.yacser.graphQLserver.graphql.objects.UpdateSystemInterfaceInput;
import nl.infrabim.yacser.graphQLserver.graphql.objects.UpdateSystemSlotInput;
import nl.infrabim.yacser.graphQLserver.graphql.objects.YacserObject;
import nl.infrabim.yacser.graphQLserver.graphql.objects.YacserObjectRepository;
import nl.infrabim.yacser.graphQLserver.graphql.objects.YacserObjectType;

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
	public YacserModel createModel(String modelId, Optional<String> modelName) {
		return yacserModelRepository.createModel(modelId, modelName);
	}

	/**
	 * @param modelId    Base URI of the model.
	 * @param type       Subtype of YACSER object.
	 * @param objectName Optional name of the object.
	 * @return
	 * @throws IOException
	 */
	public YacserObject createObject(String modelId, YacserObjectType type, Optional<String> objectName)
			throws IOException {
		return yacserObjectRepository.createObject(modelId, type, objectName);
	}

	/**
	 * @param input Input arguments for updating the SystemInterface object
	 * @return SystemInterface object
	 * @throws IOException
	 */
	public SystemInterface updateSystemInterface(UpdateSystemInterfaceInput input) throws IOException {
		return yacserObjectRepository.updateSystemInterface(input.getSystemInterfaceId(),
				Optional.ofNullable(input.getUpdateName()), Optional.ofNullable(input.getUpdateSystemSlot0()),
				Optional.ofNullable(input.getUpdateSystemSlot1()));
	}

	/**
	 * @param input Input arguments for updating the SystemSlot object
	 * @return SystemSlot object
	 * @throws IOException
	 */
	public SystemSlot updateSystemSlot(UpdateSystemSlotInput input) throws IOException {
		return yacserObjectRepository.updateSystemSlot(input.getSystemSlotId(),
				Optional.ofNullable(input.getUpdateName()), Optional.ofNullable(input.getAddFunctions()));
	}

}
