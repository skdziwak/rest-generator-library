package net.skdziwak.restgen.jsonschema.annotationProcessors;

import net.skdziwak.restgen.jsonschema.SchemaAnnotationProcessor;
import net.skdziwak.restgen.jsonschema.data.SchemaProperty;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;

public class NotNullAnnotationProcessor extends SchemaAnnotationProcessor<NotNull> {
    @Override
    public void setValues(Field field, NotNull annotation, SchemaProperty schemaProperty) {
        schemaProperty.getParent().requires(field.getName());
    }
}
