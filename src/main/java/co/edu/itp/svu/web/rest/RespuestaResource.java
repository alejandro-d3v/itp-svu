package co.edu.itp.svu.web.rest;

import co.edu.itp.svu.repository.RespuestaRepository;
import co.edu.itp.svu.service.RespuestaService;
import co.edu.itp.svu.service.dto.RespuestaDTO;
import co.edu.itp.svu.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.edu.itp.svu.domain.Respuesta}.
 */
@RestController
@RequestMapping("/api/respuestas")
public class RespuestaResource {

    private static final Logger LOG = LoggerFactory.getLogger(RespuestaResource.class);

    private static final String ENTITY_NAME = "respuesta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RespuestaService respuestaService;

    private final RespuestaRepository respuestaRepository;

    public RespuestaResource(RespuestaService respuestaService, RespuestaRepository respuestaRepository) {
        this.respuestaService = respuestaService;
        this.respuestaRepository = respuestaRepository;
    }

    /**
     * {@code POST  /respuestas} : Create a new respuesta.
     *
     * @param respuestaDTO the respuestaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new respuestaDTO, or with status {@code 400 (Bad Request)} if the respuesta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RespuestaDTO> createRespuesta(@Valid @RequestBody RespuestaDTO respuestaDTO) throws URISyntaxException {
        LOG.debug("REST request to save Respuesta : {}", respuestaDTO);
        if (respuestaDTO.getId() != null) {
            throw new BadRequestAlertException("A new respuesta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        respuestaDTO = respuestaService.save(respuestaDTO);
        return ResponseEntity.created(new URI("/api/respuestas/" + respuestaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, respuestaDTO.getId()))
            .body(respuestaDTO);
    }

    /**
     * {@code PUT  /respuestas/:id} : Updates an existing respuesta.
     *
     * @param id the id of the respuestaDTO to save.
     * @param respuestaDTO the respuestaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated respuestaDTO,
     * or with status {@code 400 (Bad Request)} if the respuestaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the respuestaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaDTO> updateRespuesta(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody RespuestaDTO respuestaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Respuesta : {}, {}", id, respuestaDTO);
        if (respuestaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, respuestaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!respuestaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        respuestaDTO = respuestaService.update(respuestaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, respuestaDTO.getId()))
            .body(respuestaDTO);
    }

    /**
     * {@code PATCH  /respuestas/:id} : Partial updates given fields of an existing respuesta, field will ignore if it is null
     *
     * @param id the id of the respuestaDTO to save.
     * @param respuestaDTO the respuestaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated respuestaDTO,
     * or with status {@code 400 (Bad Request)} if the respuestaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the respuestaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the respuestaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RespuestaDTO> partialUpdateRespuesta(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody RespuestaDTO respuestaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Respuesta partially : {}, {}", id, respuestaDTO);
        if (respuestaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, respuestaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!respuestaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RespuestaDTO> result = respuestaService.partialUpdate(respuestaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, respuestaDTO.getId())
        );
    }

    /**
     * {@code GET  /respuestas} : get all the respuestas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of respuestas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RespuestaDTO>> getAllRespuestas(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Respuestas");
        Page<RespuestaDTO> page = respuestaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /respuestas/:id} : get the "id" respuesta.
     *
     * @param id the id of the respuestaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the respuestaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaDTO> getRespuesta(@PathVariable("id") String id) {
        LOG.debug("REST request to get Respuesta : {}", id);
        Optional<RespuestaDTO> respuestaDTO = respuestaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(respuestaDTO);
    }

    /**
     * {@code DELETE  /respuestas/:id} : delete the "id" respuesta.
     *
     * @param id the id of the respuestaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRespuesta(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Respuesta : {}", id);
        respuestaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
