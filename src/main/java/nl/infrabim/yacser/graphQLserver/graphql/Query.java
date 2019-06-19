package nl.infrabim.yacser.graphQLserver.graphql;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

@Component
public class Query implements GraphQLQueryResolver {

	public String test() {
		return "YACSER GraphQL server is working.";
	}
}
