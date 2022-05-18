package com.innowise.contract.tool.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContractPositionMapperTest {

    private ContractPositionMapper contractPositionMapper;

    @BeforeEach
    public void setUp() {
        contractPositionMapper = new ContractPositionMapperImpl();
    }
}
