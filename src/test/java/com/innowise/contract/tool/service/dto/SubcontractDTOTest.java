package com.innowise.contract.tool.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.innowise.contract.tool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubcontractDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubcontractDTO.class);
        SubcontractDTO subcontractDTO1 = new SubcontractDTO();
        subcontractDTO1.setId(1L);
        SubcontractDTO subcontractDTO2 = new SubcontractDTO();
        assertThat(subcontractDTO1).isNotEqualTo(subcontractDTO2);
        subcontractDTO2.setId(subcontractDTO1.getId());
        assertThat(subcontractDTO1).isEqualTo(subcontractDTO2);
        subcontractDTO2.setId(2L);
        assertThat(subcontractDTO1).isNotEqualTo(subcontractDTO2);
        subcontractDTO1.setId(null);
        assertThat(subcontractDTO1).isNotEqualTo(subcontractDTO2);
    }
}
