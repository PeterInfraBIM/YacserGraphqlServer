package nl.infrabim.yacser.graphQLserver.graphql;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

@Component
public class Query implements GraphQLQueryResolver {
	
	private final YacserModelRepository yacserModelRepository;

	public Query(YacserModelRepository yacserModelRepository) {
		this.yacserModelRepository = yacserModelRepository;
	}

	public String test() {
		return "YACSER GraphQL server is working.";
	}

	public List<YacserModel> getAllModels() throws IOException {
		return yacserModelRepository.getAllModels();
	}
}
