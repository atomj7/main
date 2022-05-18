package com.innowise.contract.tool.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubcontractMapperTest {

    private SubcontractMapper subcontractMapper;

    @BeforeEach
    public void setUp() {
        subcontractMapper = new SubcontractMapperImpl();
    }
}
