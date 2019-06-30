package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;

@Component
public class ValueResolver extends YacserObjectResolver implements GraphQLResolver<Value> {

	public ValueResolver() {
	}

	public String getName(Value value) throws IOException {
		return super.getName(value);
	}

	public String getDescription(Value value) throws IOException {
		return super.getDescription(value);
	}

	public YacserObjectType getType(Value value) throws IOException {
		return super.getType(value);
	}
	
	public String getUnit(Value value) throws IOException {
		return null;
	}
	
	public Double getValue(Value value) throws IOException {
		return null;
	}
}
