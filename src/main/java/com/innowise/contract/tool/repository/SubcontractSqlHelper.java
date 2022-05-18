package com.innowise.contract.tool.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class SubcontractSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("contract_id", table, columnPrefix + "_contract_id"));
        columns.add(Column.aliased("project_id", table, columnPrefix + "_project_id"));
        columns.add(Column.aliased("subcontract_cipher", table, columnPrefix + "_subcontract_cipher"));
        columns.add(Column.aliased("status_id", table, columnPrefix + "_status_id"));
        columns.add(Column.aliased("cooperation_type_id", table, columnPrefix + "_cooperation_type_id"));
        columns.add(Column.aliased("sum", table, columnPrefix + "_sum"));
        columns.add(Column.aliased("positions", table, columnPrefix + "_positions"));
        columns.add(Column.aliased("currency_id", table, columnPrefix + "_currency_id"));
        columns.add(Column.aliased("payment_term_type_id", table, columnPrefix + "_payment_term_type_id"));
        columns.add(Column.aliased("payment_term_id", table, columnPrefix + "_payment_term_id"));
        columns.add(Column.aliased("start_date", table, columnPrefix + "_start_date"));
        columns.add(Column.aliased("finish_date", table, columnPrefix + "_finish_date"));
        columns.add(Column.aliased("type_id", table, columnPrefix + "_type_id"));
        columns.add(Column.aliased("taskt_type_id", table, columnPrefix + "_taskt_type_id"));
        columns.add(Column.aliased("technology_id", table, columnPrefix + "_technology_id"));
        columns.add(Column.aliased("domen_id", table, columnPrefix + "_domen_id"));
        columns.add(Column.aliased("link", table, columnPrefix + "_link"));

        columns.add(Column.aliased("contract_id_id", table, columnPrefix + "_contract_id_id"));
        columns.add(Column.aliased("project_id_id", table, columnPrefix + "_project_id_id"));
        return columns;
    }
}
