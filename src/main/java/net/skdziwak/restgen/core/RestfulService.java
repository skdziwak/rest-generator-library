package net.skdziwak.restgen.core;

import net.skdziwak.restgen.core.interfaces.IEntity;
import net.skdziwak.restgen.core.interfaces.RestfulRepository;

public interface RestfulService<ENTITY extends IEntity<ID>, ID> {
    <DTO> DTO mapEntityToDTO(ENTITY entity, Class<DTO> dto);
    <DTO> ENTITY mapDtoToEntity(DTO dto);
    <DTO> void mapDtoToEntity(DTO dto, ENTITY entity);
    <DTO> void mapDtoToEntityWithoutNulls(DTO dto, ENTITY entity);
    RestfulRepository<ENTITY, ID> getRepository();
    RestfulServiceConfiguration configuration();

    default ENTITY getOne(ID id) {
        return getRepository().findById(id).orElseThrow(() -> configuration().resourceNotFoundException());
    }
}
