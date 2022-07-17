package net.skdziwak.restgen.jsonschema.data;

import java.util.*;

public class MapConverter {
    public static Map<String, Object> convert(Map<String, Object> map) {
        Map<String, Object> resultMap = new HashMap<>();
        map.forEach((k, v) -> {
            resultMap.put(k, convert(v));
        });
        return resultMap;
    }

    public static List<?> convert(Collection<?> collection) {
        List<Object> resultList = new LinkedList<>();
        for (Object o : collection) {
            resultList.add(convert(o));
        }
        return resultList;
    }
    public static Object convert(Object object) {
        if (object instanceof MapConvertable) {
            return ((MapConvertable) object).toMap();
        } else if (object instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) object;
            Map<String, Object> resultMap = new HashMap<>();
            map.forEach((k, v) -> {
                resultMap.put(String.valueOf(k), convert(v));
            });
            return resultMap;
        } else if (object instanceof Collection) {
            List<Object> resultList = new LinkedList<>();
            for (Object o : ((Collection<?>) object)) {
                resultList.add(convert(o));
            }
            return resultList;
        } else {
            return object;
        }
    }
}
