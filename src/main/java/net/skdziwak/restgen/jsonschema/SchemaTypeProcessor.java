package net.skdziwak.restgen.jsonschema;

import net.skdziwak.restgen.jsonschema.data.SchemaProperty;

public abstract class SchemaTypeProcessor {
    public abstract void setValues(Class<?> type, SchemaProperty property);
}
