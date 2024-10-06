package co.edu.itp.svu.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.itp.svu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArchivoAdjuntoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArchivoAdjuntoDTO.class);
        ArchivoAdjuntoDTO archivoAdjuntoDTO1 = new ArchivoAdjuntoDTO();
        archivoAdjuntoDTO1.setId("id1");
        ArchivoAdjuntoDTO archivoAdjuntoDTO2 = new ArchivoAdjuntoDTO();
        assertThat(archivoAdjuntoDTO1).isNotEqualTo(archivoAdjuntoDTO2);
        archivoAdjuntoDTO2.setId(archivoAdjuntoDTO1.getId());
        assertThat(archivoAdjuntoDTO1).isEqualTo(archivoAdjuntoDTO2);
        archivoAdjuntoDTO2.setId("id2");
        assertThat(archivoAdjuntoDTO1).isNotEqualTo(archivoAdjuntoDTO2);
        archivoAdjuntoDTO1.setId(null);
        assertThat(archivoAdjuntoDTO1).isNotEqualTo(archivoAdjuntoDTO2);
    }
}
