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
		List<Performance> performances = null;
		List<String> performanceIds = YacserObjectRepository.getRelatedObjects(realisationModule.getId(),
				YacserObjectRepository.YACSER_HAS_PERFORMANCE);
		if (performanceIds != null) {
			performances = new ArrayList<>();
			for (String performanceId : performanceIds) {
				performances
						.add((Performance) YacserObjectRepository.build(YacserObjectType.Performance, performanceId));
			}
		}
		return performances;
	}

	public List<Hamburger> getHamburgers(RealisationModule realisationModule) throws IOException {
		List<Hamburger> hamburgers = null;
		List<String> hamburgerIds = YacserObjectRepository.getRelatedSubjects(realisationModule.getId(),
				YacserObjectRepository.YACSER_TECHNICAL_SOLUTION);
		if (hamburgerIds != null) {
			hamburgers = new ArrayList<>();
			for (String hamburgerId : hamburgerIds) {
				hamburgers.add((Hamburger) YacserObjectRepository.build(YacserObjectType.Hamburger, hamburgerId));
			}
		}
		return hamburgers;
	}

}
