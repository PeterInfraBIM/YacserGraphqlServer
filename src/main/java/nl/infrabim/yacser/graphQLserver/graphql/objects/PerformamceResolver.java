package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;

@Component
public class PerformamceResolver extends YacserObjectResolver implements GraphQLResolver<Performance> {

	public PerformamceResolver() {
	}

	public String getName(Performance performance) throws IOException {
		return super.getName(performance);
	}

	public String getDescription(Performance performance) throws IOException {
		return super.getDescription(performance);
	}

	public YacserObjectType getType(Performance performance) throws IOException {
		return super.getType(performance);
	}

	public RealisationModule getOwner(Performance performance) throws IOException {
		String ownerId = YacserObjectRepository.getRelatedSubject(performance.getId(),
				YacserObjectRepository.YACSER_HAS_PERFORMANCE);
		if (ownerId != null) {
			return (RealisationModule) YacserObjectRepository.build(YacserObjectType.RealisationModule, ownerId);
		} else {
			return null;
		}
	}

	public Value getValue(Performance performance) throws IOException {
		String valueId = YacserObjectRepository.getRelatedObject(performance.getId(),
				YacserObjectRepository.YACSER_HAS_VALUE);
		return (Value) YacserObjectRepository.build(YacserObjectType.Value, valueId);
	}

}
