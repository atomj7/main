package com.innowise.contract.tool.service.impl;

import com.innowise.contract.tool.domain.Contract;
import com.innowise.contract.tool.repository.ContractRepository;
import com.innowise.contract.tool.service.ContractService;
import com.innowise.contract.tool.service.dto.ContractDTO;
import com.innowise.contract.tool.service.mapper.ContractMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Contract}.
 */
@Service
@Transactional
public class ContractServiceImpl implements ContractService {

    private final Logger log = LoggerFactory.getLogger(ContractServiceImpl.class);

    private final ContractRepository contractRepository;

    private final ContractMapper contractMapper;

    public ContractServiceImpl(ContractRepository contractRepository, ContractMapper contractMapper) {
        this.contractRepository = contractRepository;
        this.contractMapper = contractMapper;
    }

    @Override
    public Mono<ContractDTO> save(ContractDTO contractDTO) {
        log.debug("Request to save Contract : {}", contractDTO);
        return contractRepository.save(contractMapper.toEntity(contractDTO)).map(contractMapper::toDto);
    }

    @Override
    public Mono<ContractDTO> update(ContractDTO contractDTO) {
        log.debug("Request to save Contract : {}", contractDTO);
        return contractRepository.save(contractMapper.toEntity(contractDTO)).map(contractMapper::toDto);
    }

    @Override
    public Mono<ContractDTO> partialUpdate(ContractDTO contractDTO) {
        log.debug("Request to partially update Contract : {}", contractDTO);

        return contractRepository
            .findById(contractDTO.getId())
            .map(existingContract -> {
                contractMapper.partialUpdate(existingContract, contractDTO);

                return existingContract;
            })
            .flatMap(contractRepository::save)
            .map(contractMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<ContractDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contracts");
        return contractRepository.findAllBy(pageable).map(contractMapper::toDto);
    }

    public Mono<Long> countAll() {
        return contractRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<ContractDTO> findOne(Long id) {
        log.debug("Request to get Contract : {}", id);
        return contractRepository.findById(id).map(contractMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Contract : {}", id);
        return contractRepository.deleteById(id);
    }
}
