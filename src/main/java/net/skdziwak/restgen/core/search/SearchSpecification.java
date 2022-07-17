package net.skdziwak.restgen.core.search;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public abstract class SearchSpecification<ENTITY> implements Specification<ENTITY> {
    public abstract Pageable getPageable();
}
