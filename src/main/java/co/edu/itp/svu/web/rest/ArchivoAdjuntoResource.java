package co.edu.itp.svu.web.rest;

import co.edu.itp.svu.repository.ArchivoAdjuntoRepository;
import co.edu.itp.svu.service.ArchivoAdjuntoService;
import co.edu.itp.svu.service.PqrsService;
import co.edu.itp.svu.service.RespuestaService;
import co.edu.itp.svu.service.dto.ArchivoAdjuntoDTO;
import co.edu.itp.svu.service.dto.PqrsDTO;
import co.edu.itp.svu.service.dto.RespuestaDTO;
import co.edu.itp.svu.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.edu.itp.svu.domain.ArchivoAdjunto}.
 */
@RestController
@RequestMapping("/api/archivo-adjuntos")
public class ArchivoAdjuntoResource {

    private static final Logger LOG = LoggerFactory.getLogger(ArchivoAdjuntoResource.class);

    private static final String ENTITY_NAME = "archivoAdjunto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArchivoAdjuntoService archivoAdjuntoService;
    private final PqrsService pqrsService;
    private final RespuestaService respuestaService;

    private final ArchivoAdjuntoRepository archivoAdjuntoRepository;

    public ArchivoAdjuntoResource(
        ArchivoAdjuntoService archivoAdjuntoService,
        ArchivoAdjuntoRepository archivoAdjuntoRepository,
        PqrsService pqrsService,
        RespuestaService respuestaService
    ) {
        this.archivoAdjuntoService = archivoAdjuntoService;
        this.archivoAdjuntoRepository = archivoAdjuntoRepository;
        this.pqrsService = pqrsService;
        this.respuestaService = respuestaService;
    }

