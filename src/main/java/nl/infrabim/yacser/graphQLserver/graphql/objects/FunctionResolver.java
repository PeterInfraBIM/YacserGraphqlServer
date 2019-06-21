package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;

@Component
public class FunctionResolver extends YacserObjectResolver implements GraphQLResolver<Function> {

	public FunctionResolver() {
	}

	public String getName(Function function) throws IOException {
		return super.getName(function);
	}

	public YacserObjectType getType(Function function) throws IOException {
		return super.getType(function);
	}
}
