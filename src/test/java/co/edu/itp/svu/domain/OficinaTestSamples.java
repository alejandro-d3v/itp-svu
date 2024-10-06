package co.edu.itp.svu.domain;

import java.util.UUID;

public class OficinaTestSamples {

    public static Oficina getOficinaSample1() {
        return new Oficina().id("id1").nombre("nombre1").descripcion("descripcion1").nivel("nivel1").oficinaSuperior("oficinaSuperior1");
    }

    public static Oficina getOficinaSample2() {
        return new Oficina().id("id2").nombre("nombre2").descripcion("descripcion2").nivel("nivel2").oficinaSuperior("oficinaSuperior2");
    }

    public static Oficina getOficinaRandomSampleGenerator() {
        return new Oficina()
            .id(UUID.randomUUID().toString())
            .nombre(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString())
            .nivel(UUID.randomUUID().toString())
            .oficinaSuperior(UUID.randomUUID().toString());
    }
}
