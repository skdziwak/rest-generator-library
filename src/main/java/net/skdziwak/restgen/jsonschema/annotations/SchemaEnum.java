package net.skdziwak.restgen.jsonschema.annotations;

import net.skdziwak.restgen.jsonschema.annotationProcessors.OptionProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SchemaEnum {
    Class<? extends OptionProvider> value();
}
