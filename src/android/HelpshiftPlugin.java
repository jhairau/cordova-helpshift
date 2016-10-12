package com.helpshift.android;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.HashMap;

import com.helpshift.Core;
import com.helpshift.support.*;
import com.helpshift.support.flows.*;


public class HelpshiftPlugin extends CordovaPlugin {

    private static final String TAG = "HelpShiftDebug";
    private static final String INSTALL = "install";

    private static final String SHOW_CONVERSATION = "showConversation";
    private static final String SHOW_FAQS = "showFAQs";
    private static final String SHOW_SINGLE_FAQ = "showSingleFAQ";
    private static final String SHOW_FAQ_SECTION = "showFAQSection";
    private static final String SHOW_DYNAMIC_FORM = "showDynamicForm";
    private static final String SHOW_ALERT_TO_RATE_APP = "showAlertToRateAppWithURL";

    private static final String SET_USER_IDENTIFIER = "setUserIdentifier";
    private static final String SET_NAME_AND_EMAIL = "setNameAndEmail";
    private static final String LEAVE_BREAD_CRUMB = "leaveBreadCrumb";
    private static final String CLEAR_BREAD_CRUMBS = "clearBreadCrumbs";

    private static final String GET_NOTIFICATION_COUNT = "getNotificationCount";
    private static final String REGISTER_DEVICE_TOKEN = "registerDeviceToken";
    private static final String REGISTER_SESSION_DELEGATES = "registerSessionDelegates";
    private static final String REGISTER_CONVERSATION_DELEGATES = "registerConversationDelegates";
    private static final String HANDLE_PUSH = "handlePush";
    private static final String LOGIN = "login";
    private static final String LOGOUT = "logout";
    private static final String SET_SDK_LANGUAGE = "setSDKLanguage";
    private static final String LOG_E = "logE";
    private static final String LOG_V = "logV";
    private static final String LOG_I = "logI";
    private static final String LOG_W = "logW";
    private static final String LOG_D = "logD";

    public static final String CONVERSATION_FLOW = "conversationFlow";
    public static final String FAQS_FLOW = "faqsFlow";
    public static final String FAQ_SECTION_FLOW = "faqSectionFlow";
    public static final String SINGLE_FAQ_FLOW = "singleFaqFlow";
    public static final String DYNAMIC_FORM_FLOW = "dynamicFormFlow";

