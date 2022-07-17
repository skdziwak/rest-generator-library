package net.skdziwak.restgen.jsonschema.annotationProcessors;

import net.skdziwak.restgen.jsonschema.SchemaAnnotationProcessor;
import net.skdziwak.restgen.jsonschema.annotations.SchemaOneOf;
import net.skdziwak.restgen.jsonschema.data.SchemaProperty;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectProvider;

import java.lang.reflect.Field;
import java.util.*;

public class SchemaOneOfAnnotationProcessor extends SchemaAnnotationProcessor<SchemaOneOf> {
    private final BeanFactory beanFactory;

    public SchemaOneOfAnnotationProcessor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setValues(Field field, SchemaOneOf annotation, SchemaProperty schemaProperty) {
        ObjectProvider<? extends OptionMapProvider> beanProvider = beanFactory.getBeanProvider(annotation.value());
        OptionMapProvider optionProvider = beanProvider.getObject();
        Map<?, ?> providedOptions = optionProvider.getOptions();
        List<Map<String, Object>> oneOf = new LinkedList<>();

        providedOptions.forEach((k, v) -> {
            if (k != null && v != null) {
                oneOf.add(Map.of(
                        "const", k,
                        "title", v
                ));
            }
        });

        schemaProperty.set("oneOf", oneOf);
    }
}
