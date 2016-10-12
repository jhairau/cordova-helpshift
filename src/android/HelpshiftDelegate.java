package com.helpshift.android;

import com.helpshift.support.*;
import java.io.File;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.List;

class HelpshiftDelegate implements Support.Delegate {

    private static CallbackContext delegateSessionCB;
    private static CallbackContext delegateConversationCB;

    private static List<String> supportedFileFormats;

    public static void setSessionDelegateListener(CallbackContext contextCB) {
        delegateSessionCB = contextCB;
    }

    public static void setConversationDelegateListener(CallbackContext contextCB) {
        delegateConversationCB = contextCB;
    }
    @Override
    public void sessionBegan() {
        if(delegateSessionCB != null) {
            JSONObject array = new JSONObject();
            try {
                array.put("eventname","helpshiftSupportSessionStarted");
            } catch(Exception e) {
                Log.e("HelpshiftDebug",e.toString());
            }
            PluginResult result = new PluginResult(PluginResult.Status.OK, array);
            result.setKeepCallback(true);
            delegateSessionCB.sendPluginResult(result);
        }
    }

    @Override
    public void sessionEnded() {
        if(delegateSessionCB != null) {
            JSONObject array = new JSONObject();
            try {
                array.put("eventname","helpshiftSupportSessionEnded");
            } catch(Exception e) {
                Log.e("HelpshiftDebug",e.toString());
            }
            PluginResult result = new PluginResult(PluginResult.Status.OK, array);
            result.setKeepCallback(true);
            delegateSessionCB.sendPluginResult(result);
        }
    }

    @Override
    public void newConversationStarted(String newConversationMessage) {
        if(delegateConversationCB != null) {
            JSONObject array = new JSONObject();
            try {
                array.put("eventname","helpshiftSupportNewConversationStarted");
                array.put("newConversationMessage",newConversationMessage);
            } catch(Exception e) {
                Log.e("HelpshiftDebug",e.toString());
            }
            PluginResult result = new PluginResult(PluginResult.Status.OK, array);
            result.setKeepCallback(true);
            delegateConversationCB.sendPluginResult(result);
        }
    }

    @Override
    public void userRepliedToConversation(String newMessage) {
        if(delegateConversationCB != null) {
            JSONObject array = new JSONObject();
            try {
                array.put("eventname","helpshiftSupportUserRepliedToConversation");
                array.put("userRepliedMessage",newMessage);
            } catch(Exception e) {
                Log.e("HelpshiftDebug",e.toString());
            }
            PluginResult result = new PluginResult(PluginResult.Status.OK, array);
            result.setKeepCallback(true);
            delegateConversationCB.sendPluginResult(result);
        }
    }

    @Override
    public void userCompletedCustomerSatisfactionSurvey(int rating, String feedback) {
        if(delegateConversationCB != null) {
            JSONObject array = new JSONObject();
            try {
                array.put("eventname","helpshiftSupportUserCompletedCustomerSatisfactionSurvey");
                array.put("rating",String.valueOf(rating));
                array.put("feedback",feedback);
            } catch(Exception e) {
                Log.e("HelpshiftDebug",e.toString());
            }
            PluginResult result = new PluginResult(PluginResult.Status.OK, array);
            result.setKeepCallback(true);
            delegateConversationCB.sendPluginResult(result);
        }
    }

    @Override
    public void displayAttachmentFile(File attachmentFile) {
        if (delegateConversationCB != null) {
            String path = attachmentFile.getAbsolutePath();
            String fileName = attachmentFile.getName();
            String extension = null;
            if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
                extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            }
            if (extension != null && this.supportedFileFormats != null && this.supportedFileFormats.contains(extension)) {
                JSONObject array = new JSONObject();
                try {
                    array.put("eventname","helpshiftSupportDisplayAttachmentFile");
                    array.put("file", path);
                } catch(Exception e) {
                    Log.e("HelpshiftDebug",e.toString());
                }
                PluginResult result = new PluginResult(PluginResult.Status.OK, array);
                result.setKeepCallback(true);
                delegateConversationCB.sendPluginResult(result);
            }
        }
    }

    @Override
    public void didReceiveNotification(int newMessagesCount) {
        if(newMessagesCount > 0)
        {
            if(delegateConversationCB != null) {
                JSONObject array = new JSONObject();
                try {
                    array.put("eventname","helpshiftSupportDidReceiveNotification");
                    array.put("newMessagesCount",String.valueOf(newMessagesCount));
                } catch(Exception e) {
                    Log.e("HelpshiftDebug",e.toString());
                }
                PluginResult result = new PluginResult(PluginResult.Status.OK, array);
                result.setKeepCallback(true);
                delegateConversationCB.sendPluginResult(result);
            }
        }
    }

    public static void setSupportedFileFormats(List<String> fileFormats) {
        if (fileFormats != null) {
            supportedFileFormats = fileFormats;
        }
    }
}
