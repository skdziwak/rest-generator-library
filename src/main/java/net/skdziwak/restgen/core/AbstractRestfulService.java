package net.skdziwak.restgen.core;

import net.skdziwak.restgen.core.interfaces.IEntity;
import net.skdziwak.restgen.core.interfaces.RestfulRepository;

public abstract class AbstractRestfulService<ENTITY extends IEntity<ID>, ID> implements RestfulService<ENTITY, ID> {
    private final RestfulServiceConfiguration configuration;
    private final RestfulRepository<ENTITY, ID> repository;
    public AbstractRestfulService(RestfulServiceConfiguration configuration, RestfulRepository<ENTITY, ID> repository) {
        this.configuration = configuration;
        this.repository = repository;
    }

    @Override
    public RestfulRepository<ENTITY, ID> getRepository() {
        return repository;
    }

    @Override
    public RestfulServiceConfiguration configuration() {
        return configuration;
    }
}
