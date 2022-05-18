package com.innowise.contract.tool.repository.rowmapper;

import com.innowise.contract.tool.domain.Contract;
import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Contract}, with proper type conversions.
 */
@Service
public class ContractRowMapper implements BiFunction<Row, String, Contract> {

    private final ColumnConverter converter;

    public ContractRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Contract} stored in the database.
     */
    @Override
    public Contract apply(Row row, String prefix) {
        Contract entity = new Contract();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setCipher(converter.fromRow(row, prefix + "_cipher", String.class));
        entity.setClientId(converter.fromRow(row, prefix + "_client_id", Long.class));
        entity.setProviderId(converter.fromRow(row, prefix + "_provider_id", String.class));
        entity.setTypeId(converter.fromRow(row, prefix + "_type_id", String.class));
        entity.setSum(converter.fromRow(row, prefix + "_sum", Float.class));
        entity.setPositionCount(converter.fromRow(row, prefix + "_position_count", Integer.class));
        entity.setCurrencyId(converter.fromRow(row, prefix + "_currency_id", String.class));
        entity.setPaymentTerm(converter.fromRow(row, prefix + "_payment_term", Integer.class));
        entity.setPaymentTermTypeId(converter.fromRow(row, prefix + "_payment_term_type_id", String.class));
        entity.setStartDate(converter.fromRow(row, prefix + "_start_date", LocalDate.class));
        entity.setFinishDate(converter.fromRow(row, prefix + "_finish_date", LocalDate.class));
        entity.setStatusId(converter.fromRow(row, prefix + "_status_id", String.class));
        entity.setLink(converter.fromRow(row, prefix + "_link", String.class));
        entity.setClientId(converter.fromRow(row, prefix + "_client_id", Long.class));
        return entity;
    }
}
