package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;

@Component
public class SystemInterfaceResolver extends YacserObjectResolver implements GraphQLResolver<SystemInterface> {

	public SystemInterfaceResolver() {
	}

	public String getName(SystemInterface systemInterface) throws IOException {
		return super.getName(systemInterface);
	}

	public String getDescription(SystemInterface systemInterface) throws IOException {
		return super.getDescription(systemInterface);
	}

	public YacserObjectType getType(SystemInterface systemInterface) throws IOException {
		return super.getType(systemInterface);
	}

	public SystemSlot getSystemSlot0(SystemInterface systemInterface) throws IOException {
		String systemSlot0Id = YacserObjectRepository.getRelatedObject(systemInterface.getId(),
				YacserObjectRepository.YACSER_SYSTEM_SLOT_0);
		return systemSlot0Id != null
				? (SystemSlot) YacserObjectRepository.build(YacserObjectType.SystemSlot, systemSlot0Id)
				: null;
	}

	public SystemSlot getSystemSlot1(SystemInterface systemInterface) throws IOException {
		String systemSlot1Id = YacserObjectRepository.getRelatedObject(systemInterface.getId(),
				YacserObjectRepository.YACSER_SYSTEM_SLOT_1);
		return systemSlot1Id != null
				? (SystemSlot) YacserObjectRepository.build(YacserObjectType.SystemSlot, systemSlot1Id)
				: null;
	}
	
	public List<Requirement> getRequirements(SystemInterface systemInterface) throws IOException {
		List<String> requirementIds = YacserObjectRepository.getRelatedObjects(systemInterface.getId(),
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
	
	public List<Function> getFunctionInputs(SystemInterface systemInterface) throws IOException {
		List<Function> functions = null;
		List<String> subjectIds = YacserObjectRepository.getRelatedSubjects(systemInterface.getId(),
				YacserObjectRepository.YACSER_HAS_INPUT);
		if (subjectIds != null) {
			functions = new ArrayList<>();
			for (String subjectId : subjectIds) {
				functions.add((Function) YacserObjectRepository.build(YacserObjectType.Function, subjectId));
			}
		}
		return functions;
	}

	public List<Function> getFunctionOutputs(SystemInterface systemInterface) throws IOException {
		List<Function> functions = null;
		List<String> subjectIds = YacserObjectRepository.getRelatedSubjects(systemInterface.getId(),
				YacserObjectRepository.YACSER_HAS_OUTPUT);
		if (subjectIds != null) {
			functions = new ArrayList<>();
			for (String subjectId : subjectIds) {
				functions.add((Function) YacserObjectRepository.build(YacserObjectType.Function, subjectId));
			}
		}
		return functions;
	}

	public List<PortRealisation> getPortRealisations(SystemInterface systemInterface) throws IOException {
		List<PortRealisation> portRealisations = null;
		List<String> subjectIds = YacserObjectRepository.getRelatedSubjects(systemInterface.getId(),
				YacserObjectRepository.YACSER_INTERFACE);
		if (subjectIds != null) {
			portRealisations = new ArrayList<>();
			for (String subjectId : subjectIds) {
				portRealisations.add(
						(PortRealisation) YacserObjectRepository.build(YacserObjectType.PortRealisation, subjectId));
			}
		}
		return portRealisations;
	}

	public SystemInterface getAssembly(SystemInterface systemInterface) throws IOException {
		String assemblyId = super.getAssemblyId(systemInterface);
		return assemblyId != null
				? (SystemInterface) YacserObjectRepository.build(YacserObjectType.SystemInterface, assemblyId)
				: null;
	}

	public List<SystemInterface> getParts(SystemInterface systemInterface) throws IOException {
		List<String> partIds = super.getPartIds(systemInterface);
		if (partIds != null) {
			List<SystemInterface> parts = new ArrayList<>();
			for (String partId : partIds) {
				parts.add((SystemInterface) YacserObjectRepository.build(YacserObjectType.SystemInterface, partId));
			}
			return parts;
		}
		return null;
	}
}
