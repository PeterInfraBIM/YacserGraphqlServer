package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;

@Component
public class HamburgerResolver extends YacserObjectResolver implements GraphQLResolver<Hamburger> {
	
	public HamburgerResolver() {
	}

	public String getName(Hamburger hamburger) throws IOException {
		return super.getName(hamburger);
	}

	public YacserObjectType getType(Hamburger hamburger) throws IOException {
		return super.getType(hamburger);
	}
}
