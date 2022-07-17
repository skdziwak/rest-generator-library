package net.skdziwak.restgen.jsonschema;

import java.lang.reflect.Field;

public class FieldTranslator {
    public String translate(Field field) {
        return field.getName();
    }
}