    private HelpshiftDelegate delegateObject;
    private CallbackContext notificationCallback;

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        delegateObject = new HelpshiftDelegate();
        Support.setDelegate(delegateObject);
    }

    @Override
    public boolean execute(String function, JSONArray arguments, CallbackContext callbackContext) throws JSONException {
        if (INSTALL.equals(function)) {
            Core.init(Support.getInstance());
            String apiKey = arguments.getString(0);
            String domainName = arguments.getString(1);
            String appID = arguments.getString(2);
            HashMap<String,Object> config = new HashMap<String,Object>();
            config.put("sdkType", "phonegap");
            if (arguments.length() == 4) {
                JSONObject configObject = arguments.getJSONObject(3);
                config.putAll(HSJSONUtils.convertToHashMap(configObject));
                if (configObject.has("supportedFileFormats")) {
                    JSONArray fileFormatsArray = configObject.getJSONArray("supportedFileFormats");
                    HelpshiftDelegate.setSupportedFileFormats(HSJSONUtils.toList(fileFormatsArray));
                }
            }
            Core.install (cordova.getActivity().getApplication(), apiKey, domainName, appID, config);
            callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
            return true;
        } else if (SHOW_FAQS.equals(function)) {
            if (arguments.length() == 1) {
                HashMap<String,Object> config = new HashMap<String,Object>();
                config = HSJSONUtils.convertToHashMap(arguments.getJSONObject(0));
                Support.showFAQs(cordova.getActivity(), config);
                callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
                return true;
            }
            Support.showFAQs(cordova.getActivity());
            callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
            return true;
        } else if (SHOW_CONVERSATION.equals(function)) {
            if (arguments.length() == 1) {
                HashMap<String,Object> config = new HashMap<String,Object>();
                config = HSJSONUtils.convertToHashMap(arguments.getJSONObject(0));
                Support.showConversation(cordova.getActivity(), config);
                callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
                return true;
            }
            Support.showConversation(cordova.getActivity());
            callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
            return true;
        } else if (SHOW_SINGLE_FAQ.equals(function)) {
            String faqId = arguments.getString(0);
            if (arguments.length() == 2) {
                HashMap<String,Object> config = new HashMap<String,Object>();
                config = HSJSONUtils.convertToHashMap(arguments.getJSONObject(0));
                Support.showSingleFAQ(cordova.getActivity(), faqId, config);
                callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
                return true;
            }
            Support.showSingleFAQ(cordova.getActivity(), faqId);
            callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
            return true;
        } else if (SHOW_FAQ_SECTION.equals(function)) {
            String sectionId = arguments.getString(0);
            if (arguments.length() == 2) {
                HashMap<String,Object> config = new HashMap<String,Object>();
                config = HSJSONUtils.convertToHashMap(arguments.getJSONObject(0));
                Support.showFAQSection(cordova.getActivity(), sectionId, config);
                callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
                return true;
            }
            Support.showFAQSection(cordova.getActivity(), sectionId);
            callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
            return true;
        } else if (SHOW_DYNAMIC_FORM.equals(function)) {
            if (arguments.length() == 1) {
                Support.showDynamicFormFromData(cordova.getActivity(), HSJSONUtils.toFlowList(arguments.getJSONArray(0)));
                callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
                return true;
            }
        } else if (SHOW_ALERT_TO_RATE_APP.equals(function)) {
            PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT, "");
            result.setKeepCallback(true);
            this.notificationCallback = callbackContext;
            AlertToRateAppListener actionListener =  new AlertToRateAppListener() {
                    public void onAction(int action) {
                        String msg = "";
                        switch (action) {
                        case Support.RateAlert.CLOSE:
                            msg = "HS_RATE_ALERT_CLOSE";
                            break;
                        case Support.RateAlert.FEEDBACK:
                            msg = "HS_RATE_ALERT_FEEDBACK";
                            break;
                        case Support.RateAlert.SUCCESS:
                            msg = "HS_RATE_ALERT_SUCCESS";
                            break;
                        case Support.RateAlert.FAIL:
                            msg = "HS_RATE_ALERT_FAIL";
                            break;
                        }
                        notificationCallback.success(msg);
                    }
                };
            Support.showAlertToRateApp(arguments.getString(0), actionListener);
            return true;
        } else if (SET_NAME_AND_EMAIL.equals(function)) {
            String name = null;
            String email = null;
            name = arguments.getString(0);
            email = arguments.getString(1);
            if(arguments.isNull(0)) {
                name = null;
            }
            if(arguments.isNull(1)) {
                email = null;
            }
            Core.setNameAndEmail(name, email);
            callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
            return true;
        } else if (SET_USER_IDENTIFIER.equals(function)) {
            Support.setUserIdentifier(arguments.getString(0));
            callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
            return true;
        } else if (REGISTER_DEVICE_TOKEN.equals(function)) {
            Core.registerDeviceToken(cordova.getActivity(), arguments.getString(0));
            callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
            return true;
        } else if (LEAVE_BREAD_CRUMB.equals(function)) {
            Support.leaveBreadCrumb(arguments.getString(0));
            callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
            return true;
        } else if (CLEAR_BREAD_CRUMBS.equals(function)) {
            Support.clearBreadCrumbs();
            callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
            return true;
        } else if (LOGIN.equals(function)) {
            String identifier = arguments.getString(0);
            String name = arguments.getString(1);
            String email = arguments.getString(2);
            Core.login(identifier,name,email);
            callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
            return true;
        } else if (LOGOUT.equals(function)) {
            Core.logout();
            callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
            return true;
        } else if (GET_NOTIFICATION_COUNT.equals(function)) {
            if (arguments.length() >= 2) {
                PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT, "");
                result.setKeepCallback(true);
                this.notificationCallback = callbackContext;
                Handler mainThreadHandler = new Handler(Looper.getMainLooper());
                mainThreadHandler.post (new Runnable()
                {
                    public void run ()
                    {
                        Handler countHandler = new Handler()
                        {
                            public void handleMessage(Message msg)
                            {
                                super.handleMessage(msg);
                                Bundle countData = (Bundle) msg.obj;
                                Integer count = countData.getInt("value");
                                Boolean cache = countData.getBoolean("cache");
                                notificationCallback.success(String.valueOf(count));
                            }
                        };
                        Support.getNotificationCount(countHandler, new Handler());
                    }
                });
            } else if (arguments.length() == 1) {
                int notifCount = Support.getNotificationCount();
                callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, String.valueOf(notifCount)));
                return true;
            }
        } else if (SET_SDK_LANGUAGE.equals(function)) {
            String locale = arguments.getString(0);
            Support.setSDKLanguage(locale);
            callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
            return true;
        } else if (REGISTER_SESSION_DELEGATES.equals(function)) {
            PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT, "");
            result.setKeepCallback(true);
            HelpshiftDelegate.setSessionDelegateListener(callbackContext);
            return true;
        } else if (REGISTER_CONVERSATION_DELEGATES.equals(function)) {
            PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT, "");
            result.setKeepCallback(true);
            HelpshiftDelegate.setConversationDelegateListener(callbackContext);
            return true;
        } else if (REGISTER_DEVICE_TOKEN.equals(function)) {
            Core.registerDeviceToken(cordova.getActivity(),arguments.getString(0));
            callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
            return true;
        } else if (HANDLE_PUSH.equals(function)) {
            Bundle bundle = new Bundle();
            JSONObject data = arguments.getJSONObject(0);
            Iterator<?> keys = data.keys();
            while( keys.hasNext() ) {
                String key = (String)keys.next();
                bundle.putString(key, data.getString(key));
            }
            Core.init(Support.getInstance());
            Core.handlePush(cordova.getActivity(), bundle);
            callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
            return true;
        } else if (LOG_E.equals(function)) {
            Log.e(arguments.getString(0),arguments.getString(1));
            callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
            return true;
        } else if (LOG_W.equals(function)) {
            Log.w(arguments.getString(0),arguments.getString(1));
            callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
            return true;
        } else if (LOG_I.equals(function)) {
            Log.i(arguments.getString(0),arguments.getString(1));
            callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
            return true;
        } else if (LOG_V.equals(function)) {
            Log.v(arguments.getString(0),arguments.getString(1));
            callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
            return true;
        } else if (LOG_D.equals(function)) {
            Log.d(arguments.getString(0),arguments.getString(1));
            callbackContext.sendPluginResult( new PluginResult(PluginResult.Status.OK, ""));
            return true;
        }
        return false;
    }
}