    /**
     * {@code POST  /archivo-adjuntos} : Create a new archivoAdjunto.
     *
     * @param archivoAdjuntoDTO the archivoAdjuntoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new archivoAdjuntoDTO, or with status {@code 400 (Bad Request)} if the archivoAdjunto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping(value = "", consumes = "multipart/form-data")
    public ResponseEntity<ArchivoAdjuntoDTO> createArchivoAdjunto(
        @RequestParam("file") MultipartFile file,
        @RequestParam(value = "nombre", required = false) String nombre,
        @RequestParam(value = "tipo", required = false) String tipo,
        @RequestParam(value = "urlArchivo", required = false) String urlArchivo,
        @RequestParam(value = "pqrsId", required = false) String pqrsId,
        @RequestParam(value = "respuestaId", required = false) String respuestaId
    ) throws URISyntaxException {
        LOG.debug("REST request to save ArchivoAdjunto : {}", file.getOriginalFilename());

        if (file.isEmpty()) {
            throw new BadRequestAlertException("No file uploaded", ENTITY_NAME, "fileempty");
        }

        ArchivoAdjuntoDTO archivoAdjuntoDTO = new ArchivoAdjuntoDTO();

        archivoAdjuntoDTO.setNombre(nombre);
        archivoAdjuntoDTO.setTipo(tipo);
        archivoAdjuntoDTO.setUrlArchivo(urlArchivo);
        archivoAdjuntoDTO.setFechaSubida(Instant.now());

        if (nombre == null) {
            archivoAdjuntoDTO.setNombre(file.getOriginalFilename());
        }
        if (tipo == null) {
            String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            archivoAdjuntoDTO.setTipo(fileType);
        }
        if (pqrsId != null) {
            PqrsDTO pqrsDTO = pqrsService.findOne(pqrsId).get();
            archivoAdjuntoDTO.setPqrs(pqrsDTO);
        }
        if (respuestaId != null) {
            RespuestaDTO respuestaDTO = respuestaService.findOne(respuestaId).get();
            archivoAdjuntoDTO.setRespuesta(respuestaDTO);
        }

        try {
            byte[] fileBytes = file.getBytes();
            archivoAdjuntoDTO.setFileData(fileBytes);
        } catch (IOException e) {
            throw new BadRequestAlertException("Error reading file", "archivoAdjunto", "fileerror");
        }

        archivoAdjuntoDTO = archivoAdjuntoService.save(archivoAdjuntoDTO);

        return ResponseEntity.created(new URI("/api/archivo-adjuntos/" + archivoAdjuntoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, archivoAdjuntoDTO.getId()))
            .body(archivoAdjuntoDTO);
    }

    /**
     * {@code PUT  /archivo-adjuntos/:id} : Updates an existing archivoAdjunto.
     *
     * @param id                the id of the archivoAdjuntoDTO to save.
     * @param archivoAdjuntoDTO the archivoAdjuntoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated archivoAdjuntoDTO,
     *         or with status {@code 400 (Bad Request)} if the archivoAdjuntoDTO is not valid,
     *         or with status {@code 500 (Internal Server Error)} if the archivoAdjuntoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<ArchivoAdjuntoDTO> updateArchivoAdjunto(
        @PathVariable(value = "id", required = false) final String id,
        @RequestParam(value = "file", required = false) MultipartFile file,
        @RequestParam(value = "nombre", required = false) String nombre,
        @RequestParam(value = "tipo", required = false) String tipo,
        @RequestParam(value = "urlArchivo", required = false) String urlArchivo,
        @RequestParam(value = "pqrsId", required = false) String pqrsId,
        @RequestParam(value = "respuestaId", required = false) String respuestaId
    ) throws URISyntaxException {
        LOG.debug("REST request to update ArchivoAdjunto : {}", id);

        if (!archivoAdjuntoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArchivoAdjuntoDTO archivoAdjuntoDTO = archivoAdjuntoService.findOne(id).orElseThrow();

        if (file != null && !file.isEmpty()) {
            try {
                byte[] fileBytes = file.getBytes();
                archivoAdjuntoDTO.setFileData(fileBytes);
            } catch (IOException e) {
                throw new BadRequestAlertException("Error reading file", ENTITY_NAME, "fileerror");
            }
        }
        if (nombre != null) {
            archivoAdjuntoDTO.setNombre(nombre);
        } else {
            archivoAdjuntoDTO.setNombre(file != null ? file.getOriginalFilename() : archivoAdjuntoDTO.getNombre());
        }
        if (tipo != null) {
            archivoAdjuntoDTO.setTipo(tipo);
        } else if (file != null) {
            String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            archivoAdjuntoDTO.setTipo(fileType);
        }
        if (urlArchivo != null) {
            archivoAdjuntoDTO.setUrlArchivo(urlArchivo);
        }
        if (pqrsId != null) {
            PqrsDTO pqrsDTO = pqrsService.findOne(pqrsId).orElseThrow();
            archivoAdjuntoDTO.setPqrs(pqrsDTO);
        }
        if (respuestaId != null) {
            RespuestaDTO respuestaDTO = respuestaService.findOne(respuestaId).orElseThrow();
            archivoAdjuntoDTO.setRespuesta(respuestaDTO);
        }

        archivoAdjuntoDTO = archivoAdjuntoService.update(archivoAdjuntoDTO);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, archivoAdjuntoDTO.getId()))
            .body(archivoAdjuntoDTO);
    }

    /**
     * {@code PATCH  /archivo-adjuntos/:id} : Partial updates given fields of an
     * existing archivoAdjunto, field will ignore if it is null
     *
     * @param id                the id of the archivoAdjuntoDTO to save.
     * @param archivoAdjuntoDTO the archivoAdjuntoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated archivoAdjuntoDTO,
     *         or with status {@code 400 (Bad Request)} if the archivoAdjuntoDTO is not valid,
     *         or with status {@code 404 (Not Found)} if the archivoAdjuntoDTO is not found,
     *         or with status {@code 500 (Internal Server Error)} if the archivoAdjuntoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArchivoAdjuntoDTO> partialUpdateArchivoAdjunto(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ArchivoAdjuntoDTO archivoAdjuntoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partially update ArchivoAdjunto : {}, {}", id, archivoAdjuntoDTO);

        if (!archivoAdjuntoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArchivoAdjuntoDTO> existingArchivoAdjunto = archivoAdjuntoService.findOne(id);
        if (!existingArchivoAdjunto.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArchivoAdjuntoDTO updatedArchivoAdjunto = existingArchivoAdjunto.get();

        if (archivoAdjuntoDTO.getNombre() != null) {
            updatedArchivoAdjunto.setNombre(archivoAdjuntoDTO.getNombre());
        }
        if (archivoAdjuntoDTO.getTipo() != null) {
            updatedArchivoAdjunto.setTipo(archivoAdjuntoDTO.getTipo());
        }
        if (archivoAdjuntoDTO.getUrlArchivo() != null) {
            updatedArchivoAdjunto.setUrlArchivo(archivoAdjuntoDTO.getUrlArchivo());
        }
        if (archivoAdjuntoDTO.getFile() != null) {
            try {
                byte[] fileBytes = archivoAdjuntoDTO.getFile().getBytes();
                updatedArchivoAdjunto.setFileData(fileBytes);
            } catch (IOException e) {
                throw new BadRequestAlertException("Error reading file", ENTITY_NAME, "fileerror");
            }
        }

        Optional<ArchivoAdjuntoDTO> result = archivoAdjuntoService.partialUpdate(updatedArchivoAdjunto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, updatedArchivoAdjunto.getId())
        );
    }

    /**
     * {@code GET  /archivo-adjuntos} : get all the archivoAdjuntos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of archivoAdjuntos in body.
     */
    @GetMapping("")
    public List<ArchivoAdjuntoDTO> getAllArchivoAdjuntos() {
        LOG.debug("REST request to get all ArchivoAdjuntos");
        return archivoAdjuntoService.findAll();
    }

