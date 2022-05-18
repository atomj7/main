package com.innowise.contract.tool.web.rest;

import com.innowise.contract.tool.repository.SubcontractRepository;
import com.innowise.contract.tool.service.SubcontractService;
import com.innowise.contract.tool.service.dto.SubcontractDTO;
import com.innowise.contract.tool.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.innowise.contract.tool.domain.Subcontract}.
 */
@RestController
@RequestMapping("/api")
public class SubcontractResource {

    private final Logger log = LoggerFactory.getLogger(SubcontractResource.class);

    private static final String ENTITY_NAME = "subcontract";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubcontractService subcontractService;

    private final SubcontractRepository subcontractRepository;

    public SubcontractResource(SubcontractService subcontractService, SubcontractRepository subcontractRepository) {
        this.subcontractService = subcontractService;
        this.subcontractRepository = subcontractRepository;
    }

    /**
     * {@code POST  /subcontracts} : Create a new subcontract.
     *
     * @param subcontractDTO the subcontractDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subcontractDTO, or with status {@code 400 (Bad Request)} if the subcontract has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subcontracts")
    public Mono<ResponseEntity<SubcontractDTO>> createSubcontract(@Valid @RequestBody SubcontractDTO subcontractDTO)
        throws URISyntaxException {
        log.debug("REST request to save Subcontract : {}", subcontractDTO);
        if (subcontractDTO.getId() != null) {
            throw new BadRequestAlertException("A new subcontract cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return subcontractService
            .save(subcontractDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/subcontracts/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /subcontracts/:id} : Updates an existing subcontract.
     *
     * @param id the id of the subcontractDTO to save.
     * @param subcontractDTO the subcontractDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subcontractDTO,
     * or with status {@code 400 (Bad Request)} if the subcontractDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subcontractDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subcontracts/{id}")
    public Mono<ResponseEntity<SubcontractDTO>> updateSubcontract(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SubcontractDTO subcontractDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Subcontract : {}, {}", id, subcontractDTO);
        if (subcontractDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subcontractDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return subcontractRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return subcontractService
                    .update(subcontractDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /subcontracts/:id} : Partial updates given fields of an existing subcontract, field will ignore if it is null
     *
     * @param id the id of the subcontractDTO to save.
     * @param subcontractDTO the subcontractDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subcontractDTO,
     * or with status {@code 400 (Bad Request)} if the subcontractDTO is not valid,
     * or with status {@code 404 (Not Found)} if the subcontractDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the subcontractDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/subcontracts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<SubcontractDTO>> partialUpdateSubcontract(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SubcontractDTO subcontractDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Subcontract partially : {}, {}", id, subcontractDTO);
        if (subcontractDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subcontractDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return subcontractRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<SubcontractDTO> result = subcontractService.partialUpdate(subcontractDTO);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /subcontracts} : get all the subcontracts.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subcontracts in body.
     */
    @GetMapping("/subcontracts")
    public Mono<ResponseEntity<List<SubcontractDTO>>> getAllSubcontracts(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Subcontracts");
        return subcontractService
            .countAll()
            .zipWith(subcontractService.findAll(pageable).collectList())
            .map(countWithEntities ->
                ResponseEntity
                    .ok()
                    .headers(
                        PaginationUtil.generatePaginationHttpHeaders(
                            UriComponentsBuilder.fromHttpRequest(request),
                            new PageImpl<>(countWithEntities.getT2(), pageable, countWithEntities.getT1())
                        )
                    )
                    .body(countWithEntities.getT2())
            );
    }

    /**
     * {@code GET  /subcontracts/:id} : get the "id" subcontract.
     *
     * @param id the id of the subcontractDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subcontractDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subcontracts/{id}")
    public Mono<ResponseEntity<SubcontractDTO>> getSubcontract(@PathVariable Long id) {
        log.debug("REST request to get Subcontract : {}", id);
        Mono<SubcontractDTO> subcontractDTO = subcontractService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subcontractDTO);
    }

    /**
     * {@code DELETE  /subcontracts/:id} : delete the "id" subcontract.
     *
     * @param id the id of the subcontractDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subcontracts/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteSubcontract(@PathVariable Long id) {
        log.debug("REST request to delete Subcontract : {}", id);
        return subcontractService
            .delete(id)
            .map(result ->
                ResponseEntity
                    .noContent()
                    .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                    .build()
            );
    }
}
