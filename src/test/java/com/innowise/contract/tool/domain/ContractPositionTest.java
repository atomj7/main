package com.innowise.contract.tool.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.innowise.contract.tool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContractPositionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractPosition.class);
        ContractPosition contractPosition1 = new ContractPosition();
        contractPosition1.setId(1L);
        ContractPosition contractPosition2 = new ContractPosition();
        contractPosition2.setId(contractPosition1.getId());
        assertThat(contractPosition1).isEqualTo(contractPosition2);
        contractPosition2.setId(2L);
        assertThat(contractPosition1).isNotEqualTo(contractPosition2);
        contractPosition1.setId(null);
        assertThat(contractPosition1).isNotEqualTo(contractPosition2);
    }
}
