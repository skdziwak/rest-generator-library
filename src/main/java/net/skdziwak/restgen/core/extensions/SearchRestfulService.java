package net.skdziwak.restgen.core.extensions;

import net.skdziwak.restgen.core.RestfulService;
import net.skdziwak.restgen.core.interfaces.IEntity;
import net.skdziwak.restgen.core.search.DefaultSearchSpecification;
import net.skdziwak.restgen.core.search.DefaultSearchSpecificationDTO;
import net.skdziwak.restgen.core.search.SearchSpecification;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public interface SearchRestfulService<ENTITY extends IEntity<ID>, ID, DTO> extends RestfulService<ENTITY, ID> {
    default Class<DTO> searchDtoClass() {
        @SuppressWarnings("unchecked")
        Class<DTO> dtoClass = (Class<DTO>) Objects.requireNonNull(GenericTypeResolver.resolveTypeArguments(getClass(), SearchRestfulService.class))[2];
        return dtoClass;
    }

    DTO mapSearch(ENTITY entity);
    default Page<DTO> search(SearchSpecification<ENTITY> specification) {
        Pageable pageable = specification.getPageable();
        Page<ENTITY> page = getRepository().findAll(specification, pageable);
        return new PageImpl<>(
                page.getContent().stream().map(this::mapSearch).collect(Collectors.toList()),
                page.getPageable(), page.getTotalElements()
        );
    }

    default Page<DTO> search(DefaultSearchSpecificationDTO specification) {
        return search(new DefaultSearchSpecification<>(specification, configuration()));
    }

    default Map<String, Object> searchContentSchema() {
        return configuration().schemaGenerator().generateMap(searchDtoClass());
    }
}
