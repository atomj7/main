package com.innowise.contract.tool.repository;

import com.innowise.contract.tool.domain.ContractPosition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the ContractPosition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractPositionRepository extends ReactiveCrudRepository<ContractPosition, Long>, ContractPositionRepositoryInternal {
    Flux<ContractPosition> findAllBy(Pageable pageable);

    @Query("SELECT * FROM contract_position entity WHERE entity.subcontract_id_id = :id")
    Flux<ContractPosition> findBySubcontractId(Long id);

    @Query("SELECT * FROM contract_position entity WHERE entity.subcontract_id_id IS NULL")
    Flux<ContractPosition> findAllWhereSubcontractIdIsNull();

    @Override
    <S extends ContractPosition> Mono<S> save(S entity);

    @Override
    Flux<ContractPosition> findAll();

    @Override
    Mono<ContractPosition> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface ContractPositionRepositoryInternal {
    <S extends ContractPosition> Mono<S> save(S entity);

    Flux<ContractPosition> findAllBy(Pageable pageable);

    Flux<ContractPosition> findAll();

    Mono<ContractPosition> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<ContractPosition> findAllBy(Pageable pageable, Criteria criteria);

}
