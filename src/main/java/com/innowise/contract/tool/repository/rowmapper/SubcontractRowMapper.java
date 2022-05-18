package com.innowise.contract.tool.repository.rowmapper;

import com.innowise.contract.tool.domain.Subcontract;
import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Subcontract}, with proper type conversions.
 */
@Service
public class SubcontractRowMapper implements BiFunction<Row, String, Subcontract> {

    private final ColumnConverter converter;

    public SubcontractRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Subcontract} stored in the database.
     */
    @Override
    public Subcontract apply(Row row, String prefix) {
        Subcontract entity = new Subcontract();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setContractId(converter.fromRow(row, prefix + "_contract_id", Long.class));
        entity.setProjectId(converter.fromRow(row, prefix + "_project_id", Long.class));
        entity.setSubcontractCipher(converter.fromRow(row, prefix + "_subcontract_cipher", String.class));
        entity.setStatusId(converter.fromRow(row, prefix + "_status_id", String.class));
        entity.setCooperationTypeId(converter.fromRow(row, prefix + "_cooperation_type_id", String.class));
        entity.setSum(converter.fromRow(row, prefix + "_sum", Float.class));
        entity.setPositions(converter.fromRow(row, prefix + "_positions", Integer.class));
        entity.setCurrencyId(converter.fromRow(row, prefix + "_currency_id", String.class));
        entity.setPaymentTermTypeId(converter.fromRow(row, prefix + "_payment_term_type_id", String.class));
        entity.setPaymentTermId(converter.fromRow(row, prefix + "_payment_term_id", String.class));
        entity.setStartDate(converter.fromRow(row, prefix + "_start_date", LocalDate.class));
        entity.setFinishDate(converter.fromRow(row, prefix + "_finish_date", LocalDate.class));
        entity.setTypeId(converter.fromRow(row, prefix + "_type_id", String.class));
        entity.setTasktTypeId(converter.fromRow(row, prefix + "_taskt_type_id", String.class));
        entity.setTechnologyId(converter.fromRow(row, prefix + "_technology_id", String.class));
        entity.setDomenId(converter.fromRow(row, prefix + "_domen_id", String.class));
        entity.setLink(converter.fromRow(row, prefix + "_link", String.class));
        entity.setContractIdId(converter.fromRow(row, prefix + "_contract_id_id", Long.class));
        entity.setProjectIdId(converter.fromRow(row, prefix + "_project_id_id", Long.class));
        return entity;
    }
}
