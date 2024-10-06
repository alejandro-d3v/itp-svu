package co.edu.itp.svu.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.itp.svu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InformePqrsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InformePqrsDTO.class);
        InformePqrsDTO informePqrsDTO1 = new InformePqrsDTO();
        informePqrsDTO1.setId("id1");
        InformePqrsDTO informePqrsDTO2 = new InformePqrsDTO();
        assertThat(informePqrsDTO1).isNotEqualTo(informePqrsDTO2);
        informePqrsDTO2.setId(informePqrsDTO1.getId());
        assertThat(informePqrsDTO1).isEqualTo(informePqrsDTO2);
        informePqrsDTO2.setId("id2");
        assertThat(informePqrsDTO1).isNotEqualTo(informePqrsDTO2);
        informePqrsDTO1.setId(null);
        assertThat(informePqrsDTO1).isNotEqualTo(informePqrsDTO2);
    }
}
