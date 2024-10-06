package co.edu.itp.svu.service.mapper;

import co.edu.itp.svu.domain.Oficina;
import co.edu.itp.svu.domain.Pqrs;
import co.edu.itp.svu.service.dto.OficinaDTO;
import co.edu.itp.svu.service.dto.PqrsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pqrs} and its DTO {@link PqrsDTO}.
 */
@Mapper(componentModel = "spring")
public interface PqrsMapper extends EntityMapper<PqrsDTO, Pqrs> {
    @Mapping(target = "oficinaResponder", source = "oficinaResponder", qualifiedByName = "oficinaId")
    PqrsDTO toDto(Pqrs s);

    @Named("oficinaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OficinaDTO toDtoOficinaId(Oficina oficina);
}
