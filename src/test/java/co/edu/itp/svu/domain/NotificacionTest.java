package co.edu.itp.svu.domain;

import static co.edu.itp.svu.domain.NotificacionTestSamples.*;
import static co.edu.itp.svu.domain.OficinaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import co.edu.itp.svu.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class NotificacionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notificacion.class);
        Notificacion notificacion1 = getNotificacionSample1();
        Notificacion notificacion2 = new Notificacion();
        assertThat(notificacion1).isNotEqualTo(notificacion2);

        notificacion2.setId(notificacion1.getId());
        assertThat(notificacion1).isEqualTo(notificacion2);

        notificacion2 = getNotificacionSample2();
        assertThat(notificacion1).isNotEqualTo(notificacion2);
    }

    @Test
    void destinatariosTest() {
        Notificacion notificacion = getNotificacionRandomSampleGenerator();
        Oficina oficinaBack = getOficinaRandomSampleGenerator();

        notificacion.addDestinatarios(oficinaBack);
        assertThat(notificacion.getDestinatarios()).containsOnly(oficinaBack);

        notificacion.removeDestinatarios(oficinaBack);
        assertThat(notificacion.getDestinatarios()).doesNotContain(oficinaBack);

        notificacion.destinatarios(new HashSet<>(Set.of(oficinaBack)));
        assertThat(notificacion.getDestinatarios()).containsOnly(oficinaBack);

        notificacion.setDestinatarios(new HashSet<>());
        assertThat(notificacion.getDestinatarios()).doesNotContain(oficinaBack);
    }
}
