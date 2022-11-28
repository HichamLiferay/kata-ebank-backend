package com.kata.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.time.DateUtils;


import java.io.IOException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class JsonHelper {

    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonHelper() { }

    public static String toJson(Object targetObject) throws JsonProcessingException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"){
            public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition pos){
                StringBuffer toFix = super.format(DateUtils.setMilliseconds(date,0), toAppendTo, pos);
                return toFix.insert(toFix.length()-2, ':');
            }
        };
        mapper.setDateFormat(df);
        return mapper.writeValueAsString(targetObject);
    }

    public static <T> T fromJson(String targetJson, Class<T> clazz) throws IOException {
        return mapper.readValue(targetJson, clazz);
    }
}
