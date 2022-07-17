package net.skdziwak.restgen.core.search;

public class SearchSorter {
    private String key;
    private SortDirection direction;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SortDirection getDirection() {
        return direction;
    }

    public void setDirection(SortDirection direction) {
        this.direction = direction;
    }
}
