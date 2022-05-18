package com.innowise.contract.tool.repository.rowmapper;

import com.innowise.contract.tool.domain.Client;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Client}, with proper type conversions.
 */
@Service
public class ClientRowMapper implements BiFunction<Row, String, Client> {

    private final ColumnConverter converter;

    public ClientRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Client} stored in the database.
     */
    @Override
    public Client apply(Row row, String prefix) {
        Client entity = new Client();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setSalesManagerId(converter.fromRow(row, prefix + "_sales_manager_id", Long.class));
        entity.setCountryId(converter.fromRow(row, prefix + "_country_id", String.class));
        entity.setPartnerStatus(converter.fromRow(row, prefix + "_partner_status", Boolean.class));
        entity.setContactPerson(converter.fromRow(row, prefix + "_contact_person", String.class));
        entity.setContractEmail(converter.fromRow(row, prefix + "_contract_email", String.class));
        entity.setIndustryId(converter.fromRow(row, prefix + "_industry_id", String.class));
        entity.setSigner(converter.fromRow(row, prefix + "_signer", String.class));
        entity.setSignerPosition(converter.fromRow(row, prefix + "_signer_position", String.class));
        entity.setLegalAdress(converter.fromRow(row, prefix + "_legal_adress", String.class));
        entity.setRegistrationNumber(converter.fromRow(row, prefix + "_registration_number", String.class));
        entity.setBaseOfActivityId(converter.fromRow(row, prefix + "_base_of_activity_id", String.class));
        entity.setVatNumber(converter.fromRow(row, prefix + "_vat_number", String.class));
        entity.setBankName(converter.fromRow(row, prefix + "_bank_name", String.class));
        entity.setBankAdress(converter.fromRow(row, prefix + "_bank_adress", String.class));
        entity.setSwiftCode(converter.fromRow(row, prefix + "_swift_code", String.class));
        entity.setIbanCode(converter.fromRow(row, prefix + "_iban_code", String.class));
        return entity;
    }
}
