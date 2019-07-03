package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	public List<Performance> getPerformances(RealisationModule realisationModule) throws IOException {
		List<String> performanceIds = YacserObjectRepository.getRelatedObjects(realisationModule.getId(),
				YacserObjectRepository.YACSER_HAS_PERFORMANCE);
		if (performanceIds != null) {
			List<Performance> performances = new ArrayList<>();
			for (String performanceId : performanceIds) {
				performances
						.add((Performance) YacserObjectRepository.build(YacserObjectType.Performance, performanceId));
			}
			return performances;
		}
		return null;
	}

}
