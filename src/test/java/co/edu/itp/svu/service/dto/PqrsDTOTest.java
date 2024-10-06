package co.edu.itp.svu.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.itp.svu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PqrsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PqrsDTO.class);
        PqrsDTO pqrsDTO1 = new PqrsDTO();
        pqrsDTO1.setId("id1");
        PqrsDTO pqrsDTO2 = new PqrsDTO();
        assertThat(pqrsDTO1).isNotEqualTo(pqrsDTO2);
        pqrsDTO2.setId(pqrsDTO1.getId());
        assertThat(pqrsDTO1).isEqualTo(pqrsDTO2);
        pqrsDTO2.setId("id2");
        assertThat(pqrsDTO1).isNotEqualTo(pqrsDTO2);
        pqrsDTO1.setId(null);
        assertThat(pqrsDTO1).isNotEqualTo(pqrsDTO2);
    }
}
