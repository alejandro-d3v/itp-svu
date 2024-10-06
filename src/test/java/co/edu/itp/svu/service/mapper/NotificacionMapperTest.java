package co.edu.itp.svu.service.mapper;

import static co.edu.itp.svu.domain.NotificacionAsserts.*;
import static co.edu.itp.svu.domain.NotificacionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotificacionMapperTest {

    private NotificacionMapper notificacionMapper;

    @BeforeEach
    void setUp() {
        notificacionMapper = new NotificacionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNotificacionSample1();
        var actual = notificacionMapper.toEntity(notificacionMapper.toDto(expected));
        assertNotificacionAllPropertiesEquals(expected, actual);
    }
}
