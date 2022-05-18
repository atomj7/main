package com.innowise.contract.tool.repository;

import com.innowise.contract.tool.domain.Contract;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Contract entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractRepository extends ReactiveCrudRepository<Contract, Long>, ContractRepositoryInternal {
    Flux<Contract> findAllBy(Pageable pageable);

    @Query("SELECT * FROM contract entity WHERE entity.client_id = :id")
    Flux<Contract> findByClient(Long id);

    @Query("SELECT * FROM contract entity WHERE entity.client_id IS NULL")
    Flux<Contract> findAllWhereClientIsNull();

    @Override
    <S extends Contract> Mono<S> save(S entity);

    @Override
    Flux<Contract> findAll();

    @Override
    Mono<Contract> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface ContractRepositoryInternal {
    <S extends Contract> Mono<S> save(S entity);

    Flux<Contract> findAllBy(Pageable pageable);

    Flux<Contract> findAll();

    Mono<Contract> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Contract> findAllBy(Pageable pageable, Criteria criteria);

}
