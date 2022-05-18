package com.innowise.contract.tool.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class ClientSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("name", table, columnPrefix + "_name"));
        columns.add(Column.aliased("sales_manager_id", table, columnPrefix + "_sales_manager_id"));
        columns.add(Column.aliased("country_id", table, columnPrefix + "_country_id"));
        columns.add(Column.aliased("partner_status", table, columnPrefix + "_partner_status"));
        columns.add(Column.aliased("contact_person", table, columnPrefix + "_contact_person"));
        columns.add(Column.aliased("contract_email", table, columnPrefix + "_contract_email"));
        columns.add(Column.aliased("industry_id", table, columnPrefix + "_industry_id"));
        columns.add(Column.aliased("signer", table, columnPrefix + "_signer"));
        columns.add(Column.aliased("signer_position", table, columnPrefix + "_signer_position"));
        columns.add(Column.aliased("legal_adress", table, columnPrefix + "_legal_adress"));
        columns.add(Column.aliased("registration_number", table, columnPrefix + "_registration_number"));
        columns.add(Column.aliased("base_of_activity_id", table, columnPrefix + "_base_of_activity_id"));
        columns.add(Column.aliased("vat_number", table, columnPrefix + "_vat_number"));
        columns.add(Column.aliased("bank_name", table, columnPrefix + "_bank_name"));
        columns.add(Column.aliased("bank_adress", table, columnPrefix + "_bank_adress"));
        columns.add(Column.aliased("swift_code", table, columnPrefix + "_swift_code"));
        columns.add(Column.aliased("iban_code", table, columnPrefix + "_iban_code"));

        return columns;
    }
}
