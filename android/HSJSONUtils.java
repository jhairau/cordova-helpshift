package com.helpshift.android;

import android.util.Log;
import android.content.Context;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import com.helpshift.support.*;
import com.helpshift.support.flows.*;
public class HSJSONUtils {
    private static final String TAG = "PhoneGap/HelpShiftDebug";
    public static HashMap convertToHashMap(JSONObject argumentsObject) {
        HashMap<String,Object> config = new HashMap<String,Object>();
        try {
            Object value = argumentsObject.opt("enableContactUs");
            if(value != null) {
                config.put("enableContactUs", stringToInt(value.toString()));
            }
            config.put("enableInAppNotification", convertKey("enableInAppNotification", argumentsObject, true));
            config.put("requireEmail", convertKey("requireEmail", argumentsObject, false));
            config.put("hideNameAndEmail", convertKey("hideNameAndEmail", argumentsObject, false));
            config.put("enableFullPrivacy", convertKey("enableFullPrivacy", argumentsObject, false));
            config.put("showSearchOnNewConversation", convertKey("showSearchOnNewConversation", argumentsObject, false));
            config.put("gotoConversationAfterContactUs", convertKey("gotoConversationAfterContactUs", argumentsObject, false));
            config.put("showConversationResolutionQuestion", convertKey("showConversationResolutionQuestion", argumentsObject, true));
            config.put("enableDefaultFallbackLanguage", convertKey("enableDefaultFallbackLanguage", argumentsObject, true));

            if (argumentsObject.has("HSCUSTOMMETADATAKEY")) {
                JSONObject metaData = argumentsObject.getJSONObject("HSCUSTOMMETADATAKEY");
                HashMap metaMap = (HashMap) HSJSONUtils.toMap(metaData);
                if(metaData.has("HSTAGSKEY")) {
                    List tags = HSJSONUtils.toList(metaData.getJSONArray("HSTAGSKEY"));
                    String[] tagsArray = new String[tags.size()];
                    metaMap.put(Support.TagsKey, (String[])tags.toArray(tagsArray));
                    metaMap.remove("HSTAGSKEY");
                }
                config.put(Support.CustomMetadataKey,metaMap);
            }

            if (argumentsObject.has("withTagsMatching")) {
                JSONObject filterFaqsData = argumentsObject.getJSONObject("withTagsMatching");
                HashMap filterFaqsMap = (HashMap) HSJSONUtils.toMap(filterFaqsData);
                if (filterFaqsData.has("tags")) {
                    List tags = HSJSONUtils.toList(filterFaqsData.getJSONArray("tags"));
                    String[] tagsArray = new String[tags.size()];
                    filterFaqsMap.put("tags", (String[])tags.toArray(tagsArray));
                }
                config.put("withTagsMatching", filterFaqsMap);
            }
        } catch (Exception e) {
            Log.e(TAG,e.toString());
        }
        return config;
    }

    private static boolean convertKey(String key,JSONObject argumentsObject, boolean defaultValue) {
        try {
            Object value = argumentsObject.opt(key);
            if (value == null) {
                return defaultValue;
            } else if(value.toString().equals("YES")) {
                return true;
            } else if(value.toString().equals("NO")){
                return false;
            }
        } catch (Exception e) {
            return defaultValue;
        }
        return defaultValue;
    }

    private static int stringToInt(String str) {
        int returnval = Support.EnableContactUs.ALWAYS;
        if(str.equals("ALWAYS")) {
            returnval = Support.EnableContactUs.ALWAYS;
        } else if(str.equals("AFTER_VIEWING_FAQS")) {
            returnval = Support.EnableContactUs.AFTER_VIEWING_FAQS;
        } else if(str.equals("NEVER")) {
            returnval = Support.EnableContactUs.NEVER;
        } else if (str.equals("AFTER_MARKING_ANSWER_UNHELPFUL")) {
            returnval = Support.EnableContactUs.AFTER_MARKING_ANSWER_UNHELPFUL;
        }
        return returnval;
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap();
        Iterator keys = object.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            map.put(key, fromJson(object.get(key)));
        }
        return map;
    }

    public static List toList(JSONArray array) throws JSONException {
        List list = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            list.add(fromJson(array.get(i)));
        }
        return list;
    }

    private static Object fromJson(Object json) throws JSONException {
        if (json == JSONObject.NULL) {
            return null;
        } else if (json instanceof JSONObject) {
            return toMap((JSONObject) json);
        } else if (json instanceof JSONArray) {
            return toList((JSONArray) json);
        } else {
            return json;
        }
    }

    public static List toFlowList(JSONArray array) throws JSONException {
        List list = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            list.add(toFlowMap(array.getJSONObject(i)));
        }
        return list;
    }

    public static Map<String, Object> toFlowMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap();
        Iterator keys = object.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            map.put(key, fromFlowJson(object.get(key)));
        }
        return map;
    }

    private static Object fromFlowJson(Object json) throws JSONException {
        if (json == JSONObject.NULL) {
            return null;
        } else if (json instanceof JSONObject) {
            return toFlowMap((JSONObject) json);
        } else if (json instanceof JSONArray) {
            return toFlowList((JSONArray) json);
        } else {
            return json;
        }
    }
}
