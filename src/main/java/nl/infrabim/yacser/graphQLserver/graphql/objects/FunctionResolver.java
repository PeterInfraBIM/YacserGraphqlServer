package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;

@Component
public class FunctionResolver extends YacserObjectResolver implements GraphQLResolver<Function> {

	public FunctionResolver() {
	}

	public String getName(Function function) throws IOException {
		return super.getName(function);
	}

	public String getDescription(Function function) throws IOException {
		return super.getDescription(function);
	}

	public YacserObjectType getType(Function function) throws IOException {
		return super.getType(function);
	}

	public SystemSlot getOwner(Function function) throws IOException {
		String ownerId = YacserObjectRepository.getRelatedSubject(function.getId(),
				YacserObjectRepository.YACSER_HAS_FUNCTION);
		return ownerId != null ? ((SystemSlot) YacserObjectRepository.build(YacserObjectType.SystemSlot, ownerId))
				: null;
	}

	public List<Requirement> getRequirements(Function function) throws IOException {
		List<String> requirementIds = YacserObjectRepository.getRelatedObjects(function.getId(),
				YacserObjectRepository.YACSER_HAS_REQUIREMENT);
		if (requirementIds != null) {
			List<Requirement> requirements = new ArrayList<>();
			for (String requirementId : requirementIds) {
				requirements
						.add((Requirement) YacserObjectRepository.build(YacserObjectType.Requirement, requirementId));
			}
			return requirements;
		}
		return null;
	}

	public SystemInterface getInput(Function function) throws IOException {
		String inputId = YacserObjectRepository.getRelatedObject(function.getId(),
				YacserObjectRepository.YACSER_HAS_INPUT);
		return inputId != null
				? (SystemInterface) YacserObjectRepository.build(YacserObjectType.SystemInterface, inputId)
				: null;
	}

	public SystemInterface getOutput(Function function) throws IOException {
		String outputId = YacserObjectRepository.getRelatedObject(function.getId(),
				YacserObjectRepository.YACSER_HAS_OUTPUT);
		return outputId != null
				? (SystemInterface) YacserObjectRepository.build(YacserObjectType.SystemInterface, outputId)
				: null;
	}

	public Function getAssembly(Function function) throws IOException {
		String assemblyId = super.getAssemblyId(function);
		return assemblyId != null ? (Function) YacserObjectRepository.build(YacserObjectType.Function, assemblyId)
				: null;
	}

	public List<Function> getParts(Function function) throws IOException {
		List<String> partIds = super.getPartIds(function);
		if (partIds != null) {
			List<Function> parts = new ArrayList<>();
			for (String partId : partIds) {
				parts.add((Function) YacserObjectRepository.build(YacserObjectType.Function, partId));
			}
			return parts;
		}
		return null;
	}

}
