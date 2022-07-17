package net.skdziwak.restgen.jsonschema.annotationProcessors;

import net.skdziwak.restgen.jsonschema.SchemaAnnotationProcessor;
import net.skdziwak.restgen.jsonschema.data.SchemaProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.Field;

public class MinAnnotationProcessor extends SchemaAnnotationProcessor<Min> {
    @Override
    public void setValues(Field field, Min annotation, SchemaProperty schemaProperty) {
        schemaProperty.set("minimum", annotation.value());
    }
}
