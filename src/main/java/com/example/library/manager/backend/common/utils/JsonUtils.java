package com.example.library.manager.backend.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/28
 */
@Slf4j
public class JsonUtils {

    private static final JsonMapper MAPPER = new JsonMapper();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static {
        MAPPER.setDateFormat(DATE_FORMAT);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String toJSONString(Object args) {
        try {
            return MAPPER.writeValueAsString(args);
        } catch (JsonProcessingException e) {
            log.error("json format occur err,data is :{},ex is:{}", args, e);
        }
        return "";
    }

    public static <T> T parseJson(String args, Class<T> clazz) {
        try {
            return MAPPER.readValue(args, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parseJson(String args, TypeReference<T> javaType)
            throws JsonProcessingException {
        return MAPPER.readValue(args, javaType);
    }

    public static <T> T parseJson(InputStream inputStream, TypeReference<T> javaType)
            throws IOException {
        return MAPPER.readValue(inputStream, javaType);
    }
}
