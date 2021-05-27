package wq.study.demo.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;
import wq.study.demo.utils.json.JSONArray;
import wq.study.demo.utils.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JSON 工具类
 *
 * @author coocaapc
 */
public class JsonUtils {
    public static class MyObjectMapper extends ObjectMapper {

        public MyObjectMapper() {
            setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            setSerializationInclusion(JsonInclude.Include.NON_NULL);
//            configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);// 过时的
            configure(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS, false);
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

//            configOverride(Map.class).setInclude(JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.NON_NULL));// 生效，被setNullValueSerializer替换

//            configOverride(List.class).setInclude(JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, null));
//            configOverride(Collection.class).setInclude(JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, null));
//            configOverride(Collection.class).setInclude(JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.NON_NULL));
//            configOverride(List.class).setInclude(JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.NON_NULL));
//            configOverride(List.class).setSetterInfo(JsonSetter.Value.forValueNulls(Nulls.AS_EMPTY));
//            configOverride(List.class).setSetterInfo(JsonSetter.Value.forContentNulls(Nulls.AS_EMPTY));
//            configOverride(List.class).setSetterInfo(JsonSetter.Value.forValueNulls(Nulls.AS_EMPTY, Nulls.AS_EMPTY));

            getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
                @Override
                public void serialize(Object obj, JsonGenerator jsonGen, SerializerProvider unused)
                        throws IOException, JsonProcessingException {
                }
            });
            //BasicDeserializerFactory
            SimpleModule module = new SimpleModule();
            module.addAbstractTypeMapping(Map.class, JSONObject.class);
            module.addAbstractTypeMapping(List.class, JSONArray.class);
            registerModule(module);

        }
    }


    protected static final ObjectMapper mapper = new MyObjectMapper();

    private static TypeFactory typeFactory;

    static {
        typeFactory = mapper.getTypeFactory();
    }

    /**
     * Json字符串转Java对象
     *
     */
    public static <T> T toObject(String jsonString, Class<T> classOfT) {
        try {
            JavaType javaType = typeFactory.constructType(classOfT);
            return mapper.readValue(jsonString,javaType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("parse error, info: " + jsonString + " classType: " + classOfT.getSimpleName());
        }
    }

    public static <T> T toObject(Object object, Class<T> classOfT) {
        try {
            String str = mapper.writeValueAsString(object);
            return mapper.readValue(str, new TypeReference<T>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> toList(String jsonStr, Class<T> classOfT) {
        try {
            List<T> ts = mapper.readValue(jsonStr, new TypeReference<List<T>>() {
            });
            return ts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Json字符串转List对象
     *
     */
    public static JSONArray toJSONArray(String jsonStr) {
        try {
            return mapper.readValue(jsonStr, new TypeReference<JSONArray>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
//        return JSONArray.parseArray(jsonStr, clazz);
    }

    /**
     * @param o 被转换对象
     * @return 字符串
     */
    public static JSONObject toJSONObject(String o) {
        try {
            return mapper.readValue(o, new TypeReference<JSONObject>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param o 被转换对象
     * @return 字符串
     */
    public static String toJson(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static <T> List<T> parseToList(String text, Class<T> targetClass) {
        JavaType javaType = getCollectionType(ArrayList.class, targetClass);
        try {
            return mapper.readValue(text, javaType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("json parse error! JsonArray is: " + text);
        }
    }

    private static JavaType getCollectionType(Class<?> containerClass, Class<?>... targetClass) {
        return typeFactory.constructParametricType(containerClass, targetClass);
    }

}
