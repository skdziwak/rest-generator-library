package net.skdziwak.restgen.jsonschema.annotationProcessors;

import net.skdziwak.restgen.jsonschema.SchemaAnnotationProcessor;
import net.skdziwak.restgen.jsonschema.annotations.SchemaEnum;
import net.skdziwak.restgen.jsonschema.data.SchemaProperty;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectProvider;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

public class SchemaEnumAnnotationProcessor extends SchemaAnnotationProcessor<SchemaEnum> {
    private final BeanFactory beanFactory;

    public SchemaEnumAnnotationProcessor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setValues(Field field, SchemaEnum annotation, SchemaProperty schemaProperty) {
        ObjectProvider<? extends OptionProvider> beanProvider = beanFactory.getBeanProvider(annotation.value());
        OptionProvider optionProvider = beanProvider.getObject();
        schemaProperty.set("enum", Objects.requireNonNullElse(optionProvider.options(field), List.of()));
    }
}
