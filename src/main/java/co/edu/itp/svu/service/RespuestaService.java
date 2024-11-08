package co.edu.itp.svu.service;

import co.edu.itp.svu.domain.Respuesta;
import co.edu.itp.svu.repository.RespuestaRepository;
import co.edu.itp.svu.service.dto.RespuestaDTO;
import co.edu.itp.svu.service.mapper.RespuestaMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link co.edu.itp.svu.domain.Respuesta}.
 */
@Service
public class RespuestaService {

    private static final Logger LOG = LoggerFactory.getLogger(RespuestaService.class);

    private final RespuestaRepository respuestaRepository;

    private final RespuestaMapper respuestaMapper;

    public RespuestaService(RespuestaRepository respuestaRepository, RespuestaMapper respuestaMapper) {
        this.respuestaRepository = respuestaRepository;
        this.respuestaMapper = respuestaMapper;
    }

    /**
     * Save a respuesta.
     *
     * @param respuestaDTO the entity to save.
     * @return the persisted entity.
     */
    public RespuestaDTO save(RespuestaDTO respuestaDTO) {
        LOG.debug("Request to save Respuesta : {}", respuestaDTO);
        Respuesta respuesta = respuestaMapper.toEntity(respuestaDTO);
        respuesta = respuestaRepository.save(respuesta);
        return respuestaMapper.toDto(respuesta);
    }

    /**
     * Update a respuesta.
     *
     * @param respuestaDTO the entity to save.
     * @return the persisted entity.
     */
    public RespuestaDTO update(RespuestaDTO respuestaDTO) {
        LOG.debug("Request to update Respuesta : {}", respuestaDTO);
        Respuesta respuesta = respuestaMapper.toEntity(respuestaDTO);
        respuesta = respuestaRepository.save(respuesta);
        return respuestaMapper.toDto(respuesta);
    }

    /**
     * Partially update a respuesta.
     *
     * @param respuestaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RespuestaDTO> partialUpdate(RespuestaDTO respuestaDTO) {
        LOG.debug("Request to partially update Respuesta : {}", respuestaDTO);

        return respuestaRepository
            .findById(respuestaDTO.getId())
            .map(existingRespuesta -> {
                respuestaMapper.partialUpdate(existingRespuesta, respuestaDTO);

                return existingRespuesta;
            })
            .map(respuestaRepository::save)
            .map(respuestaMapper::toDto);
    }

    /**
     * Get all the respuestas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<RespuestaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Respuestas");
        return respuestaRepository.findAll(pageable).map(respuestaMapper::toDto);
    }

    /**
     * Get one respuesta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<RespuestaDTO> findOne(String id) {
        LOG.debug("Request to get Respuesta : {}", id);
        return respuestaRepository.findById(id).map(respuestaMapper::toDto);
    }

    /**
     * Delete the respuesta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        LOG.debug("Request to delete Respuesta : {}", id);
        respuestaRepository.deleteById(id);
    }

    public Optional<RespuestaDTO> findByPqrsId(String pqrsId) {
        LOG.debug("Request to get respuestas by Pqrs ID : {}", pqrsId);
        return respuestaRepository.findByPqrId(pqrsId).stream().map(respuestaMapper::toDto).reduce((first, second) -> second);
    }
}
