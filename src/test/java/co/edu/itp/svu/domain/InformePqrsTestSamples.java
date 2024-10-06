package co.edu.itp.svu.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class InformePqrsTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static InformePqrs getInformePqrsSample1() {
        return new InformePqrs().id("id1").totalPqrs(1).totalResueltas(1).totalPendientes(1);
    }

    public static InformePqrs getInformePqrsSample2() {
        return new InformePqrs().id("id2").totalPqrs(2).totalResueltas(2).totalPendientes(2);
    }

    public static InformePqrs getInformePqrsRandomSampleGenerator() {
        return new InformePqrs()
            .id(UUID.randomUUID().toString())
            .totalPqrs(intCount.incrementAndGet())
            .totalResueltas(intCount.incrementAndGet())
            .totalPendientes(intCount.incrementAndGet());
    }
}
