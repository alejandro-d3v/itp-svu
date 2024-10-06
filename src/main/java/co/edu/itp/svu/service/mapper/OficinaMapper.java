package co.edu.itp.svu.service.mapper;

import co.edu.itp.svu.domain.Oficina;
import co.edu.itp.svu.service.dto.OficinaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Oficina} and its DTO {@link OficinaDTO}.
 */
@Mapper(componentModel = "spring")
public interface OficinaMapper extends EntityMapper<OficinaDTO, Oficina> {}
