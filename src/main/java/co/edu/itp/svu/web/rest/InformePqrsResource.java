package co.edu.itp.svu.web.rest;

import co.edu.itp.svu.repository.InformePqrsRepository;
import co.edu.itp.svu.service.InformePqrsService;
import co.edu.itp.svu.service.dto.InformePqrsDTO;
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
 * REST controller for managing {@link co.edu.itp.svu.domain.InformePqrs}.
 */
@RestController
@RequestMapping("/api/informe-pqrs")
public class InformePqrsResource {

    private static final Logger LOG = LoggerFactory.getLogger(InformePqrsResource.class);

    private static final String ENTITY_NAME = "informePqrs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InformePqrsService informePqrsService;

    private final InformePqrsRepository informePqrsRepository;

    public InformePqrsResource(InformePqrsService informePqrsService, InformePqrsRepository informePqrsRepository) {
        this.informePqrsService = informePqrsService;
        this.informePqrsRepository = informePqrsRepository;
    }

    /**
     * {@code POST  /informe-pqrs} : Create a new informePqrs.
     *
     * @param informePqrsDTO the informePqrsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new informePqrsDTO, or with status {@code 400 (Bad Request)} if the informePqrs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InformePqrsDTO> createInformePqrs(@Valid @RequestBody InformePqrsDTO informePqrsDTO) throws URISyntaxException {
        LOG.debug("REST request to save InformePqrs : {}", informePqrsDTO);
        if (informePqrsDTO.getId() != null) {
            throw new BadRequestAlertException("A new informePqrs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        informePqrsDTO = informePqrsService.save(informePqrsDTO);
        return ResponseEntity.created(new URI("/api/informe-pqrs/" + informePqrsDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, informePqrsDTO.getId()))
            .body(informePqrsDTO);
    }

    /**
     * {@code PUT  /informe-pqrs/:id} : Updates an existing informePqrs.
     *
     * @param id the id of the informePqrsDTO to save.
     * @param informePqrsDTO the informePqrsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated informePqrsDTO,
     * or with status {@code 400 (Bad Request)} if the informePqrsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the informePqrsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InformePqrsDTO> updateInformePqrs(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody InformePqrsDTO informePqrsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update InformePqrs : {}, {}", id, informePqrsDTO);
        if (informePqrsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, informePqrsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!informePqrsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        informePqrsDTO = informePqrsService.update(informePqrsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, informePqrsDTO.getId()))
            .body(informePqrsDTO);
    }

    /**
     * {@code PATCH  /informe-pqrs/:id} : Partial updates given fields of an existing informePqrs, field will ignore if it is null
     *
     * @param id the id of the informePqrsDTO to save.
     * @param informePqrsDTO the informePqrsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated informePqrsDTO,
     * or with status {@code 400 (Bad Request)} if the informePqrsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the informePqrsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the informePqrsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InformePqrsDTO> partialUpdateInformePqrs(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody InformePqrsDTO informePqrsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update InformePqrs partially : {}, {}", id, informePqrsDTO);
        if (informePqrsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, informePqrsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!informePqrsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InformePqrsDTO> result = informePqrsService.partialUpdate(informePqrsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, informePqrsDTO.getId())
        );
    }

    /**
     * {@code GET  /informe-pqrs} : get all the informePqrs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of informePqrs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<InformePqrsDTO>> getAllInformePqrs(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of InformePqrs");
        Page<InformePqrsDTO> page = informePqrsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /informe-pqrs/:id} : get the "id" informePqrs.
     *
     * @param id the id of the informePqrsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the informePqrsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InformePqrsDTO> getInformePqrs(@PathVariable("id") String id) {
        LOG.debug("REST request to get InformePqrs : {}", id);
        Optional<InformePqrsDTO> informePqrsDTO = informePqrsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(informePqrsDTO);
    }

    /**
     * {@code DELETE  /informe-pqrs/:id} : delete the "id" informePqrs.
     *
     * @param id the id of the informePqrsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInformePqrs(@PathVariable("id") String id) {
        LOG.debug("REST request to delete InformePqrs : {}", id);
        informePqrsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
