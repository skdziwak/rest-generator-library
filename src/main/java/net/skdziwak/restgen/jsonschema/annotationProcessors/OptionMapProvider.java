package net.skdziwak.restgen.jsonschema.annotationProcessors;

import java.util.Map;

public abstract class OptionMapProvider {
    public abstract Map<?, ?> getOptions();
}
