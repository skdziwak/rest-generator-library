package net.skdziwak.restgen.core.search;

import net.skdziwak.restgen.core.RestfulServiceConfiguration;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Locale;
import java.util.regex.Pattern;

public class FilterSpecification<ENTITY> implements Specification<ENTITY> {
    private final RestfulServiceConfiguration configuration;
    private final SearchFilter searchFilter;

    public FilterSpecification(RestfulServiceConfiguration configuration, SearchFilter searchFilter) {
        this.searchFilter = searchFilter;
        this.configuration = configuration;
    }

    @Override
    public Predicate toPredicate(Root<ENTITY> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return getPredicate(root, criteriaBuilder);
    }

    private Predicate getPredicate(Root<ENTITY> root, CriteriaBuilder criteriaBuilder) {
        Path<?> path = parsePath(root, searchFilter.getKey());

        switch (searchFilter.getType()) {
            case STRING:
                switch (searchFilter.getOperation()) {
                    case ILIKE:
                        return criteriaBuilder.like(criteriaBuilder.upper(path.as(String.class)), String.valueOf(searchFilter.getValue()).toUpperCase(Locale.ROOT));
                    case NOT_ILIKE:
                        return criteriaBuilder.notLike(criteriaBuilder.upper(path.as(String.class)), String.valueOf(searchFilter.getValue()).toUpperCase(Locale.ROOT));
                    case LIKE:
                        return criteriaBuilder.like(path.as(String.class), String.valueOf(searchFilter.getValue()));
                    case NOT_LIKE:
                        return criteriaBuilder.notLike(path.as(String.class), String.valueOf(searchFilter.getValue()));
                }
                break;
            case INTEGER:
            case DATE:
            case TIMESTAMP:
            case NUMERIC:
                switch (searchFilter.getOperation()) {
                    case GREATER:
                        return greaterThan(path, searchFilter.getValue(), criteriaBuilder);
                    case LOWER:
                        return lessThan(path, searchFilter.getValue(), criteriaBuilder);
                    case GREATER_EQUAL:
                        return greaterEqual(path, searchFilter.getValue(), criteriaBuilder);
                    case LOWER_EQUAL:
                        return lessEqual(path, searchFilter.getValue(), criteriaBuilder);
                }
                break;
        }

        switch (searchFilter.getOperation()) {
            case EQUAL:
                return criteriaBuilder.equal(path, searchFilter.getValue());
            case NOT_EQUAL:
                return criteriaBuilder.notEqual(path, searchFilter.getValue());
            case IS_NULL:
                return criteriaBuilder.isNull(path);
            case NOT_NULL:
                return criteriaBuilder.isNotNull(path);
        }

        throw configuration.badSearchFilterException();
    }

    private <C extends Comparable<? super C>> Predicate greaterEqual(Path<?> path, Object value, CriteriaBuilder criteriaBuilder) {
        if (value instanceof Comparable) {
            C comparable = (C) value;
            Class<C> cClass = (Class<C>) value.getClass();
            Expression<C> expression = path.as(cClass);
            return criteriaBuilder.greaterThanOrEqualTo(expression, comparable);
        }
        throw configuration.badSearchFilterException();
    }

    private <C extends Comparable<? super C>> Predicate lessEqual(Path<?> path, Object value, CriteriaBuilder criteriaBuilder) {
        if (value instanceof Comparable) {
            C comparable = (C) value;
            Class<C> cClass = (Class<C>) value.getClass();
            Expression<C> expression = path.as(cClass);
            return criteriaBuilder.lessThanOrEqualTo(expression, comparable);
        }
        throw configuration.badSearchFilterException();
    }

    private <C extends Comparable<? super C>> Predicate lessThan(Path<?> path, Object value, CriteriaBuilder criteriaBuilder) {
        if (value instanceof Comparable) {
            C comparable = (C) value;
            Class<C> cClass = (Class<C>) value.getClass();
            Expression<C> expression = path.as(cClass);
            return criteriaBuilder.lessThan(expression, comparable);
        }
        throw configuration.badSearchFilterException();
    }

    private <C extends Comparable<? super C>> Predicate greaterThan(Path<?> path, Object value, CriteriaBuilder criteriaBuilder) {
        if (value instanceof Comparable) {
            C comparable = (C) value;
            Class<C> cClass = (Class<C>) value.getClass();
            Expression<C> expression = path.as(cClass);
            return criteriaBuilder.greaterThan(expression, comparable);
        }
        throw configuration.badSearchFilterException();
    }

    private Path<?> parsePath(Root<?> root, String path) {
        if (path.contains(".")) {
            From<?, ?> from = root;

            String[] keys = path.split(Pattern.quote("."));
            for (int i = 0; i < keys.length - 1; i++) {
                from = from.join(keys[i]);
            }

            return from.get(keys[keys.length - 1]);
        } else {
            return root.get(path);
        }
    }
}
