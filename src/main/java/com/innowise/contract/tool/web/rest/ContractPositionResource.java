package com.innowise.contract.tool.web.rest;

import com.innowise.contract.tool.repository.ContractPositionRepository;
import com.innowise.contract.tool.service.ContractPositionService;
import com.innowise.contract.tool.service.dto.ContractPositionDTO;
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
 * REST controller for managing {@link com.innowise.contract.tool.domain.ContractPosition}.
 */
@RestController
@RequestMapping("/api")
public class ContractPositionResource {

    private final Logger log = LoggerFactory.getLogger(ContractPositionResource.class);

    private static final String ENTITY_NAME = "contractPosition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContractPositionService contractPositionService;

    private final ContractPositionRepository contractPositionRepository;

    public ContractPositionResource(
        ContractPositionService contractPositionService,
        ContractPositionRepository contractPositionRepository
    ) {
        this.contractPositionService = contractPositionService;
        this.contractPositionRepository = contractPositionRepository;
    }

    /**
     * {@code POST  /contract-positions} : Create a new contractPosition.
     *
     * @param contractPositionDTO the contractPositionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contractPositionDTO, or with status {@code 400 (Bad Request)} if the contractPosition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contract-positions")
    public Mono<ResponseEntity<ContractPositionDTO>> createContractPosition(@Valid @RequestBody ContractPositionDTO contractPositionDTO)
        throws URISyntaxException {
        log.debug("REST request to save ContractPosition : {}", contractPositionDTO);
        if (contractPositionDTO.getId() != null) {
            throw new BadRequestAlertException("A new contractPosition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return contractPositionService
            .save(contractPositionDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/contract-positions/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /contract-positions/:id} : Updates an existing contractPosition.
     *
     * @param id the id of the contractPositionDTO to save.
     * @param contractPositionDTO the contractPositionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contractPositionDTO,
     * or with status {@code 400 (Bad Request)} if the contractPositionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contractPositionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contract-positions/{id}")
    public Mono<ResponseEntity<ContractPositionDTO>> updateContractPosition(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContractPositionDTO contractPositionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ContractPosition : {}, {}", id, contractPositionDTO);
        if (contractPositionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contractPositionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return contractPositionRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return contractPositionService
                    .update(contractPositionDTO)
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
     * {@code PATCH  /contract-positions/:id} : Partial updates given fields of an existing contractPosition, field will ignore if it is null
     *
     * @param id the id of the contractPositionDTO to save.
     * @param contractPositionDTO the contractPositionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contractPositionDTO,
     * or with status {@code 400 (Bad Request)} if the contractPositionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contractPositionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contractPositionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contract-positions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<ContractPositionDTO>> partialUpdateContractPosition(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContractPositionDTO contractPositionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContractPosition partially : {}, {}", id, contractPositionDTO);
        if (contractPositionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contractPositionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return contractPositionRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<ContractPositionDTO> result = contractPositionService.partialUpdate(contractPositionDTO);

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
     * {@code GET  /contract-positions} : get all the contractPositions.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contractPositions in body.
     */
    @GetMapping("/contract-positions")
    public Mono<ResponseEntity<List<ContractPositionDTO>>> getAllContractPositions(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of ContractPositions");
        return contractPositionService
            .countAll()
            .zipWith(contractPositionService.findAll(pageable).collectList())
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
     * {@code GET  /contract-positions/:id} : get the "id" contractPosition.
     *
     * @param id the id of the contractPositionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contractPositionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contract-positions/{id}")
    public Mono<ResponseEntity<ContractPositionDTO>> getContractPosition(@PathVariable Long id) {
        log.debug("REST request to get ContractPosition : {}", id);
        Mono<ContractPositionDTO> contractPositionDTO = contractPositionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contractPositionDTO);
    }

    /**
     * {@code DELETE  /contract-positions/:id} : delete the "id" contractPosition.
     *
     * @param id the id of the contractPositionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contract-positions/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteContractPosition(@PathVariable Long id) {
        log.debug("REST request to delete ContractPosition : {}", id);
        return contractPositionService
            .delete(id)
            .map(result ->
                ResponseEntity
                    .noContent()
                    .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                    .build()
            );
    }
}
