package com.innowise.contract.tool.repository.rowmapper;

import com.innowise.contract.tool.domain.ContractPosition;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link ContractPosition}, with proper type conversions.
 */
@Service
public class ContractPositionRowMapper implements BiFunction<Row, String, ContractPosition> {

    private final ColumnConverter converter;

    public ContractPositionRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link ContractPosition} stored in the database.
     */
    @Override
    public ContractPosition apply(Row row, String prefix) {
        ContractPosition entity = new ContractPosition();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setContractPositionId(converter.fromRow(row, prefix + "_contract_position_id", String.class));
        entity.setEmployeeId(converter.fromRow(row, prefix + "_employee_id", Long.class));
        entity.setSubcontractId(converter.fromRow(row, prefix + "_subcontract_id", Long.class));
        entity.setNumberContractPosition(converter.fromRow(row, prefix + "_number_contract_position", Integer.class));
        entity.setRestrictionTypeId(converter.fromRow(row, prefix + "_restriction_type_id", String.class));
        entity.setRestriction(converter.fromRow(row, prefix + "_restriction", Integer.class));
        entity.setCurrencyId(converter.fromRow(row, prefix + "_currency_id", String.class));
        entity.setRateAnHour(converter.fromRow(row, prefix + "_rate_an_hour", Float.class));
        entity.setStatusId(converter.fromRow(row, prefix + "_status_id", String.class));
        entity.setSubcontractIdId(converter.fromRow(row, prefix + "_subcontract_id_id", Long.class));
        return entity;
    }
}
