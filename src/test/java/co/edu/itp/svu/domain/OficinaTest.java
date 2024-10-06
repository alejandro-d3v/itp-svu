package co.edu.itp.svu.domain;

import static co.edu.itp.svu.domain.NotificacionTestSamples.*;
import static co.edu.itp.svu.domain.OficinaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import co.edu.itp.svu.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OficinaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Oficina.class);
        Oficina oficina1 = getOficinaSample1();
        Oficina oficina2 = new Oficina();
        assertThat(oficina1).isNotEqualTo(oficina2);

        oficina2.setId(oficina1.getId());
        assertThat(oficina1).isEqualTo(oficina2);

        oficina2 = getOficinaSample2();
        assertThat(oficina1).isNotEqualTo(oficina2);
    }

    @Test
    void notificacionTest() {
        Oficina oficina = getOficinaRandomSampleGenerator();
        Notificacion notificacionBack = getNotificacionRandomSampleGenerator();

        oficina.addNotificacion(notificacionBack);
        assertThat(oficina.getNotificacions()).containsOnly(notificacionBack);
        assertThat(notificacionBack.getDestinatarios()).containsOnly(oficina);

        oficina.removeNotificacion(notificacionBack);
        assertThat(oficina.getNotificacions()).doesNotContain(notificacionBack);
        assertThat(notificacionBack.getDestinatarios()).doesNotContain(oficina);

        oficina.notificacions(new HashSet<>(Set.of(notificacionBack)));
        assertThat(oficina.getNotificacions()).containsOnly(notificacionBack);
        assertThat(notificacionBack.getDestinatarios()).containsOnly(oficina);

        oficina.setNotificacions(new HashSet<>());
        assertThat(oficina.getNotificacions()).doesNotContain(notificacionBack);
        assertThat(notificacionBack.getDestinatarios()).doesNotContain(oficina);
    }
}
