package net.skdziwak.restgen.jsonschema.typeProcessors;

import net.skdziwak.restgen.jsonschema.SchemaTypeProcessor;
import net.skdziwak.restgen.jsonschema.data.PropertyType;
import net.skdziwak.restgen.jsonschema.data.SchemaProperty;

import java.lang.reflect.Field;

public class DateTimeProcessor extends SchemaTypeProcessor {
    @Override
    public void setValues(Class<?> type, SchemaProperty property) {
        property.setType(PropertyType.STRING);
        property.set("format", "date-time");
    }
}