    /**
     * {@code GET  /archivo-adjuntos/:id} : get the "id" archivoAdjunto.
     *
     * @param id the id of the archivoAdjuntoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the archivoAdjuntoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArchivoAdjuntoDTO> getArchivoAdjunto(@PathVariable("id") String id) {
        LOG.debug("REST request to get ArchivoAdjunto : {}", id);
        Optional<ArchivoAdjuntoDTO> archivoAdjuntoDTO = archivoAdjuntoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(archivoAdjuntoDTO);
    }

    /**
     * {@code DELETE  /archivo-adjuntos/:id} : delete the "id" archivoAdjunto.
     *
     * @param id the id of the archivoAdjuntoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArchivoAdjunto(@PathVariable("id") String id) {
        LOG.debug("REST request to delete ArchivoAdjunto : {}", id);
        archivoAdjuntoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code GET  /archivo-adjuntos/:id/download} : Download the "id" archivoAdjunto.
     *
     * @param id the id of the archivoAdjuntoDTO to download.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the file data in the body,
     *         or with status {@code 404 (Not Found)} if the archivoAdjuntoDTO is not found,
     *         or with status {@code 400 (Bad Request)} if the file data is null.
     */
    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadArchivoAdjunto(@PathVariable String id) {
        LOG.debug("REST request to download ArchivoAdjunto : {}", id);

        Optional<ArchivoAdjuntoDTO> archivoAdjuntoDTO = archivoAdjuntoService.findOne(id);

        if (!archivoAdjuntoDTO.isPresent() || archivoAdjuntoDTO.get().getFileData() == null) {
            throw new BadRequestAlertException("File not found", ENTITY_NAME, "filenotfound");
        }

        byte[] fileData = archivoAdjuntoDTO.get().getFileData();
        String fileName = archivoAdjuntoDTO.get().getNombre();
        String fileType = archivoAdjuntoDTO.get().getTipo();

        // Asegúrate de que el tipo MIME sea válido
        if (fileType == null || !fileType.contains("/")) {
            fileType = "application/octet-stream"; // Tipo MIME por defecto
        }

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
            .contentType(MediaType.parseMediaType(fileType))
            .body(fileData);
    }
}
