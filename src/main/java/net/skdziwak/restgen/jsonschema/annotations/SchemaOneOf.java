package net.skdziwak.restgen.jsonschema.annotations;

import net.skdziwak.restgen.jsonschema.annotationProcessors.OptionMapProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SchemaOneOf {
    Class<? extends OptionMapProvider> value();
}
