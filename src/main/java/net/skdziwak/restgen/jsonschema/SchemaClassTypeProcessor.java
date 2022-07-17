package net.skdziwak.restgen.jsonschema;

import org.springframework.core.GenericTypeResolver;

public abstract class SchemaClassTypeProcessor<C> extends SchemaTypeProcessor {
    public Class<C> getType() {
        @SuppressWarnings("unchecked")
        Class<C> cClass = (Class<C>) GenericTypeResolver.resolveTypeArgument(getClass(), SchemaClassTypeProcessor.class);
        return cClass;
    }
}
