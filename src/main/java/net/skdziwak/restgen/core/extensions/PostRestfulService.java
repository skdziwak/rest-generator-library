package net.skdziwak.restgen.core.extensions;

import net.skdziwak.restgen.core.interfaces.IEntity;
import org.springframework.core.GenericTypeResolver;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

public interface PostRestfulService<ENTITY extends IEntity<ID>, ID, VIEW_DTO, CREATE_DTO> extends ViewRestfulService<ENTITY, ID, VIEW_DTO> {
    @Transactional
    default VIEW_DTO post(CREATE_DTO dto) {
        ENTITY entity = mapPost(dto);
        beforePost(dto);
        getRepository().saveAndFlush(entity);
        afterPost(dto, entity);
        return mapView(entity);
    }

    default Map<String, Object> postSchema() {
        Class<?> dtoClass = Objects.requireNonNull(GenericTypeResolver.resolveTypeArguments(getClass(), PostRestfulService.class))[3];
        return configuration().schemaGenerator().generateMap(dtoClass);
    }


    default void beforePost(CREATE_DTO dto) {

    }

    default void afterPost(CREATE_DTO dto, ENTITY entity) {

    }

    ENTITY mapPost(CREATE_DTO dto);
}
