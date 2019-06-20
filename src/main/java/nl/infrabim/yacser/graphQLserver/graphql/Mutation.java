package nl.infrabim.yacser.graphQLserver.graphql;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

@Component
public class Mutation implements GraphQLMutationResolver {
	
	private final YacserModelRepository yacserModelRepository;

	public Mutation(YacserModelRepository yacserModelRepository) {
		this.yacserModelRepository = yacserModelRepository;
	}

	public YacserModel createModel(String id, String name) {
		return yacserModelRepository.createModel(id, name);
	}

	public YacserObject createObject(YacserObjectType type) {
		return null;
	}

}
