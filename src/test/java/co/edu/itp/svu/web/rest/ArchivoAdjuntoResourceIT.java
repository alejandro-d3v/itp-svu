package co.edu.itp.svu.web.rest;

import static co.edu.itp.svu.domain.ArchivoAdjuntoAsserts.*;
import static co.edu.itp.svu.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.itp.svu.IntegrationTest;
import co.edu.itp.svu.domain.ArchivoAdjunto;
import co.edu.itp.svu.repository.ArchivoAdjuntoRepository;
import co.edu.itp.svu.service.dto.ArchivoAdjuntoDTO;
import co.edu.itp.svu.service.mapper.ArchivoAdjuntoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ArchivoAdjuntoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArchivoAdjuntoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_URL_ARCHIVO = "AAAAAAAAAA";
    private static final String UPDATED_URL_ARCHIVO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_SUBIDA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_SUBIDA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/archivo-adjuntos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ArchivoAdjuntoRepository archivoAdjuntoRepository;

    @Autowired
    private ArchivoAdjuntoMapper archivoAdjuntoMapper;

    @Autowired
    private MockMvc restArchivoAdjuntoMockMvc;

    private ArchivoAdjunto archivoAdjunto;

    private ArchivoAdjunto insertedArchivoAdjunto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArchivoAdjunto createEntity() {
        return new ArchivoAdjunto()
            .nombre(DEFAULT_NOMBRE)
            .tipo(DEFAULT_TIPO)
            .urlArchivo(DEFAULT_URL_ARCHIVO)
            .fechaSubida(DEFAULT_FECHA_SUBIDA);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArchivoAdjunto createUpdatedEntity() {
        return new ArchivoAdjunto()
            .nombre(UPDATED_NOMBRE)
            .tipo(UPDATED_TIPO)
            .urlArchivo(UPDATED_URL_ARCHIVO)
            .fechaSubida(UPDATED_FECHA_SUBIDA);
    }

    @BeforeEach
    public void initTest() {
        archivoAdjunto = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedArchivoAdjunto != null) {
            archivoAdjuntoRepository.delete(insertedArchivoAdjunto);
            insertedArchivoAdjunto = null;
        }
    }

    @Test
    void createArchivoAdjunto() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ArchivoAdjunto
        ArchivoAdjuntoDTO archivoAdjuntoDTO = archivoAdjuntoMapper.toDto(archivoAdjunto);
        var returnedArchivoAdjuntoDTO = om.readValue(
            restArchivoAdjuntoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(archivoAdjuntoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ArchivoAdjuntoDTO.class
        );

        // Validate the ArchivoAdjunto in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedArchivoAdjunto = archivoAdjuntoMapper.toEntity(returnedArchivoAdjuntoDTO);
        assertArchivoAdjuntoUpdatableFieldsEquals(returnedArchivoAdjunto, getPersistedArchivoAdjunto(returnedArchivoAdjunto));

        insertedArchivoAdjunto = returnedArchivoAdjunto;
    }

    @Test
    void createArchivoAdjuntoWithExistingId() throws Exception {
        // Create the ArchivoAdjunto with an existing ID
        archivoAdjunto.setId("existing_id");
        ArchivoAdjuntoDTO archivoAdjuntoDTO = archivoAdjuntoMapper.toDto(archivoAdjunto);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArchivoAdjuntoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(archivoAdjuntoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArchivoAdjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        archivoAdjunto.setNombre(null);

        // Create the ArchivoAdjunto, which fails.
        ArchivoAdjuntoDTO archivoAdjuntoDTO = archivoAdjuntoMapper.toDto(archivoAdjunto);

        restArchivoAdjuntoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(archivoAdjuntoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkTipoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        archivoAdjunto.setTipo(null);

        // Create the ArchivoAdjunto, which fails.
        ArchivoAdjuntoDTO archivoAdjuntoDTO = archivoAdjuntoMapper.toDto(archivoAdjunto);

        restArchivoAdjuntoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(archivoAdjuntoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkFechaSubidaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        archivoAdjunto.setFechaSubida(null);

        // Create the ArchivoAdjunto, which fails.
        ArchivoAdjuntoDTO archivoAdjuntoDTO = archivoAdjuntoMapper.toDto(archivoAdjunto);

        restArchivoAdjuntoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(archivoAdjuntoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllArchivoAdjuntos() throws Exception {
        // Initialize the database
        insertedArchivoAdjunto = archivoAdjuntoRepository.save(archivoAdjunto);

        // Get all the archivoAdjuntoList
        restArchivoAdjuntoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(archivoAdjunto.getId())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].urlArchivo").value(hasItem(DEFAULT_URL_ARCHIVO)))
            .andExpect(jsonPath("$.[*].fechaSubida").value(hasItem(DEFAULT_FECHA_SUBIDA.toString())));
    }

    @Test
    void getArchivoAdjunto() throws Exception {
        // Initialize the database
        insertedArchivoAdjunto = archivoAdjuntoRepository.save(archivoAdjunto);

        // Get the archivoAdjunto
        restArchivoAdjuntoMockMvc
            .perform(get(ENTITY_API_URL_ID, archivoAdjunto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(archivoAdjunto.getId()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.urlArchivo").value(DEFAULT_URL_ARCHIVO))
            .andExpect(jsonPath("$.fechaSubida").value(DEFAULT_FECHA_SUBIDA.toString()));
    }

    @Test
    void getNonExistingArchivoAdjunto() throws Exception {
        // Get the archivoAdjunto
        restArchivoAdjuntoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingArchivoAdjunto() throws Exception {
        // Initialize the database
        insertedArchivoAdjunto = archivoAdjuntoRepository.save(archivoAdjunto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the archivoAdjunto
        ArchivoAdjunto updatedArchivoAdjunto = archivoAdjuntoRepository.findById(archivoAdjunto.getId()).orElseThrow();
        updatedArchivoAdjunto.nombre(UPDATED_NOMBRE).tipo(UPDATED_TIPO).urlArchivo(UPDATED_URL_ARCHIVO).fechaSubida(UPDATED_FECHA_SUBIDA);
        ArchivoAdjuntoDTO archivoAdjuntoDTO = archivoAdjuntoMapper.toDto(updatedArchivoAdjunto);

        restArchivoAdjuntoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, archivoAdjuntoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(archivoAdjuntoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ArchivoAdjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedArchivoAdjuntoToMatchAllProperties(updatedArchivoAdjunto);
    }

    @Test
    void putNonExistingArchivoAdjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archivoAdjunto.setId(UUID.randomUUID().toString());

        // Create the ArchivoAdjunto
        ArchivoAdjuntoDTO archivoAdjuntoDTO = archivoAdjuntoMapper.toDto(archivoAdjunto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArchivoAdjuntoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, archivoAdjuntoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(archivoAdjuntoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArchivoAdjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchArchivoAdjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archivoAdjunto.setId(UUID.randomUUID().toString());

        // Create the ArchivoAdjunto
        ArchivoAdjuntoDTO archivoAdjuntoDTO = archivoAdjuntoMapper.toDto(archivoAdjunto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArchivoAdjuntoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(archivoAdjuntoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArchivoAdjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamArchivoAdjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archivoAdjunto.setId(UUID.randomUUID().toString());

        // Create the ArchivoAdjunto
        ArchivoAdjuntoDTO archivoAdjuntoDTO = archivoAdjuntoMapper.toDto(archivoAdjunto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArchivoAdjuntoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(archivoAdjuntoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArchivoAdjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateArchivoAdjuntoWithPatch() throws Exception {
        // Initialize the database
        insertedArchivoAdjunto = archivoAdjuntoRepository.save(archivoAdjunto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the archivoAdjunto using partial update
        ArchivoAdjunto partialUpdatedArchivoAdjunto = new ArchivoAdjunto();
        partialUpdatedArchivoAdjunto.setId(archivoAdjunto.getId());

        partialUpdatedArchivoAdjunto.nombre(UPDATED_NOMBRE).tipo(UPDATED_TIPO);

        restArchivoAdjuntoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArchivoAdjunto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArchivoAdjunto))
            )
            .andExpect(status().isOk());

        // Validate the ArchivoAdjunto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArchivoAdjuntoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedArchivoAdjunto, archivoAdjunto),
            getPersistedArchivoAdjunto(archivoAdjunto)
        );
    }

    @Test
    void fullUpdateArchivoAdjuntoWithPatch() throws Exception {
        // Initialize the database
        insertedArchivoAdjunto = archivoAdjuntoRepository.save(archivoAdjunto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the archivoAdjunto using partial update
        ArchivoAdjunto partialUpdatedArchivoAdjunto = new ArchivoAdjunto();
        partialUpdatedArchivoAdjunto.setId(archivoAdjunto.getId());

        partialUpdatedArchivoAdjunto
            .nombre(UPDATED_NOMBRE)
            .tipo(UPDATED_TIPO)
            .urlArchivo(UPDATED_URL_ARCHIVO)
            .fechaSubida(UPDATED_FECHA_SUBIDA);

        restArchivoAdjuntoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArchivoAdjunto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArchivoAdjunto))
            )
            .andExpect(status().isOk());

        // Validate the ArchivoAdjunto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArchivoAdjuntoUpdatableFieldsEquals(partialUpdatedArchivoAdjunto, getPersistedArchivoAdjunto(partialUpdatedArchivoAdjunto));
    }

    @Test
    void patchNonExistingArchivoAdjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archivoAdjunto.setId(UUID.randomUUID().toString());

        // Create the ArchivoAdjunto
        ArchivoAdjuntoDTO archivoAdjuntoDTO = archivoAdjuntoMapper.toDto(archivoAdjunto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArchivoAdjuntoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, archivoAdjuntoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(archivoAdjuntoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArchivoAdjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchArchivoAdjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archivoAdjunto.setId(UUID.randomUUID().toString());

        // Create the ArchivoAdjunto
        ArchivoAdjuntoDTO archivoAdjuntoDTO = archivoAdjuntoMapper.toDto(archivoAdjunto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArchivoAdjuntoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(archivoAdjuntoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArchivoAdjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamArchivoAdjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        archivoAdjunto.setId(UUID.randomUUID().toString());

        // Create the ArchivoAdjunto
        ArchivoAdjuntoDTO archivoAdjuntoDTO = archivoAdjuntoMapper.toDto(archivoAdjunto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArchivoAdjuntoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(archivoAdjuntoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArchivoAdjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteArchivoAdjunto() throws Exception {
        // Initialize the database
        insertedArchivoAdjunto = archivoAdjuntoRepository.save(archivoAdjunto);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the archivoAdjunto
        restArchivoAdjuntoMockMvc
            .perform(delete(ENTITY_API_URL_ID, archivoAdjunto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return archivoAdjuntoRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected ArchivoAdjunto getPersistedArchivoAdjunto(ArchivoAdjunto archivoAdjunto) {
        return archivoAdjuntoRepository.findById(archivoAdjunto.getId()).orElseThrow();
    }

    protected void assertPersistedArchivoAdjuntoToMatchAllProperties(ArchivoAdjunto expectedArchivoAdjunto) {
        assertArchivoAdjuntoAllPropertiesEquals(expectedArchivoAdjunto, getPersistedArchivoAdjunto(expectedArchivoAdjunto));
    }

    protected void assertPersistedArchivoAdjuntoToMatchUpdatableProperties(ArchivoAdjunto expectedArchivoAdjunto) {
        assertArchivoAdjuntoAllUpdatablePropertiesEquals(expectedArchivoAdjunto, getPersistedArchivoAdjunto(expectedArchivoAdjunto));
    }
}
