package com.sprintboot.product.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();
    private JacksonUtil(){
    }

    public static String convertObjectToJson(Object object){
        try {
            if(object != null)
                return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T convertJsonToObject(String jsonString, Class<T> classOfT) {
        try {
            if(jsonString != null)
                return mapper.readValue(jsonString, classOfT);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] convertObjectToJsonBytes(Object object)  {
        try {
            if(object != null)
                return mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}