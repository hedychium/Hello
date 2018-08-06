package com.jl.UTILS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.google.gson.*;

/**
 * ʹ��Gson�Ƚ�����json�ַ����Ƿ�ȼ�
 */
public class JsonSameUtil {
    private static final Gson gson = new Gson();
    private static final JsonParser parser = new JsonParser();
 
    /**
     * �Ƚ�����bean�Ƿ�ȼ�
     */
    public static boolean same(Object a, Object b) {
        if (a == null) {
            return b == null;
        }
        return same(gson.toJson(a), gson.toJson(b));
    }
 
    /**
     * �Ƚ�����json�ַ����Ƿ�ȼ�
     */
    public static boolean same(String a, String b) {
        if (a == null) {
            return b == null;
        }
        if (a.equals(b)) {
            return true;
        }
        JsonElement aElement = parser.parse(a);
        JsonElement bElement = parser.parse(b);
        if (gson.toJson(aElement).equals(gson.toJson(bElement))) {
            return true;
        }
        return same(aElement, bElement);
    }
 
    private static boolean same(JsonElement a, JsonElement b) {
        if (a.isJsonObject() && b.isJsonObject()) {
            return same((JsonObject) a, (JsonObject) b);
        } else if (a.isJsonArray() && b.isJsonArray()) {
            return same((JsonArray) a, (JsonArray) b);
        } else if (a.isJsonPrimitive() && b.isJsonPrimitive()) {
            return same((JsonPrimitive) a, (JsonPrimitive) b);
        } else if (a.isJsonNull() && b.isJsonNull()) {
            return same((JsonNull) a, (JsonNull) b);
        } else {
            return Boolean.FALSE;
        }
    }
 
    private static boolean same(JsonObject a, JsonObject b) {
        Set<String> aSet = a.keySet();
        Set<String> bSet = b.keySet();
        if (!aSet.equals(bSet)) {
            return false;
        }
        for (String aKey : aSet) {
            if (!same(a.get(aKey), b.get(aKey))) {
                return false;
            }
        }
        return true;
    }
 
    private static boolean same(JsonArray a, JsonArray b) {
        if (a.size() != b.size()) {
            return false;
        }
        List<JsonElement> aList = toSortedList(a);
        List<JsonElement> bList = toSortedList(b);
        for (int i = 0; i < aList.size(); i++) {
            if (!same(aList.get(i), bList.get(i))) {
                return false;
            }
        }
        return true;
    }
 
    private static boolean same(JsonPrimitive a, JsonPrimitive b) {
 
        return a.equals(b);
    }
 
    private static boolean same(JsonNull a, JsonNull b) {
        return true;
    }
 
    private static List<JsonElement> toSortedList(JsonArray a) {
        List<JsonElement> aList = new ArrayList<>();
        a.forEach(aList::add);
        aList.sort(Comparator.comparing(gson::toJson));
        return aList;
    }
    public static void main(String[] args) {
        List<String> obj1 = Arrays.asList("1", "2", "3");
        List<String> obj2 = Arrays.asList("1", "3", "2");
        System.out.println(JsonSameUtil.same(obj1, obj2)); // true
 
        String str1 = "[{\"a\":1},{\"b\":2},{\"c\":3}]";
        String str2 = "[{\"a\":1},{\"c\":3},{\"b\":2}]";
        System.out.println(JsonSameUtil.same(str1, str2)); // true
    }

}
