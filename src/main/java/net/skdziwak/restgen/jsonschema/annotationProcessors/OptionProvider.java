package net.skdziwak.restgen.jsonschema.annotationProcessors;

import java.lang.reflect.Field;
import java.util.List;

public abstract class OptionProvider {
    public abstract List<Object> options(Field field);
}
