package net.skdziwak.restgen.core.extensions;

import net.skdziwak.restgen.core.interfaces.IEntity;
import org.springframework.core.GenericTypeResolver;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

public interface PutRestfulService<ENTITY extends IEntity<ID>, ID, VIEW_DTO, PUT_DTO> extends ViewRestfulService<ENTITY, ID, VIEW_DTO> {
    @Transactional
    default VIEW_DTO put(ID id, PUT_DTO dto) {
        ENTITY entity = getOne(id);
        beforePut(dto, entity);
        mapPut(dto, entity);
        getRepository().saveAndFlush(entity);
        afterPut(dto, entity);
        return mapView(entity);
    }

    default Map<String, Object> putSchema() {
        Class<?> dtoClass = Objects.requireNonNull(GenericTypeResolver.resolveTypeArguments(getClass(), PutRestfulService.class))[3];
        return configuration().schemaGenerator().generateMap(dtoClass);
    }

    default void beforePut(PUT_DTO dto, ENTITY entity) {

    }

    default void mapPut(PUT_DTO dto, ENTITY entity) {
        mapDtoToEntity(dto, entity);
    }

    default void afterPut(PUT_DTO dto, ENTITY entity) {

    }
}
