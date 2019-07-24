package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;

@Component
public class RealisationPortResolver extends YacserObjectResolver implements GraphQLResolver<RealisationPort> {

	public RealisationPortResolver() {
	}

	public String getName(RealisationPort realisationPort) throws IOException {
		return super.getName(realisationPort);
	}

	public String getDescription(RealisationPort realisationPort) throws IOException {
		return super.getDescription(realisationPort);
	}

	public YacserObjectType getType(RealisationPort realisationPort) throws IOException {
		return super.getType(realisationPort);
	}

	public RealisationModule getOwner(RealisationPort realisationPort) throws IOException {
		String ownerId = YacserObjectRepository.getRelatedSubject(realisationPort.getId(),
				YacserObjectRepository.YACSER_HAS_PORT);
		return ownerId != null
				? ((RealisationModule) YacserObjectRepository.build(YacserObjectType.RealisationModule, ownerId))
				: null;
	}

	public RealisationPort getAssembly(RealisationPort realisationPort) throws IOException {
		String assemblyId = super.getAssemblyId(realisationPort);
		return assemblyId != null
				? (RealisationPort) YacserObjectRepository.build(YacserObjectType.RealisationPort, assemblyId)
				: null;
	}

	public List<RealisationPort> getParts(RealisationPort realisationPort) throws IOException {
		List<String> partIds = super.getPartIds(realisationPort);
		if (partIds != null) {
			List<RealisationPort> parts = new ArrayList<>();
			for (String partId : partIds) {
				parts.add((RealisationPort) YacserObjectRepository.build(YacserObjectType.RealisationPort, partId));
			}
			return parts;
		}
		return null;
	}
}
