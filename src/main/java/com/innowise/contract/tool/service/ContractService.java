package com.innowise.contract.tool.service;

import com.innowise.contract.tool.service.dto.ContractDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.innowise.contract.tool.domain.Contract}.
 */
public interface ContractService {
    /**
     * Save a contract.
     *
     * @param contractDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<ContractDTO> save(ContractDTO contractDTO);

    /**
     * Updates a contract.
     *
     * @param contractDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<ContractDTO> update(ContractDTO contractDTO);

    /**
     * Partially updates a contract.
     *
     * @param contractDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<ContractDTO> partialUpdate(ContractDTO contractDTO);

    /**
     * Get all the contracts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<ContractDTO> findAll(Pageable pageable);

    /**
     * Returns the number of contracts available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" contract.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<ContractDTO> findOne(Long id);

    /**
     * Delete the "id" contract.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
