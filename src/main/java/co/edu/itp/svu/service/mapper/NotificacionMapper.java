package co.edu.itp.svu.service.mapper;

import co.edu.itp.svu.domain.Notificacion;
import co.edu.itp.svu.domain.Oficina;
import co.edu.itp.svu.service.dto.NotificacionDTO;
import co.edu.itp.svu.service.dto.OficinaDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notificacion} and its DTO {@link NotificacionDTO}.
 */
@Mapper(componentModel = "spring")
public interface NotificacionMapper extends EntityMapper<NotificacionDTO, Notificacion> {
    @Mapping(target = "destinatarios", source = "destinatarios", qualifiedByName = "oficinaIdSet")
    NotificacionDTO toDto(Notificacion s);

    @Mapping(target = "removeDestinatarios", ignore = true)
    Notificacion toEntity(NotificacionDTO notificacionDTO);

    @Named("oficinaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OficinaDTO toDtoOficinaId(Oficina oficina);

    @Named("oficinaIdSet")
    default Set<OficinaDTO> toDtoOficinaIdSet(Set<Oficina> oficina) {
        return oficina.stream().map(this::toDtoOficinaId).collect(Collectors.toSet());
    }
}
