package co.edu.itp.svu.service.mapper;

import static co.edu.itp.svu.domain.InformePqrsAsserts.*;
import static co.edu.itp.svu.domain.InformePqrsTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InformePqrsMapperTest {

    private InformePqrsMapper informePqrsMapper;

    @BeforeEach
    void setUp() {
        informePqrsMapper = new InformePqrsMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getInformePqrsSample1();
        var actual = informePqrsMapper.toEntity(informePqrsMapper.toDto(expected));
        assertInformePqrsAllPropertiesEquals(expected, actual);
    }
}
