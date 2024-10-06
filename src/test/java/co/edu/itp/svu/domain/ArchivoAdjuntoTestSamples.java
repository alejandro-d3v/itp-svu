package co.edu.itp.svu.domain;

import java.util.UUID;

public class ArchivoAdjuntoTestSamples {

    public static ArchivoAdjunto getArchivoAdjuntoSample1() {
        return new ArchivoAdjunto().id("id1").nombre("nombre1").tipo("tipo1").urlArchivo("urlArchivo1");
    }

    public static ArchivoAdjunto getArchivoAdjuntoSample2() {
        return new ArchivoAdjunto().id("id2").nombre("nombre2").tipo("tipo2").urlArchivo("urlArchivo2");
    }

    public static ArchivoAdjunto getArchivoAdjuntoRandomSampleGenerator() {
        return new ArchivoAdjunto()
            .id(UUID.randomUUID().toString())
            .nombre(UUID.randomUUID().toString())
            .tipo(UUID.randomUUID().toString())
            .urlArchivo(UUID.randomUUID().toString());
    }
}
