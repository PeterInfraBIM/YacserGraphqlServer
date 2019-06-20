package nl.infrabim.yacser.graphQLserver.graphql;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

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
	public YacserObject createObject(String modelId, YacserObjectType type, Optional<String> objectName) throws IOException {
		return yacserObjectRepository.createObject(modelId, type, objectName);
	}

}
