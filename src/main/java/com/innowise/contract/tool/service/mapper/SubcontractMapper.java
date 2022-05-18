package com.innowise.contract.tool.service.mapper;

import com.innowise.contract.tool.domain.Contract;
import com.innowise.contract.tool.domain.Project;
import com.innowise.contract.tool.domain.Subcontract;
import com.innowise.contract.tool.service.dto.ContractDTO;
import com.innowise.contract.tool.service.dto.ProjectDTO;
import com.innowise.contract.tool.service.dto.SubcontractDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Subcontract} and its DTO {@link SubcontractDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubcontractMapper extends EntityMapper<SubcontractDTO, Subcontract> {
    @Mapping(target = "contract", source = "contract", qualifiedByName = "contractId")
    @Mapping(target = "project", source = "project", qualifiedByName = "projectId")
    SubcontractDTO toDto(Subcontract s);

    @Named("contractId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContractDTO toDtoContractId(Contract contract);

    @Named("projectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectDTO toDtoProjectId(Project project);
}
