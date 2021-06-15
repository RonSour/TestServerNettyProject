package org.nobody.testsnp;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONMapperFactory {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();

        // SerializationFeature for changing how JSON is written
        // to enable standard indentation ("pretty-printing"):
        /*
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // to allow serialization of "empty" POJOs (no properties to serialize)
        // (without this setting, an exception is thrown in those cases)
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // to write java.util.Date, Calendar as number (timestamp):
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // DeserializationFeature for changing how JSON is read as POJOs:

        // to prevent exception when encountering unknown property:
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // to allow coercion of JSON empty String ("") to null Object value:
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);*/
    }
    public static ObjectMapper getMapper(){
        return mapper;
    }

}
