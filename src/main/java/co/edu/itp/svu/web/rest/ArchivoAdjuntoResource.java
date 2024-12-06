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
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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
        PqrsService pqrsService,
        RespuestaService respuestaService,
        ArchivoAdjuntoRepository archivoAdjuntoRepository
    ) {
        this.archivoAdjuntoService = archivoAdjuntoService;
        this.pqrsService = pqrsService;
        this.respuestaService = respuestaService;
        this.archivoAdjuntoRepository = archivoAdjuntoRepository;
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

        // Usando una ruta absoluta o relativa
        File directory = new File(System.getProperty("user.dir") + "/uploads/files/");
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs();
            if (!dirCreated) {
                throw new RuntimeException("No se pudo crear el directorio para guardar el archivo.");
            }
        }

        String originalFilename = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFilename; // Generar un nombre único
        String filePath = directory.getPath() + "/" + uniqueFileName;

        try {
            file.transferTo(new File(filePath));

            // Generar la URL completa del archivo guardado
            String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/files/")
                .path(uniqueFileName)
                .toUriString();
            archivoAdjuntoDTO.setUrlArchivo(fileUrl); // Asignar la URL al DTO
        } catch (IOException e) {
            LOG.error("Error al guardar el archivo en el sistema de archivos", e);
            throw new RuntimeException("Error al guardar el archivo", e);
        }

        archivoAdjuntoDTO = archivoAdjuntoService.save(archivoAdjuntoDTO);

        return ResponseEntity.created(new URI("/api/archivo-adjuntos/" + archivoAdjuntoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, archivoAdjuntoDTO.getId()))
            .body(archivoAdjuntoDTO);
    }

    /**
     * {@code PUT  /archivo-adjuntos/:id} : Updates an existing archivoAdjunto.
     *
     * @param id the id of the archivoAdjuntoDTO to save.
     * @param archivoAdjuntoDTO the archivoAdjuntoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated archivoAdjuntoDTO,
     * or with status {@code 400 (Bad Request)} if the archivoAdjuntoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the archivoAdjuntoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArchivoAdjuntoDTO> updateArchivoAdjunto(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ArchivoAdjuntoDTO archivoAdjuntoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ArchivoAdjunto : {}, {}", id, archivoAdjuntoDTO);
        if (archivoAdjuntoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, archivoAdjuntoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!archivoAdjuntoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        archivoAdjuntoDTO = archivoAdjuntoService.update(archivoAdjuntoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, archivoAdjuntoDTO.getId()))
            .body(archivoAdjuntoDTO);
    }

    /**
     * {@code PATCH  /archivo-adjuntos/:id} : Partial updates given fields of an existing archivoAdjunto, field will ignore if it is null
     *
     * @param id the id of the archivoAdjuntoDTO to save.
     * @param archivoAdjuntoDTO the archivoAdjuntoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated archivoAdjuntoDTO,
     * or with status {@code 400 (Bad Request)} if the archivoAdjuntoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the archivoAdjuntoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the archivoAdjuntoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArchivoAdjuntoDTO> partialUpdateArchivoAdjunto(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ArchivoAdjuntoDTO archivoAdjuntoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ArchivoAdjunto partially : {}, {}", id, archivoAdjuntoDTO);
        if (archivoAdjuntoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, archivoAdjuntoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!archivoAdjuntoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArchivoAdjuntoDTO> result = archivoAdjuntoService.partialUpdate(archivoAdjuntoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, archivoAdjuntoDTO.getId())
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
}
