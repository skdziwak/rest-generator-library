package net.skdziwak.restgen.core.extensions;

import net.skdziwak.restgen.core.RestfulService;
import net.skdziwak.restgen.core.interfaces.IEntity;
import org.springframework.core.GenericTypeResolver;
import java.util.Objects;

public interface GetRestfulService<ENTITY extends IEntity<ID>, ID, DTO> extends RestfulService<ENTITY, ID> {
    default Class<DTO> getDtoClass() {
        @SuppressWarnings("unchecked")
        Class<DTO> dtoClass = (Class<DTO>) Objects.requireNonNull(GenericTypeResolver.resolveTypeArguments(getClass(), GetRestfulService.class))[2];
        return dtoClass;
    }

    default DTO get(ID id) {
        return mapGet(getOne(id));
    }

    DTO mapGet(ENTITY entity);
}
