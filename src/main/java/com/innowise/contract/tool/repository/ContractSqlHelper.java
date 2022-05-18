package com.innowise.contract.tool.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class ContractSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("cipher", table, columnPrefix + "_cipher"));
        columns.add(Column.aliased("client_id", table, columnPrefix + "_client_id"));
        columns.add(Column.aliased("provider_id", table, columnPrefix + "_provider_id"));
        columns.add(Column.aliased("type_id", table, columnPrefix + "_type_id"));
        columns.add(Column.aliased("sum", table, columnPrefix + "_sum"));
        columns.add(Column.aliased("position_count", table, columnPrefix + "_position_count"));
        columns.add(Column.aliased("currency_id", table, columnPrefix + "_currency_id"));
        columns.add(Column.aliased("payment_term", table, columnPrefix + "_payment_term"));
        columns.add(Column.aliased("payment_term_type_id", table, columnPrefix + "_payment_term_type_id"));
        columns.add(Column.aliased("start_date", table, columnPrefix + "_start_date"));
        columns.add(Column.aliased("finish_date", table, columnPrefix + "_finish_date"));
        columns.add(Column.aliased("status_id", table, columnPrefix + "_status_id"));
        columns.add(Column.aliased("link", table, columnPrefix + "_link"));

        columns.add(Column.aliased("client_id_id", table, columnPrefix + "_client_id_id"));
        return columns;
    }
}
