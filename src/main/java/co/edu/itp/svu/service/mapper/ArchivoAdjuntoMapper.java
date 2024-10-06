package co.edu.itp.svu.service.mapper;

import co.edu.itp.svu.domain.ArchivoAdjunto;
import co.edu.itp.svu.domain.Pqrs;
import co.edu.itp.svu.domain.Respuesta;
import co.edu.itp.svu.service.dto.ArchivoAdjuntoDTO;
import co.edu.itp.svu.service.dto.PqrsDTO;
import co.edu.itp.svu.service.dto.RespuestaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArchivoAdjunto} and its DTO {@link ArchivoAdjuntoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArchivoAdjuntoMapper extends EntityMapper<ArchivoAdjuntoDTO, ArchivoAdjunto> {
    @Mapping(target = "pqrs", source = "pqrs", qualifiedByName = "pqrsId")
    @Mapping(target = "respuesta", source = "respuesta", qualifiedByName = "respuestaId")
    ArchivoAdjuntoDTO toDto(ArchivoAdjunto s);

    @Named("pqrsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PqrsDTO toDtoPqrsId(Pqrs pqrs);

    @Named("respuestaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RespuestaDTO toDtoRespuestaId(Respuesta respuesta);
}
