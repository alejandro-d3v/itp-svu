package co.edu.itp.svu.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class NotificacionAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNotificacionAllPropertiesEquals(Notificacion expected, Notificacion actual) {
        assertNotificacionAutoGeneratedPropertiesEquals(expected, actual);
        assertNotificacionAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNotificacionAllUpdatablePropertiesEquals(Notificacion expected, Notificacion actual) {
        assertNotificacionUpdatableFieldsEquals(expected, actual);
        assertNotificacionUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNotificacionAutoGeneratedPropertiesEquals(Notificacion expected, Notificacion actual) {
        assertThat(expected)
            .as("Verify Notificacion auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNotificacionUpdatableFieldsEquals(Notificacion expected, Notificacion actual) {
        assertThat(expected)
            .as("Verify Notificacion relevant properties")
            .satisfies(e -> assertThat(e.getTipo()).as("check tipo").isEqualTo(actual.getTipo()))
            .satisfies(e -> assertThat(e.getFecha()).as("check fecha").isEqualTo(actual.getFecha()))
            .satisfies(e -> assertThat(e.getMensaje()).as("check mensaje").isEqualTo(actual.getMensaje()))
            .satisfies(e -> assertThat(e.getLeido()).as("check leido").isEqualTo(actual.getLeido()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNotificacionUpdatableRelationshipsEquals(Notificacion expected, Notificacion actual) {
        assertThat(expected)
            .as("Verify Notificacion relationships")
            .satisfies(e -> assertThat(e.getDestinatarios()).as("check destinatarios").isEqualTo(actual.getDestinatarios()));
    }
}