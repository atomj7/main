package com.innowise.contract.tool.repository.rowmapper;

import com.innowise.contract.tool.domain.Project;
import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Project}, with proper type conversions.
 */
@Service
public class ProjectRowMapper implements BiFunction<Row, String, Project> {

    private final ColumnConverter converter;

    public ProjectRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Project} stored in the database.
     */
    @Override
    public Project apply(Row row, String prefix) {
        Project entity = new Project();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setClientId(converter.fromRow(row, prefix + "_client_id", Long.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setStartDate(converter.fromRow(row, prefix + "_start_date", LocalDate.class));
        entity.setFinishDate(converter.fromRow(row, prefix + "_finish_date", LocalDate.class));
        entity.setLink(converter.fromRow(row, prefix + "_link", String.class));
        entity.setStatusId(converter.fromRow(row, prefix + "_status_id", String.class));
        entity.setClientIdId(converter.fromRow(row, prefix + "_client_id_id", Long.class));
        return entity;
    }
}
