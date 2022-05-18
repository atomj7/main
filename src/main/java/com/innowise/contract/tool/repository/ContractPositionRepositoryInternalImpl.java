package com.innowise.contract.tool.repository;

import static org.springframework.data.relational.core.query.Criteria.where;

import com.innowise.contract.tool.domain.ContractPosition;
import com.innowise.contract.tool.repository.rowmapper.ContractPositionRowMapper;
import com.innowise.contract.tool.repository.rowmapper.SubcontractRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
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
 * Spring Data SQL reactive custom repository implementation for the ContractPosition entity.
 */
@SuppressWarnings("unused")
class ContractPositionRepositoryInternalImpl
    extends SimpleR2dbcRepository<ContractPosition, Long>
    implements ContractPositionRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final SubcontractRowMapper subcontractMapper;
    private final ContractPositionRowMapper contractpositionMapper;

    private static final Table entityTable = Table.aliased("contract_position", EntityManager.ENTITY_ALIAS);
    private static final Table subcontractTable = Table.aliased("subcontract", "subcontract");

    public ContractPositionRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        SubcontractRowMapper subcontractMapper,
        ContractPositionRowMapper contractpositionMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(ContractPosition.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.subcontractMapper = subcontractMapper;
        this.contractpositionMapper = contractpositionMapper;
    }

    @Override
    public Flux<ContractPosition> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<ContractPosition> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = ContractPositionSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(SubcontractSqlHelper.getColumns(subcontractTable, "subcontract"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(subcontractTable)
            .on(Column.create("subcontract_id", entityTable))
            .equals(Column.create("id", subcontractTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, ContractPosition.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<ContractPosition> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<ContractPosition> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private ContractPosition process(Row row, RowMetadata metadata) {
        ContractPosition entity = contractpositionMapper.apply(row, "e");
        entity.setSubcontract(subcontractMapper.apply(row, "subcontract"));
        return entity;
    }

    @Override
    public <S extends ContractPosition> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
