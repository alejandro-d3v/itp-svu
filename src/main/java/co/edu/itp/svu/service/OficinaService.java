package co.edu.itp.svu.service;

import co.edu.itp.svu.domain.Oficina;
import co.edu.itp.svu.domain.User;
import co.edu.itp.svu.repository.OficinaRepository;
import co.edu.itp.svu.service.dto.OficinaDTO;
import co.edu.itp.svu.service.mapper.OficinaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.jhipster.web.util.HeaderUtil;

/**
 * Service Implementation for managing {@link co.edu.itp.svu.domain.Oficina}.
 */
@Service
public class OficinaService {

    private static final Logger LOG = LoggerFactory.getLogger(OficinaService.class);

    private UserService userService; // Suponiendo que tienes un servicio de User

    private final OficinaRepository oficinaRepository;

    private final OficinaMapper oficinaMapper;

    public OficinaService(OficinaRepository oficinaRepository, OficinaMapper oficinaMapper, UserService userService) {
        this.oficinaRepository = oficinaRepository;
        this.oficinaMapper = oficinaMapper;
        this.userService = userService;
    }

    /**
     * Save a oficina.
     *
     * @param oficinaDTO the entity to save.
     * @return the persisted entity.
     */
    public OficinaDTO save(OficinaDTO oficinaDTO) {
        LOG.debug("Request to save Oficina : {}", oficinaDTO);
        Oficina oficina = oficinaMapper.toEntity(oficinaDTO);
        oficina = oficinaRepository.save(oficina);
        return oficinaMapper.toDto(oficina);
    }

    /**
     * Update a oficina.
     *
     * @param oficinaDTO the entity to save.
     * @return the persisted entity.
     */
    public OficinaDTO update(OficinaDTO oficinaDTO) {
        LOG.debug("Request to update Oficina : {}", oficinaDTO);
        Oficina oficina = oficinaMapper.toEntity(oficinaDTO);
        oficina = oficinaRepository.save(oficina);
        return oficinaMapper.toDto(oficina);
    }

