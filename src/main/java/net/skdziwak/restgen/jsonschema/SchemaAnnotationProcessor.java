package net.skdziwak.restgen.jsonschema;

import net.skdziwak.restgen.jsonschema.data.SchemaProperty;
import org.springframework.core.GenericTypeResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public abstract class SchemaAnnotationProcessor<A extends Annotation> {
    public Class<A> getAnnotationType() {
        @SuppressWarnings("unchecked")
        Class<A> aClass = (Class<A>) GenericTypeResolver.resolveTypeArgument(getClass(), SchemaAnnotationProcessor.class);
        return aClass;
    }
    public abstract void setValues(Field field, A annotation, SchemaProperty schemaProperty);
}
