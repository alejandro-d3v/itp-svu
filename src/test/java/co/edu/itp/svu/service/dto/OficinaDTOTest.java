package co.edu.itp.svu.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.itp.svu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OficinaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OficinaDTO.class);
        OficinaDTO oficinaDTO1 = new OficinaDTO();
        oficinaDTO1.setId("id1");
        OficinaDTO oficinaDTO2 = new OficinaDTO();
        assertThat(oficinaDTO1).isNotEqualTo(oficinaDTO2);
        oficinaDTO2.setId(oficinaDTO1.getId());
        assertThat(oficinaDTO1).isEqualTo(oficinaDTO2);
        oficinaDTO2.setId("id2");
        assertThat(oficinaDTO1).isNotEqualTo(oficinaDTO2);
        oficinaDTO1.setId(null);
        assertThat(oficinaDTO1).isNotEqualTo(oficinaDTO2);
    }
}
