package co.edu.itp.svu.service.mapper;

import static co.edu.itp.svu.domain.PqrsAsserts.*;
import static co.edu.itp.svu.domain.PqrsTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PqrsMapperTest {

    private PqrsMapper pqrsMapper;

    @BeforeEach
    void setUp() {
        pqrsMapper = new PqrsMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPqrsSample1();
        var actual = pqrsMapper.toEntity(pqrsMapper.toDto(expected));
        assertPqrsAllPropertiesEquals(expected, actual);
    }
}
