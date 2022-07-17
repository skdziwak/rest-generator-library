package net.skdziwak.restgen.core.search;

import net.skdziwak.restgen.core.RestfulServiceConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.*;
import java.util.Objects;

public class DefaultSearchSpecification<ENTITY> extends SearchSpecification<ENTITY> {
    private final DefaultSearchSpecificationDTO dto;
    private final RestfulServiceConfiguration configuration;
    public DefaultSearchSpecification(DefaultSearchSpecificationDTO dto, RestfulServiceConfiguration configuration) {
        this.dto = dto;
        this.configuration = configuration;
    }

    @Override
    public Predicate toPredicate(Root<ENTITY> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (dto.getFilters() == null) {
            return criteriaBuilder.conjunction();
        }
        return criteriaBuilder.and(
                dto.getFilters().stream()
                        .map(this::createFilterSpecification)
                        .map(f -> f.toPredicate(root, query, criteriaBuilder)
                        ).toArray(Predicate[]::new));
    }

    private FilterSpecification<ENTITY> createFilterSpecification(SearchFilter searchFilter) {
        return new FilterSpecification<>(configuration, searchFilter);
    }

    @Override
    public Pageable getPageable() {
        PageRequest pageRequest = PageRequest.of(
                Objects.requireNonNullElse(dto.getPage(), 0),
                Math.min(Objects.requireNonNullElse(dto.getSize(), 50), configuration.maxPageSize()));

        if (dto.getSorters() != null) {
            for (SearchSorter sorter : dto.getSorters()) {
                if (sorter.getKey() == null) continue;
                Sort.Direction direction = sorter.getDirection() == SortDirection.ASCENDING ? Sort.Direction.ASC : Sort.Direction.DESC;
                Sort sort = Sort.by(direction, sorter.getKey());
                pageRequest.withSort(sort);
            }
        }

        return pageRequest;
    }
}
