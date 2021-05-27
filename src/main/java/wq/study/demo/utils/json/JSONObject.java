package wq.study.demo.utils.json;

import org.apache.commons.lang3.math.NumberUtils;
import wq.study.demo.utils.JsonUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JSONObject extends HashMap<String, Object> implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;


    public JSONObject(Object bean) {
        String json = JsonUtils.toJson(bean);
        putAll(defaultMap(JsonUtils.toJSONObject(json)));
    }

    public JSONObject() {
    }

    public JSONObject(Map map) {
        putAll(map);
    }

    public JSONObject(String json) {
        putAll(defaultMap(JsonUtils.toJSONObject(json)));
    }

    private Map defaultMap(Map map) {
        return map == null ? new HashMap<>() : map;
    }


    public boolean has(String key) {
        return containsKey(key);
    }

    public Iterator<String> keys() {
        return keySet().iterator();
    }


    public JSONObject getJSONObject(String key) {
        Object value = get(key);

        if (value instanceof JSONObject) {
            return (JSONObject) value;
        }

        return JsonUtils.toObject(value, JSONObject.class);
    }

    public JSONArray getJSONArray(String key) {
        Object value = get(key);

        if (value instanceof JSONArray) {
            return (JSONArray) value;
        }

        return new JSONArray(value);
    }

    public <T> T getObject(String key, Class<T> clazz) {
        Object obj = get(key);
        return JsonUtils.toObject(obj, clazz);
    }

    public static String toJSONString(Object object){

        return JsonUtils.toJson(object);
    }

    public static JSONObject parseObject(String text){
        return JsonUtils.toJSONObject(text);
    }

    public static <T> T parseObject(String text,Class<T> tClass){
        if (text == null){
            return null;
        }
        return JsonUtils.toObject(text,tClass);
    }

    public static Map<String, Object> parse(String text){
        return JsonUtils.toObject(text,HashMap.class);
    }

    public int getInt(String key) {
        Object value = get(key);

        if (value == null) {
            return 0;
        }

        return NumberUtils.toInt(String.valueOf(value));
    }

    public Integer getInteger(String key){
        if (key.isEmpty()){
            return null;
        }
        Object value = get(key);
        return NumberUtils.createInteger(String.valueOf(value));
    }

    public Long getLong(String key) {
        Object value = get(key);
        if (value == null){
            return null;
        }
        return NumberUtils.toLong(String.valueOf(value));
    }


    public Float getFloat(String key) {
        Object value = get(key);

        return NumberUtils.toFloat(String.valueOf(value));
    }

    public String getString(String key) {
        Object value = get(key);

        if (value == null) {
            return null;
        }

        return value.toString();
    }



    @Override
    public Object clone() {
        return new JSONObject(this);
    }

    public String toString() {
        return JsonUtils.toJson(this);
    }

}
