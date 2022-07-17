package net.skdziwak.restgen;

import net.skdziwak.restgen.jsonschema.SchemaGenerator;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.NotBlank;
import java.util.List;

class RestGeneratorLibraryApplicationTests {

    @Test
    void contextLoads() {

        System.out.println();
    }

    static class A {
        List<B> bList;
    }

    static class B {
        @NotBlank
        String name;
    }

}
