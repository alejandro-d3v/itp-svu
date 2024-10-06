package co.edu.itp.svu.domain;

import java.util.UUID;

public class NotificacionTestSamples {

    public static Notificacion getNotificacionSample1() {
        return new Notificacion().id("id1").tipo("tipo1");
    }

    public static Notificacion getNotificacionSample2() {
        return new Notificacion().id("id2").tipo("tipo2");
    }

    public static Notificacion getNotificacionRandomSampleGenerator() {
        return new Notificacion().id(UUID.randomUUID().toString()).tipo(UUID.randomUUID().toString());
    }
}
