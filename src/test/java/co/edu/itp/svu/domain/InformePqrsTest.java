package co.edu.itp.svu.domain;

import static co.edu.itp.svu.domain.InformePqrsTestSamples.*;
import static co.edu.itp.svu.domain.OficinaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import co.edu.itp.svu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InformePqrsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InformePqrs.class);
        InformePqrs informePqrs1 = getInformePqrsSample1();
        InformePqrs informePqrs2 = new InformePqrs();
        assertThat(informePqrs1).isNotEqualTo(informePqrs2);

        informePqrs2.setId(informePqrs1.getId());
        assertThat(informePqrs1).isEqualTo(informePqrs2);

        informePqrs2 = getInformePqrsSample2();
        assertThat(informePqrs1).isNotEqualTo(informePqrs2);
    }

    @Test
    void oficinaTest() {
        InformePqrs informePqrs = getInformePqrsRandomSampleGenerator();
        Oficina oficinaBack = getOficinaRandomSampleGenerator();

        informePqrs.setOficina(oficinaBack);
        assertThat(informePqrs.getOficina()).isEqualTo(oficinaBack);

        informePqrs.oficina(null);
        assertThat(informePqrs.getOficina()).isNull();
    }
}
