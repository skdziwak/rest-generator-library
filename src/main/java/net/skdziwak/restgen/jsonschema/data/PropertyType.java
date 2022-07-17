package net.skdziwak.restgen.jsonschema.data;

public enum PropertyType {
    STRING("string"),
    NUMBER("number"),
    INTEGER("integer"),
    OBJECT("object"),
    ARRAY("array"),
    BOOLEAN("boolean"),
    NULL("null");
    private final String value;

    PropertyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
