package co.edu.itp.svu.service.mapper;

import static co.edu.itp.svu.domain.OficinaAsserts.*;
import static co.edu.itp.svu.domain.OficinaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OficinaMapperTest {

    private OficinaMapper oficinaMapper;

    @BeforeEach
    void setUp() {
        oficinaMapper = new OficinaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOficinaSample1();
        var actual = oficinaMapper.toEntity(oficinaMapper.toDto(expected));
        assertOficinaAllPropertiesEquals(expected, actual);
    }
}
