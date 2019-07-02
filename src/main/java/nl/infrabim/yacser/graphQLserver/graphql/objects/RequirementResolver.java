package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;

@Component
public class RequirementResolver extends YacserObjectResolver implements GraphQLResolver<Requirement> {

	public RequirementResolver() {
	}

	public String getName(Requirement requirement) throws IOException {
		return super.getName(requirement);
	}

	public String getDescription(Requirement requirement) throws IOException {
		return super.getDescription(requirement);
	}

	public YacserObjectType getType(Requirement requirement) throws IOException {
		return super.getType(requirement);
	}

	public YacserObject getOwner(Requirement requirement) throws IOException {
		String ownerId = YacserObjectRepository.getRelatedSubject(requirement.getId(),
				YacserObjectRepository.YACSER_HAS_REQUIREMENT);
		if (ownerId != null) {
			String typeId = YacserObjectRepository.getType(ownerId);
			return YacserObjectRepository.build(YacserObjectType.getType(typeId), ownerId);
		} else {
			return null;
		}
	}

	public Value getMinValue(Requirement requirement) throws IOException {
		String minValueId = YacserObjectRepository.getRelatedObject(requirement.getId(),
				YacserObjectRepository.YACSER_HAS_MIN_VALUE);
		return (Value) YacserObjectRepository.build(YacserObjectType.Value, minValueId);
	}

	public Value getMaxValue(Requirement requirement) throws IOException {
		String maxValueId = YacserObjectRepository.getRelatedObject(requirement.getId(),
				YacserObjectRepository.YACSER_HAS_MAX_VALUE);
		return (Value) YacserObjectRepository.build(YacserObjectType.Value, maxValueId);
	}
}
