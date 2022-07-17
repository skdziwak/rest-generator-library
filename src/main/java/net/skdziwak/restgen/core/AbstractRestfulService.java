package net.skdziwak.restgen.core;

import net.skdziwak.restgen.core.extensions.NotNullDtoMapping;
import net.skdziwak.restgen.core.interfaces.IEntity;
import net.skdziwak.restgen.core.interfaces.RestfulRepository;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;

public abstract class AbstractRestfulService<ENTITY extends IEntity<ID>, ID> implements RestfulService<ENTITY, ID> {
    private final RestfulServiceConfiguration configuration;
    private final RestfulRepository<ENTITY, ID> repository;
    private final Class<ENTITY> entityClass;
    private final DozerBeanMapper defaultMapper;
    private final DozerBeanMapper notNullMapper;

    public AbstractRestfulService(RestfulServiceConfiguration configuration, RestfulRepository<ENTITY, ID> repository, Class<ENTITY> entityClass) {
        this.configuration = configuration;
        this.repository = repository;
        this.entityClass = entityClass;
        this.defaultMapper = configuration.createMapper();
        this.notNullMapper = configuration.createMapper();

        if (this instanceof NotNullDtoMapping) {
            Class<?> notNullDtoClass = ((NotNullDtoMapping<?>) this).notNullDtoClass();
            notNullMapper.addMapping(new BeanMappingBuilder() {
                @Override
                protected void configure() {
                    mapping(notNullDtoClass, entityClass, TypeMappingOptions.mapNull(false));
                }
            });
        }
    }

    @Override
    public final <DTO> DTO mapEntityToDTO(ENTITY entity, Class<DTO> dto) {
        return defaultMapper.map(entity, dto);
    }

    @Override
    public final <DTO> ENTITY mapDtoToEntity(DTO dto) {
        return defaultMapper.map(dto, entityClass);
    }

    @Override
    public final <DTO> void mapDtoToEntity(DTO dto, ENTITY entity) {
        defaultMapper.map(dto, entity);
    }

    @Override
    public final <DTO> void mapDtoToEntityWithoutNulls(DTO dto, ENTITY entity) {
        notNullMapper.map(dto, entity);
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
