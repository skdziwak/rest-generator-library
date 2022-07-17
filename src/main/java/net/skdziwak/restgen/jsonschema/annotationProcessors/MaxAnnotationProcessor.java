package net.skdziwak.restgen.jsonschema.annotationProcessors;

import net.skdziwak.restgen.jsonschema.SchemaAnnotationProcessor;
import net.skdziwak.restgen.jsonschema.data.SchemaProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.lang.reflect.Field;

public class MaxAnnotationProcessor extends SchemaAnnotationProcessor<Max> {
    @Override
    public void setValues(Field field, Max annotation, SchemaProperty schemaProperty) {
        schemaProperty.set("maximum", annotation.value());
    }
}
