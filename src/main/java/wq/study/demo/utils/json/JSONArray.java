package wq.study.demo.utils.json;



import wq.study.demo.utils.JsonUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;

public class JSONArray extends ArrayList<Object> implements Cloneable, RandomAccess, Serializable {
    private static final long serialVersionUID = 1L;

    public JSONArray() {
        super();
    }

    public JSONArray(Object bean) {
        String json = JsonUtils.toJson(bean);
        addAll(JsonUtils.toJSONArray(json));
    }

    public JSONArray(List<Object> list) {
        addAll(list);
    }


    public JSONArray Add(Object e) {
        this.add(e);
        return this;
    }


    public String toString() {
        return JsonUtils.toJson(this);
    }

    public JSONObject getJSONObject(int index) {
        Object value = get(index);
        if (value instanceof JSONObject) {
            return (JSONObject) value;
        } else {
            return value instanceof Map ? new JSONObject((Map) value) : new JSONObject(value);
        }
    }

    public JSONArray getJSONArray(int index) {
        Object value = get(index);
        if (value instanceof JSONArray) {
            return (JSONArray) value;
        }
        if (value instanceof List) {
            return new JSONArray((List) value);
        }
        return new JSONArray(value);
    }

    public static JSONArray parseArray(String text){
        if (text == null){
            return null;
        }
        return JsonUtils.toJSONArray(text);
    }

    public static <T> List<T> parseObject(String key, Class<T> clazz) {
//        Object obj = get(key);
        return JsonUtils.toList(key, clazz);
    }

    public Object clone() {
        return new JSONArray(this);
    }

}
