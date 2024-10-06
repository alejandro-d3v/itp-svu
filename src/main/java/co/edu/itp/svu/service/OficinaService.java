package co.edu.itp.svu.service;

import co.edu.itp.svu.domain.Oficina;
import co.edu.itp.svu.repository.OficinaRepository;
import co.edu.itp.svu.service.dto.OficinaDTO;
import co.edu.itp.svu.service.mapper.OficinaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link co.edu.itp.svu.domain.Oficina}.
 */
@Service
public class OficinaService {

    private static final Logger LOG = LoggerFactory.getLogger(OficinaService.class);

    private final OficinaRepository oficinaRepository;

    private final OficinaMapper oficinaMapper;

    public OficinaService(OficinaRepository oficinaRepository, OficinaMapper oficinaMapper) {
        this.oficinaRepository = oficinaRepository;
        this.oficinaMapper = oficinaMapper;
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
}
