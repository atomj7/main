package com.innowise.contract.tool.service;

import com.innowise.contract.tool.service.dto.SubcontractDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.innowise.contract.tool.domain.Subcontract}.
 */
public interface SubcontractService {
    /**
     * Save a subcontract.
     *
     * @param subcontractDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<SubcontractDTO> save(SubcontractDTO subcontractDTO);

    /**
     * Updates a subcontract.
     *
     * @param subcontractDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<SubcontractDTO> update(SubcontractDTO subcontractDTO);

    /**
     * Partially updates a subcontract.
     *
     * @param subcontractDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<SubcontractDTO> partialUpdate(SubcontractDTO subcontractDTO);

    /**
     * Get all the subcontracts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<SubcontractDTO> findAll(Pageable pageable);

    /**
     * Returns the number of subcontracts available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" subcontract.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<SubcontractDTO> findOne(Long id);

    /**
     * Delete the "id" subcontract.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
