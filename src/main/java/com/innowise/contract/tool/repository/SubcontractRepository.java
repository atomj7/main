package com.innowise.contract.tool.repository;

import com.innowise.contract.tool.domain.Subcontract;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Subcontract entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubcontractRepository extends ReactiveCrudRepository<Subcontract, Long>, SubcontractRepositoryInternal {
    Flux<Subcontract> findAllBy(Pageable pageable);

    @Query("SELECT * FROM subcontract entity WHERE entity.contract_id = :id")
    Flux<Subcontract> findByContract(Long id);

    @Query("SELECT * FROM subcontract entity WHERE entity.contract_id IS NULL")
    Flux<Subcontract> findAllWhereContractIsNull();

    @Query("SELECT * FROM subcontract entity WHERE entity.project_id = :id")
    Flux<Subcontract> findByProject(Long id);

    @Query("SELECT * FROM subcontract entity WHERE entity.project_id IS NULL")
    Flux<Subcontract> findAllWhereProjectIsNull();

    @Override
    <S extends Subcontract> Mono<S> save(S entity);

    @Override
    Flux<Subcontract> findAll();

    @Override
    Mono<Subcontract> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface SubcontractRepositoryInternal {
    <S extends Subcontract> Mono<S> save(S entity);

    Flux<Subcontract> findAllBy(Pageable pageable);

    Flux<Subcontract> findAll();

    Mono<Subcontract> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Subcontract> findAllBy(Pageable pageable, Criteria criteria);

}
