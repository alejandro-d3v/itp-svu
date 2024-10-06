package co.edu.itp.svu.domain;

import static co.edu.itp.svu.domain.ArchivoAdjuntoTestSamples.*;
import static co.edu.itp.svu.domain.OficinaTestSamples.*;
import static co.edu.itp.svu.domain.PqrsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import co.edu.itp.svu.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PqrsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pqrs.class);
        Pqrs pqrs1 = getPqrsSample1();
        Pqrs pqrs2 = new Pqrs();
        assertThat(pqrs1).isNotEqualTo(pqrs2);

        pqrs2.setId(pqrs1.getId());
        assertThat(pqrs1).isEqualTo(pqrs2);

        pqrs2 = getPqrsSample2();
        assertThat(pqrs1).isNotEqualTo(pqrs2);
    }

    @Test
    void archivosAdjuntosTest() {
        Pqrs pqrs = getPqrsRandomSampleGenerator();
        ArchivoAdjunto archivoAdjuntoBack = getArchivoAdjuntoRandomSampleGenerator();

        pqrs.addArchivosAdjuntos(archivoAdjuntoBack);
        assertThat(pqrs.getArchivosAdjuntos()).containsOnly(archivoAdjuntoBack);
        assertThat(archivoAdjuntoBack.getPqrs()).isEqualTo(pqrs);

        pqrs.removeArchivosAdjuntos(archivoAdjuntoBack);
        assertThat(pqrs.getArchivosAdjuntos()).doesNotContain(archivoAdjuntoBack);
        assertThat(archivoAdjuntoBack.getPqrs()).isNull();

        pqrs.archivosAdjuntos(new HashSet<>(Set.of(archivoAdjuntoBack)));
        assertThat(pqrs.getArchivosAdjuntos()).containsOnly(archivoAdjuntoBack);
        assertThat(archivoAdjuntoBack.getPqrs()).isEqualTo(pqrs);

        pqrs.setArchivosAdjuntos(new HashSet<>());
        assertThat(pqrs.getArchivosAdjuntos()).doesNotContain(archivoAdjuntoBack);
        assertThat(archivoAdjuntoBack.getPqrs()).isNull();
    }

    @Test
    void oficinaResponderTest() {
        Pqrs pqrs = getPqrsRandomSampleGenerator();
        Oficina oficinaBack = getOficinaRandomSampleGenerator();

        pqrs.setOficinaResponder(oficinaBack);
        assertThat(pqrs.getOficinaResponder()).isEqualTo(oficinaBack);

        pqrs.oficinaResponder(null);
        assertThat(pqrs.getOficinaResponder()).isNull();
    }
}
