package net.skdziwak.restgen.core.extensions;

import net.skdziwak.restgen.core.RestfulService;
import net.skdziwak.restgen.core.interfaces.IEntity;
import org.springframework.core.GenericTypeResolver;

import java.util.Objects;

public interface ViewRestfulService<ENTITY extends IEntity<ID>, ID, DTO> extends RestfulService<ENTITY, ID> {
    default Class<DTO> viewDtoClass() {
        @SuppressWarnings("unchecked")
        Class<DTO> dtoClass = (Class<DTO>) Objects.requireNonNull(GenericTypeResolver.resolveTypeArguments(getClass(), ViewRestfulService.class))[2];
        return dtoClass;
    }

    default DTO mapView(ENTITY entity) {
        return mapEntityToDTO(entity, viewDtoClass());
    }
}
