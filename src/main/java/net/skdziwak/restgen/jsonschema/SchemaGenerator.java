package net.skdziwak.restgen.jsonschema;

import net.skdziwak.restgen.jsonschema.annotationProcessors.*;
import net.skdziwak.restgen.jsonschema.annotations.SchemaIgnore;
import net.skdziwak.restgen.jsonschema.data.MapConverter;
import net.skdziwak.restgen.jsonschema.data.PropertyType;
import net.skdziwak.restgen.jsonschema.data.SchemaProperty;
import net.skdziwak.restgen.jsonschema.typeProcessors.*;
import org.springframework.beans.factory.BeanFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.Consumer;

public class SchemaGenerator {
    private final Map<Class<?>, SchemaTypeProcessor> typeProcessors = new HashMap<>();
    private final Map<Class<? extends Annotation>, SchemaAnnotationProcessor<?>> annotationProcessors = new HashMap<>();
    private FieldTranslator fieldTranslator = new FieldTranslator();
    public static SchemaGenerator getDefault(BeanFactory beanFactory) {
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        schemaGenerator.addTypeProcessor(Timestamp.class, new DateTimeProcessor());
        schemaGenerator.addTypeProcessor(Date.class, new DateProcessor());
        schemaGenerator.addTypeProcessor(java.sql.Date.class, new DateProcessor());
        schemaGenerator.addTypeProcessor(Long.class, new IntegerProcessor());
        schemaGenerator.addTypeProcessor(Integer.class, new IntegerProcessor());
        schemaGenerator.addTypeProcessor(BigInteger.class, new IntegerProcessor());
        schemaGenerator.addTypeProcessor(Float.class, new NumberProcessor());
        schemaGenerator.addTypeProcessor(Double.class, new NumberProcessor());
        schemaGenerator.addTypeProcessor(BigDecimal.class, new NumberProcessor());
        schemaGenerator.addTypeProcessor(String.class, new StringProcessor());

        schemaGenerator.addAnnotationProcessor(new NotNullAnnotationProcessor());
        schemaGenerator.addAnnotationProcessor(new NotEmptyAnnotationProcessor());
        schemaGenerator.addAnnotationProcessor(new NotBlankAnnotationProcessor());
        schemaGenerator.addAnnotationProcessor(new MinAnnotationProcessor());
        schemaGenerator.addAnnotationProcessor(new MaxAnnotationProcessor());
        schemaGenerator.addAnnotationProcessor(new PatternAnnotationProcessor());
        schemaGenerator.addAnnotationProcessor(new SizeAnnotationProcessor());
        schemaGenerator.addAnnotationProcessor(new SchemaEnumAnnotationProcessor(beanFactory));
        schemaGenerator.addAnnotationProcessor(new SchemaOneOfAnnotationProcessor(beanFactory));

        return schemaGenerator;
    }

    public void addTypeProcessor(Class<?> type, SchemaTypeProcessor typeProcessor) {
        typeProcessors.put(type, typeProcessor);
    }

    public void addTypeProcessor(SchemaClassTypeProcessor<?> typeProcessor) {
        typeProcessors.put(typeProcessor.getType(), typeProcessor);
    }

    public <T extends Annotation> void addAnnotationProcessor(SchemaAnnotationProcessor<T> annotationProcessor) {
        annotationProcessors.put(annotationProcessor.getAnnotationType(), annotationProcessor);
    }

    public Map<String, Object> generateMap(Class<?> clazz) {
        SchemaProperty schemaProperty = new SchemaProperty(null);
        processObject(clazz, schemaProperty);
        return schemaProperty.toMap();
    }

    public void setFieldTranslator(FieldTranslator fieldTranslator) {
        this.fieldTranslator = fieldTranslator;
    }

    private SchemaProperty createProperty(Field field, SchemaProperty parent) {
        if (field.isAnnotationPresent(SchemaIgnore.class)) {
            return null;
        }

        SchemaProperty schemaProperty = new SchemaProperty(parent);
        if (field.getType().isEnum()) {
            SchemaTypeProcessor schemaTypeProcessor = typeProcessors.get(String.class);
            schemaTypeProcessor.setValues(field.getType(), schemaProperty);
        } else if (Collection.class.isAssignableFrom(field.getType())) {
            processArray(field, schemaProperty);
        } else {
            SchemaTypeProcessor schemaTypeProcessor = typeProcessors.get(field.getType());
            if (schemaTypeProcessor != null) {
                schemaTypeProcessor.setValues(field.getType(), schemaProperty);
            } else {
                processObject(field.getType(), schemaProperty);
            }
        }

        for (Annotation annotation : field.getAnnotations()) {
            triggerAnnotationProcessor(field, schemaProperty, annotation.annotationType());
        }

        return schemaProperty;
    }

    private <T extends Annotation> void triggerAnnotationProcessor(Field field, SchemaProperty schemaProperty, Class<T> annotationClass) {
        @SuppressWarnings("unchecked")
        SchemaAnnotationProcessor<T> schemaAnnotationProcessor = (SchemaAnnotationProcessor<T>) annotationProcessors.get(annotationClass);
        if (schemaAnnotationProcessor != null) {
            T annotation = field.getAnnotation(schemaAnnotationProcessor.getAnnotationType());
            schemaAnnotationProcessor.setValues(field, annotation, schemaProperty);
        }
    }

    private void forEachField(Class<?> clazz, Consumer<Field> consumer) {
        if (clazz != null) {
            for (Field declaredField : clazz.getDeclaredFields()) {
                consumer.accept(declaredField);
            }
            forEachField(clazz.getSuperclass(), consumer);
        }
    }

    public void processObject(Class<?> type, SchemaProperty property) {
        Map<String, SchemaProperty> properties = new HashMap<>();

        forEachField(type, f -> {
            SchemaProperty p = createProperty(f, property);
            if (p != null) {
                p.set("title", fieldTranslator.translate(f));
                properties.put(f.getName(), p);
            }
        });

        property.set("properties", MapConverter.convert(properties));
        property.setType(PropertyType.OBJECT);
    }

    public void processArray(Field field, SchemaProperty property) {
        property.setType(PropertyType.ARRAY);
        Type genericType = field.getGenericType();
        ParameterizedType parameterizedType = (ParameterizedType) genericType;
        Type genericTypeArgument = parameterizedType.getActualTypeArguments()[0];
        try {
            Class<?> aClass = Class.forName(genericTypeArgument.getTypeName());

            SchemaProperty schemaProperty = new SchemaProperty(property);
            SchemaTypeProcessor schemaTypeProcessor = typeProcessors.get(aClass);
            if (schemaTypeProcessor != null) {
                schemaTypeProcessor.setValues(aClass, schemaProperty);
            } else {
                processObject(aClass, schemaProperty);
            }

            for (Annotation annotation : field.getAnnotations()) {
                triggerAnnotationProcessor(field, schemaProperty, annotation.annotationType());
            }
            property.set("items", schemaProperty.toMap());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
