var HelpshiftPlugin = {
  /**
  * Installs the Helpshift Android SDK in your app with Config.
  * @param {string} apiKey - Your developer API Key.
  * @param {string} domainName - Your domain name without any http:// or forward slashes.
  * @param {string} appId - The unique ID assigned to your app.
  * @param {object} [options] - The config to install call.
  */
  install: function (apiKey, domainName, appId, options) {
    if (options && typeof options === "object") {
      cordova.exec (null, null, "HelpshiftPlugin", "install",
        [apiKey, domainName, appId, options]);
    } else {
      cordova.exec (null, null, "HelpshiftPlugin", "install",
        [apiKey, domainName, appId]);
    }
  },

  /**
  * Shows faqs. This will show list of sections with search.
  * @param {object} [options] - Extra config for the API.
  */
  showFAQs: function (options) {
    if (options && typeof options === "object") {
      cordova.exec (null, null, "HelpshiftPlugin", "showFAQs", [options]);
    } else {
      cordova.exec (null, null, "HelpshiftPlugin", "showFAQs", []);
    }
  },

  /**
  * You can use this api call to provide a way for the user to send feedback or start a new conversation with you.
  * @param {object} [options] - Extra config for the API.
  */
  showConversation: function (options) {
    if (options && typeof options === "object") {
      cordova.exec (null, null, "HelpshiftPlugin", "showConversation", [options]);
    } else {
      cordova.exec (null, null, "HelpshiftPlugin", "showConversation", []);
    }
  },

  /**
  * Shows FAQ section activity with Config.
  * This will show a FAQ section view with list of questions in that section.
  * The search inside this view will be limited to the specified section.
  * You can specify a section using publish ID of that section.
  * @param {string} faqSectionPublishId - Id specifying a section.
  * @param {object} [options] - Extra config for the API.
  */
  showFAQSection: function (faqSectionPublishId, options) {
    if (faqSectionPublishId && typeof faqSectionPublishId === "string") {
      if (options && typeof options === "object") {
        cordova.exec (null, null, "HelpshiftPlugin", "showFAQSection",
          [faqSectionPublishId, options]);
      } else {
        cordova.exec (null, null, "HelpshiftPlugin", "showFAQSection",
          [faqSectionPublishId]);
      }
    }
  },

  /**
  * Shows Single FAQ  activity with Config.
  * This will show a Single FAQ
  * You can specify a faq using publish ID of that section.
  * @param {string} faqPublishId - Id specifying a FAQ.
  * @param {object} [options] - Extra config for the API.
  */
  showSingleFAQ: function (faqPublishId, options) {
    if (faqPublishId && typeof faqPublishId === "string") {
      if (options && typeof options === "object") {
        cordova.exec (null, null, "HelpshiftPlugin", "showSingleFAQ",
          [faqPublishId, options]);
      } else {
        cordova.exec (null, null, "HelpshiftPlugin", "showSingleFAQ",
          [faqPublishId]);
      }
    }
  },

  /**
   * This will show the Dynamic Form screen to facilitate Guided ticket filing.
   *
   * @param {object} [options] - Your list of flows
   */
  showDynamicForm: function (flowsData, title, configOptions) {
    cordova.exec (null, null, "HelpshiftPlugin", "showDynamicForm", [flowsData, title, configOptions]);
  },

  /**
  * (Optional) If you already have indentification for your users, you can specify that as well.
  * @param {string} userIdentifier - A custom user Identifier.
  */
  setUserIdentifier: function (userIdentifier) {
    if (userIdentifier && typeof userIdentifier === "string") {
      cordova.exec (null, null, "HelpshiftPlugin", "setUserIdentifier",
        [userIdentifier]);
    }
  },

  /**
  * Login a user with a given identifier, name and email. This API introduces support for multiple login in Helpshift.
  * The identifier uniquely identifies the user.
  * If any Helpshift session is active, then any login attempt is ignored.
  * @param {string} identifier - The unique identifier of the user.
  * @param {string} name - The name of the user.
  * @param {string} email - The email of the user.
  */
  login: function (userIdentifier, name, email) {
    cordova.exec (null, null, "HelpshiftPlugin", "login", [userIdentifier, name, email]);
  },

  /**
  * You can specify the name and email for your User.
  * If you want to reset both values, you should pass null for both params
  * @param {string} name - The name of the user.
  * @param {string} email - The email of the user.
  */
  setNameAndEmail: function (name, email) {
    var lName = null, lEmail = null;
    if (name && typeof name === "string") {
      lName = name;
    }
    if (email && typeof email === "string") {
      lEmail = email;
    }
    cordova.exec (null, null, "HelpshiftPlugin", "setNameAndEmail",
      [lName, lEmail]);
  },

  /**
  * Adds additonal debugging information in your code.
  * You can add additional debugging statements to your code,
  * and see exactly what the user was doing right before they started a new conversation
  * @param {string} breadCrumb - Action/Message to add to bread-crumbs list.
  */
  leaveBreadCrumb: function (breadCrumb) {
    if (breadCrumb && typeof breadCrumb === "string") {
      cordova.exec (null, null, "HelpshiftPlugin", "leaveBreadCrumb",
        [breadCrumb]);
    }
  },

  /**
  * Logout a currently logged in user.
  * After logout, Helpshift falls back to the default login. If any Helpshift session is active, then any logout attempt is ignored.
  */
  logout: function () {
    cordova.exec (null, null, "HelpshiftPlugin", "logout", []);
  },

  /**
  * Fetch the unread message count to show in your custom notification
  * @param {string} isAsync - Fetch count asynchronously or synchronously.
  * @param {function} callBack - (Only for Android) CallBack function which gets called with asynchronous count.
  */
  getNotificationCount: function (isAsync, callBack) {
    var notificationCB;
    notificationCB = callBack;
    if (arguments.length === 1 ) {
      cordova.exec (function (count) {
        return count;
      }, null, "HelpshiftPlugin", "getNotificationCount", [isAsync]);
    } else if (arguments.length === 2 && isAsync === "YES") {
      cordova.exec (function (count) {
        if (notificationCB) {
          notificationCB.apply (this, [count]);
        }
        return count;
      }, null, "HelpshiftPlugin", "getNotificationCount", [isAsync, callBack]);
    }
  },

  /**
  * Set the SDK language for the given locale.
  * @param {string} locale - ocale contains either language code or both language code and country code in case of android.
  */
  setSDKLanguage: function (locale) {
    if (locale && typeof locale === "string") {
      cordova.exec (null, null, "HelpshiftPlugin", "setSDKLanguage", [locale]);
    }
  },

  /**
  * Clears Breadcrumbs list.
  * Breadcrumbs list stores upto 100 latest actions. You'll receive those in every Issue.
  * But if for reason you want to clear previous messages (On app load, for eg), you can do that by calling following function.
  */
  clearBreadCrumbs: function () {
    cordova.exec (null, null, "HelpshiftPlugin", "clearBreadCrumbs", []);
  },

  /**
  * Display an alert dialog, which prompts the user to rate your app, or send feedback.
  * The alertview is not shown if a conversation is currently running with the user or if you give a null/invalid url.
  * @param {string} url - Android/iOS application link.
  * @param {function} callBackFunction - The callback which will returns the user choice.
  */
  showAlertToRateAppWithURL: function (url, callBackFunction) {
    var alertToRateAppCB;
    alertToRateAppCB = callBackFunction;
    if (url && typeof url === "string") {
      cordova.exec (function (message) {
        if (alertToRateAppCB) {
          alertToRateAppCB.apply (this, [message]);
        }
      }, null, "HelpshiftPlugin", "showAlertToRateAppWithURL", [url]);
    }
  },

  /**
  * Register callback for the  Helpshift session delegates.
  * @param {function} sessionStart - Callback to session start delegate.
  * @param {function} sessionEnd - Callback to session end delegate.
  */
  registerSessionDelegates: function (sessionStart,sessionEnd) {
    var sessionStartCB , sessionEndCB;
    this.sessionStartCB = sessionStart;
    this.sessionEndCB = sessionEnd;

    sessionStartCB = sessionStart;
    sessionEndCB = sessionEnd;
    cordova.exec (function (message) {
      if(message ["eventname"] === "helpshiftSupportSessionStarted") {
        sessionStartCB.apply (this, []);
      } else if(message["eventname"] ===  "helpshiftSupportSessionEnded") {
        sessionEndCB.apply (this, []);
      }}, null, "HelpshiftPlugin", "registerSessionDelegates", []);
  },

  /**
  * Register callback for the  Helpshift conversation delegates.
  * @param {function} newConversationStarted - Callback to newConversationStarted delegate.
  * @param {function} userRepliedToConversation - Callback to userRepliedToConversation delegate.
  * @param {function} userCompletedCustomerSatisfactionSurvey - Callback to userCompletedCustomerSatisfactionSurvey delegate.
  * @param {function} didReceiveNotification - Callback to didReceiveNotification delegate.
  * @param {function} didReceiveInAppNotificationWithMessageCount - Callback to didReceiveInAppNotificationWithMessageCount delegate (Only for iOS).
  * @param {function} displayAttachmentFile - Callback to displayAttachmentFile delegate
  */
  registerConversationDelegates: function (newConversationStarted,
                                   userRepliedToConversation,
                                   userCompletedCustomerSatisfactionSurvey,
                                   didReceiveNotification,
                                   didReceiveInAppNotificationWithMessageCount,
                                   displayAttachmentFile) {
    var newConversationStartedCB, userRepliedToConversationCB, userCompletedCustomerSatisfactionSurveyCB, didReceiveNotificationCB, didReceiveInAppNotificationWithMessageCountCB, displayAttachmentFileCB;
    this.newConversationStartedCB = newConversationStarted;
    this.userRepliedToConversationCB = userRepliedToConversation;
    this.userCompletedCustomerSatisfactionSurveyCB = userCompletedCustomerSatisfactionSurvey;
    this.didReceiveNotificationCB = didReceiveNotification;
    this.didReceiveInAppNotificationWithMessageCountCB = didReceiveInAppNotificationWithMessageCount;
    this.displayAttachmentFileCB = displayAttachmentFile;

    newConversationStartedCB = newConversationStarted;
    userRepliedToConversationCB = userRepliedToConversation;
    userCompletedCustomerSatisfactionSurveyCB = userCompletedCustomerSatisfactionSurvey;
    didReceiveNotificationCB = didReceiveNotification;
    didReceiveInAppNotificationWithMessageCountCB = didReceiveInAppNotificationWithMessageCount;
    displayAttachmentFileCB = displayAttachmentFile;
    cordova.exec (function (message) {
      if(message ["eventname"] === "newConversationStartedWithMessage") {
        newConversationStartedCB.apply (this, [message["newConversationMessage"]]);
      } else if(message ["eventname"] === "userRepliedToConversationWithMessage") {
        alert("register");
        userRepliedToConversationCB.apply (this, [message["userRepliedMessage"]]);
      } else if(message ["eventname"] === "userCompletedCustomerSatisfactionSurvey") {
        userCompletedCustomerSatisfactionSurveyCB.apply (this, [message["rating"], message["feedback"]]);
      } else if(message ["eventname"] === "didReceiveInAppNotificationWithMessageCount") {
        didReceiveNotificationCB.apply (this, [message["newMessagesCount"]]);
      } else if (message ["eventname"] === "displayAttachmentFileAtLocation") {
        displayAttachmentFileCB.apply (this, [message ["file"]]);
      }
    }, null, "HelpshiftPlugin", "registerConversationDelegates", []);
  },

  /**
  * (For iOS and Android)
  * If you are using GCM push or Urban Airship and if you want to enable Push Notification,
  * set the Android Push ID (APID) using this method,
  * @param {string} regstrationID - This is the Android Push ID (APID)..
  */
  registerDeviceToken: function(regstrationID) {
    if (regstrationID && typeof regstrationID === "string") {
      cordova.exec (null, null, "HelpshiftPlugin", "registerDeviceToken", [regstrationID]);
    }
  },

  /**
  * (Only for Android)
  * This will handle push received from Helpshift using either GCM push or Urban Airship.
  * @param {object} key-value data to create notification.
  */
  handlePush: function(data) {
    if (data) {
      cordova.exec (null, null, "HelpshiftPlugin", "handlePush", [data]);
    }
  },

  /**
  * (Only for iOS)
  * If you want to forward the push notification for the Helpshift lib to handle
  */
  handleRemoteNotification: function(notification) {
    cordova.exec (null, null, "HelpshiftPlugin", "handleRemoteNotification", [notification]);
  },

  /**
  * (Only for iOS)
  * If you want to Forward the local notification for the Helpshift lib to handle
  */
  handleLocalNotification: function(notification) {
    cordova.exec (null, null, "HelpshiftPlugin", "handleLocalNotification", [notification]);
  },

  /**
  * (Only for iOS)
  * If you want to pause the in app notifications
  */
  pauseDisplayOfInAppNotification: function(pauseInApp) {
    if (pauseInApp && typeof pauseInApp === "object"){
      cordiva.exec (null, null, "HelpshiftPlugin", "pauseDisplayOfInAppNotification", [pauseInApp]);
    }
  },

  /**
  * This will print error logs which you can see on the Helpshift dashboard.
  * @param {string} tag - Tag for the log.
  * @param {string} tag - Log to be printed.
  */
  logE: function(tag, log) {
    cordova.exec (null, null, "HelpshiftPlugin", "logE", [tag, log]);
  },

  /**
  * This will print verbose logs which you can see on the Helpshift dashboard.
  * @param {string} tag - Tag for the log.
  * @param {string} tag - Log to be printed.
  */
  logV: function(tag, log) {
    cordova.exec (null, null, "HelpshiftPlugin", "logV", [tag, log]);
  },

  /**
  * This will print debug logs which you can see on the Helpshift dashboard.
  * @param {string} tag - Tag for the log.
  * @param {string} tag - Log to be printed.
  */
  logD: function(tag, log) {
    cordova.exec (null, null, "HelpshiftPlugin", "logD", [tag, log]);
  },

  /**
  * This will print index logs which you can see on the Helpshift dashboard.
  * @param {string} tag - Tag for the log.
  * @param {string} tag - Log to be printed.
  */
  logI: function(tag, log) {
    cordova.exec (null, null, "HelpshiftPlugin", "logI", [tag, log]);
  },

  /**
  * This will print warning logs which you can see on the Helpshift dashboard.
  * @param {string} tag - Tag for the log.
  * @param {string} tag - Log to be printed.
  */
  logW: function(tag, log) {
    cordova.exec (null, null, "HelpshiftPlugin", "logW", [tag, log]);
  },

  /**
  * Session start call back for ios.
  */
  _nativeSessionBeganCall: function () {
    if (this.sessionStartCB) {
      this.sessionStartCB.apply (null, []);
    }
  },

  /**
  * Session End call back for ios.
  */
  _nativeSessionEndedCall: function () {
    if (this.sessionEndCB) {
      this.sessionEndCB.apply (null, []);
    }
  },
  /**
  * showAlertToRateAppWithURL call back for ios.
  * @param {string} message - User's choice.
  */
  _nativeAppRateResponseCall: function (message) {
    if (this.alertToRateAppCB) {
      this.alertToRateAppCB.apply (null, [message]);
    }
  },

  /**
  * DidReceiveNotification call back for ios.
  * @param {string} message - Unread message count.
  */
  _nativeNotificationCall: function (message) {
    if (this.didReceiveNotificationCB) {
      this.didReceiveNotificationCB.apply (null, [message]);
    }
  },

  /**
  * DidReceiveInAppNotification call back for ios.
  * @param {string} message - Unread message count.
  */
  _nativeInAppNotificationCall: function (message) {
    if (this.didReceiveInAppNotificationWithMessageCountCB) {
      this.didReceiveInAppNotificationWithMessageCountCB.apply (null, [message]);
    }
  },

  /**
  * NewConversationStarted call back for ios.
  * @param {string} message -  New conversation message.
  */
  _nativeNewConversationStartedWithMessageCall: function (message) {
    if (this.newConversationStartedCB) {
      this.newConversationStartedCB.apply (null, [message]);
    }
  },

  /**
  * UserRepliedToConversation call back for ios.
  * @param {string} message - New message send from the user.
  */
  _nativeUserRepliedToConversationWithMessageCall: function (message) {
    if (this.userRepliedToConversationCB) {
      this.userRepliedToConversationCB.apply (null, [message]);
    }
  },

  /**
  * UserCompletedCustomerSatisfactionSurvey call back for ios.
  * @param {string} message - Feedback when user completes the customer satisfaction survey.
  * @param {integer} rating - Rating given when user completes the customer satisfaction survey.
  */
  _nativeUserCompletedCustomerSatisfactionSurveyCall: function (rating,message) {
    if (this.userCompletedCustomerSatisfactionSurveyCB) {
      this.userCompletedCustomerSatisfactionSurveyCB.apply (null, [rating, message]);
    }
  },

  /**
  * displayAttachmentFileAtLocation call back for ios.
  * @param {string} filelocation - The location of the file to be displayed
  */
  _nativeDisplayAttachmentFileAtLocation: function(fileLocation) {
    if (this.displayAttachmentFileCB) {
      this.displayAttachmentFileCB.apply (null, [fileLocation]);
    }
  }
};
module.exports = HelpshiftPlugin;
