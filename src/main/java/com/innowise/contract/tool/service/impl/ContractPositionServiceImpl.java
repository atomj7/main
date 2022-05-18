package com.innowise.contract.tool.service.impl;

import com.innowise.contract.tool.domain.ContractPosition;
import com.innowise.contract.tool.repository.ContractPositionRepository;
import com.innowise.contract.tool.service.ContractPositionService;
import com.innowise.contract.tool.service.dto.ContractPositionDTO;
import com.innowise.contract.tool.service.mapper.ContractPositionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link ContractPosition}.
 */
@Service
@Transactional
public class ContractPositionServiceImpl implements ContractPositionService {

    private final Logger log = LoggerFactory.getLogger(ContractPositionServiceImpl.class);

    private final ContractPositionRepository contractPositionRepository;

    private final ContractPositionMapper contractPositionMapper;

    public ContractPositionServiceImpl(
        ContractPositionRepository contractPositionRepository,
        ContractPositionMapper contractPositionMapper
    ) {
        this.contractPositionRepository = contractPositionRepository;
        this.contractPositionMapper = contractPositionMapper;
    }

    @Override
    public Mono<ContractPositionDTO> save(ContractPositionDTO contractPositionDTO) {
        log.debug("Request to save ContractPosition : {}", contractPositionDTO);
        return contractPositionRepository.save(contractPositionMapper.toEntity(contractPositionDTO)).map(contractPositionMapper::toDto);
    }

    @Override
    public Mono<ContractPositionDTO> update(ContractPositionDTO contractPositionDTO) {
        log.debug("Request to save ContractPosition : {}", contractPositionDTO);
        return contractPositionRepository.save(contractPositionMapper.toEntity(contractPositionDTO)).map(contractPositionMapper::toDto);
    }

    @Override
    public Mono<ContractPositionDTO> partialUpdate(ContractPositionDTO contractPositionDTO) {
        log.debug("Request to partially update ContractPosition : {}", contractPositionDTO);

        return contractPositionRepository
            .findById(contractPositionDTO.getId())
            .map(existingContractPosition -> {
                contractPositionMapper.partialUpdate(existingContractPosition, contractPositionDTO);

                return existingContractPosition;
            })
            .flatMap(contractPositionRepository::save)
            .map(contractPositionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<ContractPositionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContractPositions");
        return contractPositionRepository.findAllBy(pageable).map(contractPositionMapper::toDto);
    }

    public Mono<Long> countAll() {
        return contractPositionRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<ContractPositionDTO> findOne(Long id) {
        log.debug("Request to get ContractPosition : {}", id);
        return contractPositionRepository.findById(id).map(contractPositionMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete ContractPosition : {}", id);
        return contractPositionRepository.deleteById(id);
    }
}
