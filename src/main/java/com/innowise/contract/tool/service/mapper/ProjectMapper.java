package com.innowise.contract.tool.service.mapper;

import com.innowise.contract.tool.domain.Client;
import com.innowise.contract.tool.domain.Project;
import com.innowise.contract.tool.service.dto.ClientDTO;
import com.innowise.contract.tool.service.dto.ProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Project} and its DTO {@link ProjectDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    ProjectDTO toDto(Project s);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);
}
