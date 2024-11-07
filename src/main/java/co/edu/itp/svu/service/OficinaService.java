package co.edu.itp.svu.service;

import co.edu.itp.svu.domain.Oficina;
import co.edu.itp.svu.domain.Pqrs;
import co.edu.itp.svu.domain.User;
import co.edu.itp.svu.repository.OficinaRepository;
import co.edu.itp.svu.repository.PqrsRepository;
import co.edu.itp.svu.service.dto.OficinaDTO;
import co.edu.itp.svu.service.dto.PqrsDTO;
import co.edu.itp.svu.service.mapper.OficinaMapper;
import co.edu.itp.svu.service.mapper.PqrsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link co.edu.itp.svu.domain.Oficina}.
 */
@Service
public class OficinaService {

    private static final Logger LOG = LoggerFactory.getLogger(OficinaService.class);

    private UserService userService;

    private final OficinaRepository oficinaRepository;
    private final PqrsRepository pqrsRepository;
    private final OficinaMapper oficinaMapper;
    private final PqrsMapper pqrsMapper;

    public OficinaService(
        OficinaRepository oficinaRepository,
        PqrsRepository pqrsRepository,
        OficinaMapper oficinaMapper,
        PqrsMapper pqrsMapper,
        UserService userService
    ) {
        this.oficinaRepository = oficinaRepository;
        this.pqrsRepository = pqrsRepository;
        this.oficinaMapper = oficinaMapper;
        this.pqrsMapper = pqrsMapper;

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

    //Obtener una Oficina con cada
    public Optional<OficinaDTO> getOficina(String id) {
        Optional<Oficina> oficinaOpt = oficinaRepository.findById(id);

        if (oficinaOpt.isPresent()) {
            Oficina oficina = oficinaOpt.get(); // Obtener la oficina
            List<Pqrs> allPqrs = pqrsRepository.findByOficinaResponder_Id(id); // Obtener todas las PQRS

            // Filtrar y agregar PQRS que pertenecen a esta oficina
            for (Pqrs pqrs : allPqrs) {
                if (pqrs.getOficinaResponder().getId().equals(oficina.getId())) {
                    oficina.addPqrs(pqrs);
                }
            }
        }

        return oficinaOpt.map(oficinaMapper::toDto);
    }

    public Optional<OficinaDTO> getOficinaUser(String id) {
        Optional<Oficina> oficinaOpt = Optional.ofNullable(oficinaRepository.findByResponsable_Id(id));

        if (oficinaOpt.isPresent()) {
            Oficina oficina = oficinaOpt.get(); // Obtener la oficina
            List<Pqrs> allPqrs = pqrsRepository.findByOficinaResponder_Id(oficinaOpt.get().getId()); // Obtener todas las PQRS

            // Filtrar y agregar PQRS que pertenecen a esta oficina
            for (Pqrs pqrs : allPqrs) {
                if (pqrs.getOficinaResponder().getId().equals(oficina.getId())) {
                    oficina.addPqrs(pqrs);
                }
            }
        }

        return oficinaOpt.map(oficinaMapper::toDto);
    }

    /**
     * Obtiene una lista paginada de PQRS asociadas a un usuario específico.
     *
     * @param userId El ID del usuario para el cual se buscan las PQRS.
     * @param order Parámetro opcional para especificar el orden de los resultados.
     * @param pageable Información de paginación que incluye el número de página y el tamaño de la página.
     * @param search Término de búsqueda opcional para filtrar las PQRS por título o descripción.
     * @return Una página de objetos PqrsDTO que representan las PQRS asociadas al usuario.
     *         Si no se encuentra la oficina, se retorna una página vacía.
     */
    public Page<PqrsDTO> findPqrsByUserIdWithPagination(String userId, String order, Pageable pageable, String search) {
        Optional<Oficina> oficinaOpt = Optional.ofNullable(oficinaRepository.findByResponsable_Id(userId));

        if (oficinaOpt.isPresent()) {
            Oficina oficina = oficinaOpt.get();

            // Obtener PQRS paginados
            Page<Pqrs> pqrsPage = pqrsRepository.findByOficinaResponder_Id(oficina.getId(), pageable);

            // filtrar por título o descripción si 'search' no es nulo
            // if (search != null && !search.isEmpty()) {
            //   pqrsPage = pqrsPage.filter(pqrs ->
            //     pqrs.getTitulo().contains(search) ||
            //     pqrs.getDescripcion().contains(search)
            //   );
            // }

            return pqrsPage.map(pqrsMapper::toDto);
        }

        return Page.empty();
    }
}
