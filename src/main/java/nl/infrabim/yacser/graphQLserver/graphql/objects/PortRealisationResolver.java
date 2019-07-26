package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;

@Component
public class PortRealisationResolver extends YacserObjectResolver implements GraphQLResolver<PortRealisation> {

	public PortRealisationResolver() {
	}

	public String getName(PortRealisation portRealisation) throws IOException {
		return super.getName(portRealisation);
	}

	public String getDescription(PortRealisation portRealisation) throws IOException {
		return super.getDescription(portRealisation);
	}

	public YacserObjectType getType(PortRealisation portRealisation) throws IOException {
		return super.getType(portRealisation);
	}

	public Hamburger getOwner(PortRealisation portRealisation) throws IOException {
		String ownerId = YacserObjectRepository.getRelatedSubject(portRealisation.getId(),
				YacserObjectRepository.YACSER_HAS_PORT_REALISATION);
		return ownerId != null ? ((Hamburger) YacserObjectRepository.build(YacserObjectType.Hamburger, ownerId)) : null;
	}
	
	public SystemInterface getInterface(PortRealisation portRealisation) throws IOException {
		String systemInterfaceId = YacserObjectRepository.getRelatedObject(portRealisation.getId(),
				YacserObjectRepository.YACSER_INTERFACE);
		return systemInterfaceId != null
				? (SystemInterface) YacserObjectRepository.build(YacserObjectType.SystemInterface, systemInterfaceId)
				: null;
	}
	
	public RealisationPort getPort(PortRealisation portRealisation) throws IOException {
		String realisationPortId = YacserObjectRepository.getRelatedObject(portRealisation.getId(),
				YacserObjectRepository.YACSER_PORT);
		return realisationPortId != null
				? (RealisationPort) YacserObjectRepository.build(YacserObjectType.RealisationPort, realisationPortId)
				: null;
	}
	
	public PortRealisation getAssembly(PortRealisation portRealisation) throws IOException {
		String assemblyId = super.getAssemblyId(portRealisation);
		return assemblyId != null
				? (PortRealisation) YacserObjectRepository.build(YacserObjectType.PortRealisation, assemblyId)
				: null;
	}

	public List<PortRealisation> getParts(PortRealisation portRealisation) throws IOException {
		List<String> partIds = super.getPartIds(portRealisation);
		if (partIds != null) {
			List<PortRealisation> parts = new ArrayList<>();
			for (String partId : partIds) {
				parts.add((PortRealisation) YacserObjectRepository.build(YacserObjectType.PortRealisation, partId));
			}
			return parts;
		}
		return null;
	}
}
