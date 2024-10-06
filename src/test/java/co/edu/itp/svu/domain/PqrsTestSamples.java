package co.edu.itp.svu.domain;

import java.util.UUID;

public class PqrsTestSamples {

    public static Pqrs getPqrsSample1() {
        return new Pqrs().id("id1").titulo("titulo1").estado("estado1");
    }

    public static Pqrs getPqrsSample2() {
        return new Pqrs().id("id2").titulo("titulo2").estado("estado2");
    }

    public static Pqrs getPqrsRandomSampleGenerator() {
        return new Pqrs().id(UUID.randomUUID().toString()).titulo(UUID.randomUUID().toString()).estado(UUID.randomUUID().toString());
    }
}
