package net.skdziwak.restgen.core.search;

import java.util.List;

public class DefaultSearchSpecificationDTO {
    private Integer page;
    private Integer size;
    private List<SearchFilter> filters;
    private List<SearchSorter> sorters;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<SearchFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<SearchFilter> filters) {
        this.filters = filters;
    }

    public List<SearchSorter> getSorters() {
        return sorters;
    }

    public void setSorters(List<SearchSorter> sorters) {
        this.sorters = sorters;
    }
}
