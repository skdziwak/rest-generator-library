package net.skdziwak.restgen.jsonschema.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SchemaProperty implements MapConvertable {
    private final Map<String, Object> values = new HashMap<>();
    private PropertyType type;
    private final SchemaProperty parent;
    private final List<String> required = new LinkedList<>();

    public SchemaProperty(SchemaProperty parent) {
        this.parent = parent;
    }

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }

    public void set(String key, Object value) {
        values.put(key, value);
    }

    public SchemaProperty getParent() {
        return parent;
    }

    public void requires(String key) {
        this.required.add(key);
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type.getValue());
        if (!required.isEmpty()) {
            map.put("required", required);
        }
        map.putAll(MapConverter.convert(values));
        return map;
    }
}
