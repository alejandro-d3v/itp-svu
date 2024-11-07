package co.edu.itp.svu.web.rest;

import co.edu.itp.svu.repository.OficinaRepository;
import co.edu.itp.svu.service.OficinaService;
import co.edu.itp.svu.service.dto.OficinaDTO;
import co.edu.itp.svu.service.dto.PqrsDTO;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.edu.itp.svu.domain.Oficina}.
 */
@RestController
@RequestMapping("/api/oficinas")
public class OficinaResource {

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private static final Logger LOG = LoggerFactory.getLogger(OficinaResource.class);
    private static final String ENTITY_NAME = "oficina";

    private final OficinaRepository oficinaRepository;
    private final OficinaService oficinaService;

    public OficinaResource(OficinaService oficinaService, OficinaRepository oficinaRepository) {
        this.oficinaService = oficinaService;
        this.oficinaRepository = oficinaRepository;
    }

    /**
     * {@code POST  /oficinas} : Create a new oficina.
     *
     * @param oficinaDTO the oficinaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new oficinaDTO, or with status {@code 400 (Bad Request)} if
     *         the oficina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OficinaDTO> createOficina(@Valid @RequestBody OficinaDTO oficinaDTO) throws URISyntaxException {
        LOG.debug("REST request to save Oficina : {}", oficinaDTO);
        if (oficinaDTO.getId() != null) {
            throw new BadRequestAlertException("A new oficina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        oficinaDTO = oficinaService.save(oficinaDTO);
        return ResponseEntity.created(new URI("/api/oficinas/" + oficinaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, oficinaDTO.getId()))
            .body(oficinaDTO);
    }

    /**
     * {@code PUT  /oficinas/:id} : Updates an existing oficina.
     *
     * @param id         the id of the oficinaDTO to save.
     * @param oficinaDTO the oficinaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated oficinaDTO,
     *         or with status {@code 400 (Bad Request)} if the oficinaDTO is not
     *         valid,
     *         or with status {@code 500 (Internal Server Error)} if the oficinaDTO
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OficinaDTO> updateOficina(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody OficinaDTO oficinaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Oficina : {}, {}", id, oficinaDTO);
        if (oficinaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oficinaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oficinaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        oficinaDTO = oficinaService.update(oficinaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, oficinaDTO.getId()))
            .body(oficinaDTO);
    }

    /**
     * {@code PATCH  /oficinas/:id} : Partial updates given fields of an existing
     * oficina, field will ignore if it is null
     *
     * @param id         the id of the oficinaDTO to save.
     * @param oficinaDTO the oficinaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated oficinaDTO,
     *         or with status {@code 400 (Bad Request)} if the oficinaDTO is not
     *         valid,
     *         or with status {@code 404 (Not Found)} if the oficinaDTO is not
     *         found,
     *         or with status {@code 500 (Internal Server Error)} if the oficinaDTO
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OficinaDTO> partialUpdateOficina(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody OficinaDTO oficinaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Oficina partially : {}, {}", id, oficinaDTO);
        if (oficinaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oficinaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oficinaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OficinaDTO> result = oficinaService.partialUpdate(oficinaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, oficinaDTO.getId())
        );
    }

    /**
     * {@code GET  /oficinas} : get all the oficinas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of oficinas in body.
     */
    @GetMapping("")
    public List<OficinaDTO> getAllOficinas() {
        LOG.debug("REST request to get all Oficinas");
        return oficinaService.findAll();
    }

    /**
     * {@code GET  /oficinas/:id} : get the "id" oficina.
     *
     * @param id the id of the oficinaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the oficinaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OficinaDTO> getOficina(@PathVariable("id") String id) {
        LOG.debug("REST request to get Oficina : {}", id);
        Optional<OficinaDTO> oficinaDTO = oficinaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(oficinaDTO);
    }

    /**
     * {@code DELETE  /oficinas/:id} : delete the "id" oficina.
     *
     * @param id the id of the oficinaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOficina(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Oficina : {}", id);
        oficinaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    @PostMapping("/oficinas")
    public ResponseEntity<OficinaDTO> createOficinaUser(@Valid @RequestBody OficinaDTO oficinaDTO) throws URISyntaxException {
        OficinaDTO result = oficinaService.createOficina(oficinaDTO);
        return ResponseEntity.created(new URI("/api/oficinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    @PutMapping("/oficinas/{id}")
    public ResponseEntity<OficinaDTO> updateOficinaUser(@Valid @RequestBody OficinaDTO oficinaDTO) {
        if (!oficinaRepository.existsById(oficinaDTO.getId())) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        oficinaDTO = oficinaService.updateOficina(oficinaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, oficinaDTO.getId()))
            .body(oficinaDTO);
    }

    @GetMapping("/oficinas/{id}")
    public ResponseEntity<OficinaDTO> getOficinaPqrsList(@PathVariable("id") String id) {
        LOG.debug("REST request to get Oficina : {}", id);
        Optional<OficinaDTO> oficinaDTO = oficinaService.getOficina(id);
        return ResponseUtil.wrapOrNotFound(oficinaDTO);
    }

    // findByResponsable_Login
    @GetMapping("/oficinasUser/{userId}")
    public ResponseEntity<OficinaDTO> getOficinaUser(@PathVariable("userId") String userId) {
        LOG.debug("REST request to get Oficina : {}", userId);
        Optional<OficinaDTO> oficinaDTO = oficinaService.getOficinaUser(userId);
        return ResponseUtil.wrapOrNotFound(oficinaDTO);
    }

    /**
     * {@code GET  /{userId}/pqrs-with-pagination} : Retrieves a paginated list of PQRS
     * associated with a specific user.
     *
     * @param userId The ID of the user for whom the PQRS records are being retrieved.
     * @param order Optional parameter to specify the order of the results.
     * @param page Optional parameter to specify the page number to retrieve (1-based index).
     * @param perPage Optional parameter to specify the number of records per page.
     * @param search Optional parameter to filter the results based on a search term.
     * @return A {@link ResponseEntity} containing a {@link Page} of PqrsDTO objects
     *         with status {@code 200 (OK)} if the request is successful.
     */
    @GetMapping("/{userId}/pqrs-with-pagination")
    public ResponseEntity<Page<PqrsDTO>> getPqrsByUserIdWithPagination(
        @PathVariable String userId,
        @RequestParam(required = false) String order,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer perPage,
        @RequestParam(required = false) String search
    ) {
        Pageable pageable = PageRequest.of(page != null ? page - 1 : 0, perPage != null ? perPage : 10);

        Page<PqrsDTO> result = oficinaService.findPqrsByUserIdWithPagination(userId, order, pageable, search);

        return ResponseEntity.ok(result);
    }
}
