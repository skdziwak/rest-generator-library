package net.skdziwak.restgen.core.extensions;

import net.skdziwak.restgen.core.interfaces.IEntity;
import org.springframework.core.GenericTypeResolver;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

public interface PatchRestfulService<ENTITY extends IEntity<ID>, ID, VIEW_DTO, PATCH_DTO> extends ViewRestfulService<ENTITY, ID, VIEW_DTO> {
    @Transactional
    default VIEW_DTO patch(ID id, PATCH_DTO dto) {
        ENTITY entity = getOne(id);
        beforePatch(dto, entity);
        mapPatch(dto, entity);
        getRepository().saveAndFlush(entity);
        afterPatch(dto, entity);
        return mapView(entity);
    }

    default Map<String, Object> patchSchema() {
        Class<?> dtoClass = Objects.requireNonNull(GenericTypeResolver.resolveTypeArguments(getClass(), PatchRestfulService.class))[3];
        return configuration().schemaGenerator().generateMap(dtoClass);
    }

    default void beforePatch(PATCH_DTO dto, ENTITY entity) {

    }

    void mapPatch(PATCH_DTO dto, ENTITY entity);

    default void afterPatch(PATCH_DTO dto, ENTITY entity) {

    }
}
