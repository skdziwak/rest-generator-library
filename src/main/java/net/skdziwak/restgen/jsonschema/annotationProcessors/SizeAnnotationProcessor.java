package net.skdziwak.restgen.jsonschema.annotationProcessors;

import net.skdziwak.restgen.jsonschema.SchemaAnnotationProcessor;
import net.skdziwak.restgen.jsonschema.data.SchemaProperty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.reflect.Field;

public class SizeAnnotationProcessor extends SchemaAnnotationProcessor<Size> {
    @Override
    public void setValues(Field field, Size annotation, SchemaProperty schemaProperty) {
        schemaProperty.set("minLength", annotation.min());
        schemaProperty.set("maxLength", annotation.max());
    }
}
