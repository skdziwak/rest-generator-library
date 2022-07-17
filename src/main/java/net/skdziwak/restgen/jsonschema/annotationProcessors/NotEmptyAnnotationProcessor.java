package net.skdziwak.restgen.jsonschema.annotationProcessors;

import net.skdziwak.restgen.jsonschema.SchemaAnnotationProcessor;
import net.skdziwak.restgen.jsonschema.data.SchemaProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;

public class NotEmptyAnnotationProcessor extends SchemaAnnotationProcessor<NotEmpty> {
    @Override
    public void setValues(Field field, NotEmpty annotation, SchemaProperty schemaProperty) {
        schemaProperty.getParent().requires(field.getName());
    }
}
