package nl.infrabim.yacser.graphQLserver.graphql;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import nl.infrabim.yacser.graphQLserver.graphql.objects.Function;
import nl.infrabim.yacser.graphQLserver.graphql.objects.Hamburger;
import nl.infrabim.yacser.graphQLserver.graphql.objects.Requirement;
import nl.infrabim.yacser.graphQLserver.graphql.objects.SystemInterface;
import nl.infrabim.yacser.graphQLserver.graphql.objects.SystemSlot;
import nl.infrabim.yacser.graphQLserver.graphql.objects.YacserObject;
import nl.infrabim.yacser.graphQLserver.graphql.objects.YacserObjectRepository;
import nl.infrabim.yacser.graphQLserver.graphql.objects.YacserObjectType;

@Component
public class Query implements GraphQLQueryResolver {

	private final YacserModelRepository yacserModelRepository;
	private final YacserObjectRepository yacserObjectRepository;

	/**
	 * Constructor with injections.
	 * 
	 * @param yacserModelRepository  YACSER model repository
	 * @param yacserObjectRepository YACSER object repository
	 */
	public Query(YacserModelRepository yacserModelRepository, YacserObjectRepository yacserObjectRepository) {
		this.yacserModelRepository = yacserModelRepository;
		this.yacserObjectRepository = yacserObjectRepository;
	}

	/**
	 * List all currently loaded YACSER models.
	 * 
	 * @return model list
	 * @throws IOException
	 */
	public List<YacserModel> getAllModels() throws IOException {
		return yacserModelRepository.getAllModels();
	}

	/**
	 * List all YACSER objects in the specified model.
	 * 
	 * @param modelId Model base uri
	 * @return object list
	 * @throws IOException
	 */
	public List<YacserObject> getAllObjects(String modelId) throws IOException {
		return yacserObjectRepository.getAllObjects(modelId);
	}

	public Function getFunction(String objectId) {
		return (Function) yacserObjectRepository.getObject(YacserObjectType.Function, objectId);
	}

	public Hamburger getHamburger(String objectId) {
		return (Hamburger) yacserObjectRepository.getObject(YacserObjectType.Hamburger, objectId);
	}

	public Requirement getRequirement(String objectId) {
		return (Requirement) yacserObjectRepository.getObject(YacserObjectType.Function, objectId);
	}

	public SystemInterface getSystemInterface(String objectId) {
		return (SystemInterface) yacserObjectRepository.getObject(YacserObjectType.SystemInterface, objectId);
	}

	public SystemSlot getSystemSlot(String objectId) {
		return (SystemSlot) yacserObjectRepository.getObject(YacserObjectType.SystemSlot, objectId);
	}

}
