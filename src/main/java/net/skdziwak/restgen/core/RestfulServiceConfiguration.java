package net.skdziwak.restgen.core;

import net.skdziwak.restgen.jsonschema.SchemaGenerator;
import org.dozer.DozerBeanMapper;

public interface RestfulServiceConfiguration {
    DozerBeanMapper createMapper();
    SchemaGenerator schemaGenerator();
    default RuntimeException resourceNotFoundException() {
        return new RuntimeException("Resource not found");
    }

    default RuntimeException badSearchFilterException() {
        return new RuntimeException("Bad filter");
    }

    default Integer maxPageSize() {
        return 100;
    }
}
