package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;

@Component
public class HamburgerResolver extends YacserObjectResolver implements GraphQLResolver<Hamburger> {

	public HamburgerResolver() {
	}

	public String getName(Hamburger hamburger) throws IOException {
		return super.getName(hamburger);
	}

	public String getDescription(Hamburger hamburger) throws IOException {
		return super.getDescription(hamburger);
	}

	public YacserObjectType getType(Hamburger hamburger) throws IOException {
		return super.getType(hamburger);
	}

	public SystemSlot getFunctionalUnit(Hamburger hamburger) throws IOException {
		String systemSlotId = YacserObjectRepository.getRelatedObject(hamburger.getId(),
				YacserObjectRepository.YACSER_FUNCTIONAL_UNIT);
		return systemSlotId != null
				? (SystemSlot) YacserObjectRepository.build(YacserObjectType.SystemSlot, systemSlotId)
				: null;
	}
	
	public RealisationModule getTechnicalSolution(Hamburger hamburger) throws IOException {
		String realisationModuleId = YacserObjectRepository.getRelatedObject(hamburger.getId(),
				YacserObjectRepository.YACSER_TECHNICAL_SOLUTION);
		return realisationModuleId != null
				? (RealisationModule) YacserObjectRepository.build(YacserObjectType.RealisationModule, realisationModuleId)
				: null;
	}
	
	public Hamburger getAssembly(Hamburger hamburger) throws IOException {
		String assemblyId = super.getAssemblyId(hamburger);
		return assemblyId != null ? (Hamburger) YacserObjectRepository.build(YacserObjectType.Hamburger, assemblyId)
				: null;
	}

	public List<Hamburger> getParts(Hamburger hamburger) throws IOException {
		List<String> partIds = super.getPartIds(hamburger);
		if (partIds != null) {
			List<Hamburger> parts = new ArrayList<>();
			for (String partId : partIds) {
				parts.add((Hamburger) YacserObjectRepository.build(YacserObjectType.Hamburger, partId));
			}
			return parts;
		}
		return null;
	}
}
