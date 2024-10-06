package co.edu.itp.svu.service.mapper;

import static co.edu.itp.svu.domain.ArchivoAdjuntoAsserts.*;
import static co.edu.itp.svu.domain.ArchivoAdjuntoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArchivoAdjuntoMapperTest {

    private ArchivoAdjuntoMapper archivoAdjuntoMapper;

    @BeforeEach
    void setUp() {
        archivoAdjuntoMapper = new ArchivoAdjuntoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getArchivoAdjuntoSample1();
        var actual = archivoAdjuntoMapper.toEntity(archivoAdjuntoMapper.toDto(expected));
        assertArchivoAdjuntoAllPropertiesEquals(expected, actual);
    }
}
