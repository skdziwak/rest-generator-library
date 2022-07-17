package net.skdziwak.restgen.core.extensions;

import org.springframework.core.GenericTypeResolver;

import java.util.Objects;

public interface NotNullDtoMapping<DTO> {
    default Class<DTO> notNullDtoClass() {
        @SuppressWarnings("unchecked")
        Class<DTO> dtoClass = (Class<DTO>) Objects.requireNonNull(GenericTypeResolver.resolveTypeArguments(getClass(), NotNullDtoMapping.class))[0];
        return dtoClass;
    }
}
