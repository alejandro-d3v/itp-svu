package co.edu.itp.svu.service;

import co.edu.itp.svu.domain.ArchivoAdjunto;
import co.edu.itp.svu.repository.ArchivoAdjuntoRepository;
import co.edu.itp.svu.service.dto.ArchivoAdjuntoDTO;
import co.edu.itp.svu.service.mapper.ArchivoAdjuntoMapper;
import co.edu.itp.svu.web.rest.errors.BadRequestAlertException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link co.edu.itp.svu.domain.ArchivoAdjunto}.
 */
@Service
public class ArchivoAdjuntoService {

    private static final Logger LOG = LoggerFactory.getLogger(ArchivoAdjuntoService.class);
    private static final String ENTITY_NAME = "archivoAdjunto";

    private final ArchivoAdjuntoRepository archivoAdjuntoRepository;

    private final ArchivoAdjuntoMapper archivoAdjuntoMapper;

    public ArchivoAdjuntoService(ArchivoAdjuntoRepository archivoAdjuntoRepository, ArchivoAdjuntoMapper archivoAdjuntoMapper) {
        this.archivoAdjuntoRepository = archivoAdjuntoRepository;
        this.archivoAdjuntoMapper = archivoAdjuntoMapper;
    }

    /**
     * Save a archivoAdjunto.
     *
     * @param archivoAdjuntoDTO the entity to save.
     * @return the persisted entity.
     */
    public ArchivoAdjuntoDTO save(ArchivoAdjuntoDTO archivoAdjuntoDTO) {
        LOG.debug("Request to save ArchivoAdjunto : {}", archivoAdjuntoDTO);

        if (archivoAdjuntoDTO.getFile() != null && !archivoAdjuntoDTO.getFile().isEmpty()) {
            byte[] fileBytes;

            try {
                fileBytes = archivoAdjuntoDTO.getFile().getBytes();
                archivoAdjuntoDTO.setFileData(fileBytes);
            } catch (IOException e) {
                throw new BadRequestAlertException("Error reading file", ENTITY_NAME, "fileerror");
            }
        }

        ArchivoAdjunto archivoAdjunto = archivoAdjuntoMapper.toEntity(archivoAdjuntoDTO);
        archivoAdjunto = archivoAdjuntoRepository.save(archivoAdjunto);

        return archivoAdjuntoMapper.toDto(archivoAdjunto);
    }

    /**
     * Update a archivoAdjunto.
     *
     * @param archivoAdjuntoDTO the entity to save.
     * @return the persisted entity.
     */
    public ArchivoAdjuntoDTO update(ArchivoAdjuntoDTO archivoAdjuntoDTO) {
        LOG.debug("Request to update ArchivoAdjunto : {}", archivoAdjuntoDTO);

        if (archivoAdjuntoDTO.getFile() != null) {
            try {
                byte[] fileBytes = archivoAdjuntoDTO.getFile().getBytes();
                archivoAdjuntoDTO.setFileData(fileBytes);
            } catch (IOException e) {
                throw new BadRequestAlertException("Error reading file", ENTITY_NAME, "fileerror");
            }
        }

        ArchivoAdjunto archivoAdjunto = archivoAdjuntoMapper.toEntity(archivoAdjuntoDTO);
        archivoAdjunto = archivoAdjuntoRepository.save(archivoAdjunto);

        return archivoAdjuntoMapper.toDto(archivoAdjunto);
    }

    /**
     * Partially update a archivoAdjunto.
     *
     * @param archivoAdjuntoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ArchivoAdjuntoDTO> partialUpdate(ArchivoAdjuntoDTO archivoAdjuntoDTO) {
        LOG.debug("Request to partially update ArchivoAdjunto : {}", archivoAdjuntoDTO);

        return archivoAdjuntoRepository
            .findById(archivoAdjuntoDTO.getId())
            .map(existingArchivoAdjunto -> {
                archivoAdjuntoMapper.partialUpdate(existingArchivoAdjunto, archivoAdjuntoDTO);

                if (archivoAdjuntoDTO.getFile() != null) {
                    try {
                        byte[] fileBytes = archivoAdjuntoDTO.getFile().getBytes();
                        existingArchivoAdjunto.setFileData(fileBytes); // Establecer los bytes del archivo
                    } catch (IOException e) {
                        throw new BadRequestAlertException("Error reading file", ENTITY_NAME, "fileerror");
                    }
                }

                return archivoAdjuntoRepository.save(existingArchivoAdjunto);
            })
            .map(archivoAdjuntoMapper::toDto);
    }

    /**
     * Get all the archivoAdjuntos.
     *
     * @return the list of entities.
     */
    public List<ArchivoAdjuntoDTO> findAll() {
        LOG.debug("Request to get all ArchivoAdjuntos");
        return archivoAdjuntoRepository
            .findAll()
            .stream()
            .map(archivoAdjuntoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one archivoAdjunto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ArchivoAdjuntoDTO> findOne(String id) {
        LOG.debug("Request to get ArchivoAdjunto : {}", id);
        return archivoAdjuntoRepository.findById(id).map(archivoAdjuntoMapper::toDto);
    }

    /**
     * Delete the archivoAdjunto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        LOG.debug("Request to delete ArchivoAdjunto : {}", id);
        archivoAdjuntoRepository.deleteById(id);
    }
}
