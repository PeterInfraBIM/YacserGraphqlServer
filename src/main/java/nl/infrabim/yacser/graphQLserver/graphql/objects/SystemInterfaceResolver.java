package nl.infrabim.yacser.graphQLserver.graphql.objects;

import java.io.IOException;

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

}