    /**
     * Partially update a oficina.
     *
     * @param oficinaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OficinaDTO> partialUpdate(OficinaDTO oficinaDTO) {
        LOG.debug("Request to partially update Oficina : {}", oficinaDTO);

        return oficinaRepository
            .findById(oficinaDTO.getId())
            .map(existingOficina -> {
                oficinaMapper.partialUpdate(existingOficina, oficinaDTO);

                return existingOficina;
            })
            .map(oficinaRepository::save)
            .map(oficinaMapper::toDto);
    }

    /**
     * Get all the oficinas.
     *
     * @return the list of entities.
     */
    public List<OficinaDTO> findAll() {
        LOG.debug("Request to get all Oficinas");
        return oficinaRepository.findAll().stream().map(oficinaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one oficina by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<OficinaDTO> findOne(String id) {
        LOG.debug("Request to get Oficina : {}", id);
        return oficinaRepository.findById(id).map(oficinaMapper::toDto);
    }

    /**
     * Delete the oficina by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        LOG.debug("Request to delete Oficina : {}", id);
        oficinaRepository.deleteById(id);
    }

    /////////////////modificaciones////////////////////////////////////////7
    // Crear una nueva oficina con un usuario responsable

    /* public OficinaDTO createOficina(OficinaDTO oficinaDTO, String usernameResponsable) {
        LOG.debug("Request to save Oficina : {}", oficinaDTO);

        // Validación inicial del DTO
        if (oficinaDTO == null) {
            throw new IllegalArgumentException("OficinaDTO no puede ser null");
        }

        Optional<User> responsable = userService.getUserWithAuthoritiesByLogin(usernameResponsable);
        if (!responsable.isPresent()) {
            LOG.warn("El usuario responsable no existe: {}", usernameResponsable);
            throw new IllegalArgumentException("El usuario responsable no existe");
        }

        // Mapeo y guardado
        Oficina oficina = oficinaMapper.toEntity(oficinaDTO);
        oficina.setResponsable(responsable.get()); // Asocia el User como responsable
        oficina = oficinaRepository.save(oficina);

        LOG.info("Oficina creada con éxito: {}", oficina);
        return oficinaMapper.toDto(oficina);
    }*/

    /* public OficinaDTO createOficina(OficinaDTO oficinaDTO) {
        LOG.debug("Request to save Oficina : {}", oficinaDTO);

        // Validación inicial del DTO
        if (oficinaDTO == null) {
            throw new IllegalArgumentException("OficinaDTO no puede ser null");
        }
        String usernameResponsable = oficinaDTO.getResponsableDTO().getLogin();
        Optional<User> responsable = userService.getUserWithAuthoritiesByLogin(usernameResponsable);
        if (!responsable.isPresent()) {
            LOG.warn("El usuario responsable no existe: {}", usernameResponsable);
            throw new IllegalArgumentException("El usuario responsable no existe");
        }

        // Mapeo y guardado
        Oficina oficina = oficinaMapper.toEntity(oficinaDTO);
        oficina.setResponsable(responsable.get()); // Asocia el User como responsable
        oficina = oficinaRepository.save(oficina);

        LOG.info("Oficina creada con éxito: {}", oficina);
        return oficinaMapper.toDto(oficina);
    }
*/
    public OficinaDTO createOficina(OficinaDTO oficinaDTO) {
        LOG.debug("Request to save Oficina : {}", oficinaDTO);

        // Validación inicial del DTO
        if (oficinaDTO == null) {
            LOG.error("OficinaDTO no puede ser null");
            throw new IllegalArgumentException("OficinaDTO no puede ser null");
        }

        // Validar campos obligatorios
        if (oficinaDTO.getNombre() == null || oficinaDTO.getNombre().isEmpty()) {
            LOG.error("El nombre de la oficina es requerido");
            throw new IllegalArgumentException("El nombre de la oficina es requerido");
        }

        if (oficinaDTO.getResponsableDTO() == null || oficinaDTO.getResponsableDTO().getLogin() == null) {
            LOG.error("El usuario responsable es requerido");
            throw new IllegalArgumentException("El usuario responsable es requerido");
        }

        String usernameResponsable = oficinaDTO.getResponsableDTO().getLogin();
        Optional<User> responsable = userService.getUserWithAuthoritiesByLogin(usernameResponsable);

        if (!responsable.isPresent()) {
            LOG.warn("El usuario responsable no existe: {}", usernameResponsable);
            throw new IllegalArgumentException("El usuario responsable no existe");
        }

        // Mapeo del DTO a la entidad
        Oficina oficina;
        try {
            oficina = oficinaMapper.toEntity(oficinaDTO);
            if (oficina == null) {
                LOG.error("Error al mapear OficinaDTO a Oficina");
                throw new IllegalArgumentException("Error al mapear OficinaDTO a Oficina");
            }

            oficina.setResponsable(responsable.get()); // Asociar el User como responsable
            oficina = oficinaRepository.save(oficina);
        } catch (Exception e) {
            LOG.error("Error al guardar la oficina: {}", e.getMessage());
            throw new RuntimeException("Error al guardar la oficina", e);
        }

        LOG.info("Oficina creada con éxito: {}", oficina);
        return oficinaMapper.toDto(oficina);
    }

    // Actualizar una oficina existente un usuario responsable
    public OficinaDTO updateOficina(OficinaDTO oficinaDTO) {
        LOG.debug("Request to update Oficina : {}", oficinaDTO);
        Oficina oficina = oficinaMapper.toEntity(oficinaDTO);
        Optional<Oficina> oficinaOptional = oficinaRepository.findById(oficinaDTO.getId());
        if (oficinaOptional.isPresent()) {
            String usernameResponsable = oficinaDTO.getResponsableDTO().getLogin();
            oficina = oficinaOptional.get();
            oficina.setNombre(oficinaDTO.getNombre());
            oficina.setOficinaSuperior(oficinaDTO.getOficinaSuperior());
            //oficina.setNotificacions(oficinaDTO.getNotificacions());
            oficina.setDescripcion(oficinaDTO.getDescripcion());
            oficina.setNivel(oficinaDTO.getNivel());
            Optional<User> responsable = userService.getUserWithAuthoritiesByLogin(usernameResponsable);
            if (responsable.isPresent()) {
                oficina.setResponsable(responsable.get());
                oficina = oficinaRepository.save(oficina);
                return oficinaMapper.toDto(oficina);
            } else {
                throw new IllegalArgumentException("El usuario responsable no existe");
            }
        } else {
            throw new IllegalArgumentException("La oficina no existe");
        }
    }
    // Actualizar una oficina existente un usuario responsable
    /*   public OficinaDTO updateOficina(OficinaDTO oficinaDTO, String usernameResponsable) {
        LOG.debug("Request to update Oficina : {}", oficinaDTO);
        Oficina oficina = oficinaMapper.toEntity(oficinaDTO);
        Optional<Oficina> oficinaOptional = oficinaRepository.findById(oficinaDTO.getId());
        if (oficinaOptional.isPresent()) {
            oficina = oficinaOptional.get();
            oficina.setNombre(oficinaDTO.getNombre());
            oficina.setOficinaSuperior(oficinaDTO.getOficinaSuperior());
            //oficina.setNotificacions(oficinaDTO.getNotificacions());
            oficina.setDescripcion(oficinaDTO.getDescripcion());
            oficina.setNivel(oficinaDTO.getNivel());
            Optional<User> responsable = userService.getUserWithAuthoritiesByLogin(usernameResponsable);
            if (responsable.isPresent()) {
                oficina.setResponsable(responsable.get());
                oficina = oficinaRepository.save(oficina);
                return oficinaMapper.toDto(oficina);
            } else {
                throw new IllegalArgumentException("El usuario responsable no existe");
            }
        } else {
            throw new IllegalArgumentException("La oficina no existe");
        }
    }*/
}
