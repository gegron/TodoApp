package fr.xebia.hello.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.common.base.Throwables;

public class JsonUtils {
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    static {
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String toJson(Object object) {
        String result = null;

        try {
            result = getJsonWriter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            Throwables.propagate(e);
        }

        return result;
    }

    public static ObjectReader getJsonReader(Class type) {
        return jsonMapper.reader(type);
    }

    public static ObjectReader getJsonReader(CollectionType collectionType) {
        return jsonMapper.reader(collectionType);
    }

    public static ObjectWriter getJsonWriter() {
        return jsonMapper.writer();
    }

    public static ObjectMapper getJsonMapper() {
        return jsonMapper;
    }
}
