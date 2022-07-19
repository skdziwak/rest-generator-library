package net.skdziwak.restgen.core;

import net.skdziwak.restgen.core.interfaces.IEntity;
import net.skdziwak.restgen.core.interfaces.RestfulRepository;

public interface RestfulService<ENTITY extends IEntity<ID>, ID> {
    RestfulRepository<ENTITY, ID> getRepository();
    RestfulServiceConfiguration configuration();

    default ENTITY getOne(ID id) {
        return getRepository().findById(id).orElseThrow(() -> configuration().resourceNotFoundException());
    }
}
