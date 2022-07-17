package net.skdziwak.restgen.core.extensions;

import net.skdziwak.restgen.core.RestfulService;
import net.skdziwak.restgen.core.interfaces.IEntity;

public interface DeleteRestfulService<ENTITY extends IEntity<ID>, ID> extends RestfulService<ENTITY, ID> {

    default void delete(ID id) {
        ENTITY entity = getOne(id);
        getRepository().delete(entity);
    }

}
