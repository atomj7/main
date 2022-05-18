package com.innowise.contract.tool.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.innowise.contract.tool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubcontractTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subcontract.class);
        Subcontract subcontract1 = new Subcontract();
        subcontract1.setId(1L);
        Subcontract subcontract2 = new Subcontract();
        subcontract2.setId(subcontract1.getId());
        assertThat(subcontract1).isEqualTo(subcontract2);
        subcontract2.setId(2L);
        assertThat(subcontract1).isNotEqualTo(subcontract2);
        subcontract1.setId(null);
        assertThat(subcontract1).isNotEqualTo(subcontract2);
    }
}
