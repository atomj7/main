package com.innowise.contract.tool.service.mapper;

import com.innowise.contract.tool.domain.ContractPosition;
import com.innowise.contract.tool.domain.Subcontract;
import com.innowise.contract.tool.service.dto.ContractPositionDTO;
import com.innowise.contract.tool.service.dto.SubcontractDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContractPosition} and its DTO {@link ContractPositionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContractPositionMapper extends EntityMapper<ContractPositionDTO, ContractPosition> {
    @Mapping(target = "subcontract", source = "subcontract", qualifiedByName = "subcontractId")
    ContractPositionDTO toDto(ContractPosition s);

    @Named("subcontractId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubcontractDTO toDtoSubcontractId(Subcontract subcontract);
}
