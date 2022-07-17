package net.skdziwak.restgen.codegen.generators;

import net.skdziwak.restgen.core.search.DefaultSearchSpecificationDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerGeneratorTest {

    @Test
    void generateController() {
        ControllerGenerator controllerGenerator = new ControllerGenerator();
        ControllerGenerator.ControllerSpecification controller = new ControllerGenerator.ControllerSpecification();

        ControllerGenerator.DtoSchema dtoSchema = new ControllerGenerator.DtoSchema();
        dtoSchema.setPost("PostDTO");
        dtoSchema.setView("ViewDTO");
        dtoSchema.setGet("GetDTO");
        dtoSchema.setSearch("SearchDTO");
        controller.setDto(dtoSchema);

        controller.setClassName("TestController");
        controller.setPackageName("net.skdziwak.restgenapp");
        controller.setIdClass("Long");
        controller.setServiceClass("net.skdziwak.restgenapp.TestService");
        controller.setSearchSpecificationClass(DefaultSearchSpecificationDTO.class.getName());
        controller.setEndpointsSchema(true);
        controller.setPath("/api/Test");
        controller.setEntityName("Test");

        ControllerGenerator.Endpoints endpoints = new ControllerGenerator.Endpoints();
        endpoints.setPost(true);
        endpoints.setGet(true);
        endpoints.setSearch(true);
        controller.setEndpoints(endpoints);

        System.out.println(controllerGenerator.generateController(controller));
    }
}