package com.innowise.contract.tool.repository;

import static org.springframework.data.relational.core.query.Criteria.where;

import com.innowise.contract.tool.domain.Subcontract;
import com.innowise.contract.tool.repository.rowmapper.ContractRowMapper;
import com.innowise.contract.tool.repository.rowmapper.ProjectRowMapper;
import com.innowise.contract.tool.repository.rowmapper.SubcontractRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiFunction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Comparison;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoinCondition;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive custom repository implementation for the Subcontract entity.
 */
@SuppressWarnings("unused")
class SubcontractRepositoryInternalImpl extends SimpleR2dbcRepository<Subcontract, Long> implements SubcontractRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final ContractRowMapper contractMapper;
    private final ProjectRowMapper projectMapper;
    private final SubcontractRowMapper subcontractMapper;

    private static final Table entityTable = Table.aliased("subcontract", EntityManager.ENTITY_ALIAS);
    private static final Table contractIdTable = Table.aliased("contract", "contractId");
    private static final Table projectIdTable = Table.aliased("project", "projectId");

    public SubcontractRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        ContractRowMapper contractMapper,
        ProjectRowMapper projectMapper,
        SubcontractRowMapper subcontractMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Subcontract.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.contractMapper = contractMapper;
        this.projectMapper = projectMapper;
        this.subcontractMapper = subcontractMapper;
    }

    @Override
    public Flux<Subcontract> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Subcontract> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = SubcontractSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(ContractSqlHelper.getColumns(contractIdTable, "contractId"));
        columns.addAll(ProjectSqlHelper.getColumns(projectIdTable, "projectId"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(contractIdTable)
            .on(Column.create("contract_id_id", entityTable))
            .equals(Column.create("id", contractIdTable))
            .leftOuterJoin(projectIdTable)
            .on(Column.create("project_id_id", entityTable))
            .equals(Column.create("id", projectIdTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Subcontract.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Subcontract> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Subcontract> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Subcontract process(Row row, RowMetadata metadata) {
        Subcontract entity = subcontractMapper.apply(row, "e");
        entity.setContractId(contractMapper.apply(row, "contractId"));
        entity.setProjectId(projectMapper.apply(row, "projectId"));
        return entity;
    }

    @Override
    public <S extends Subcontract> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
