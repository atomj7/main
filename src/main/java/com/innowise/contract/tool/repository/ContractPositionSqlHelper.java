package com.innowise.contract.tool.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class ContractPositionSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("contract_position_id", table, columnPrefix + "_contract_position_id"));
        columns.add(Column.aliased("employee_id", table, columnPrefix + "_employee_id"));
        columns.add(Column.aliased("subcontract_id", table, columnPrefix + "_subcontract_id"));
        columns.add(Column.aliased("number_contract_position", table, columnPrefix + "_number_contract_position"));
        columns.add(Column.aliased("restriction_type_id", table, columnPrefix + "_restriction_type_id"));
        columns.add(Column.aliased("restriction", table, columnPrefix + "_restriction"));
        columns.add(Column.aliased("currency_id", table, columnPrefix + "_currency_id"));
        columns.add(Column.aliased("rate_an_hour", table, columnPrefix + "_rate_an_hour"));
        columns.add(Column.aliased("status_id", table, columnPrefix + "_status_id"));

        columns.add(Column.aliased("subcontract_id_id", table, columnPrefix + "_subcontract_id_id"));
        return columns;
    }
}
