package net.skdziwak.restgen.utils;

import net.skdziwak.restgen.core.interfaces.IEntity;
import org.dozer.CustomConverter;
import org.dozer.MappingException;
import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public abstract class EntityConverter<ID, ENTITY extends IEntity<ID>> implements CustomConverter {
    private final Class<ID> idClass;
    private final Class<ENTITY> entityClass;

    public EntityConverter() {
        Class<?>[] classes = Objects.requireNonNull(GenericTypeResolver.resolveTypeArguments(getClass(), EntityConverter.class));
        idClass = (Class<ID>) classes[0];
        entityClass = (Class<ENTITY>) classes[1];
    }

    @Override
    public Object convert(Object dest, Object source, Class<?> destClass, Class<?> sourceClass) {
        if (source == null) {
            return null;
        }
        if (idClass.isInstance(source)) {
            try {
                ENTITY entity = (ENTITY) destClass.getConstructor().newInstance();
                entity.setId((ID) source);
                return entity;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        } else if (entityClass.isInstance(source)) {
            return ((ENTITY) source).getId();
        } else {
            throw new MappingException("Invalid Entity-ID mapping.");
        }
    }
}
