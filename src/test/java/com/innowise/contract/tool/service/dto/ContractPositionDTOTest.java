package com.innowise.contract.tool.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.innowise.contract.tool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContractPositionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractPositionDTO.class);
        ContractPositionDTO contractPositionDTO1 = new ContractPositionDTO();
        contractPositionDTO1.setId(1L);
        ContractPositionDTO contractPositionDTO2 = new ContractPositionDTO();
        assertThat(contractPositionDTO1).isNotEqualTo(contractPositionDTO2);
        contractPositionDTO2.setId(contractPositionDTO1.getId());
        assertThat(contractPositionDTO1).isEqualTo(contractPositionDTO2);
        contractPositionDTO2.setId(2L);
        assertThat(contractPositionDTO1).isNotEqualTo(contractPositionDTO2);
        contractPositionDTO1.setId(null);
        assertThat(contractPositionDTO1).isNotEqualTo(contractPositionDTO2);
    }
}
