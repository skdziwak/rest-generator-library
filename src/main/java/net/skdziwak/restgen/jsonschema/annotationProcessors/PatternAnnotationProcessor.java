package net.skdziwak.restgen.jsonschema.annotationProcessors;

import net.skdziwak.restgen.jsonschema.SchemaAnnotationProcessor;
import net.skdziwak.restgen.jsonschema.data.SchemaProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import java.lang.reflect.Field;

public class PatternAnnotationProcessor extends SchemaAnnotationProcessor<Pattern> {
    @Override
    public void setValues(Field field, Pattern annotation, SchemaProperty schemaProperty) {
        schemaProperty.set("pattern", annotation.regexp());
    }
}
