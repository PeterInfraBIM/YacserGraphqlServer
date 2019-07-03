package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;

@Component
public class RealisationModuleResolver extends YacserObjectResolver implements GraphQLResolver<RealisationModule> {

	public RealisationModuleResolver() {
	}

	public String getName(RealisationModule realisationModule) throws IOException {
		return super.getName(realisationModule);
	}

	public String getDescription(RealisationModule realisationModule) throws IOException {
		return super.getDescription(realisationModule);
	}

	public YacserObjectType getType(RealisationModule realisationModule) throws IOException {
		return super.getType(realisationModule);
	}

}
