#import <Cordova/CDV.h>
#import "HelpshiftSupport.h"
#import "HelpshiftCore.h"

@interface HelpshiftPlugin:CDVPlugin <HelpshiftSupportDelegate>

@property NSMutableArray *fileFormats;

- (NSMutableDictionary*) convertMetaData : (NSMutableDictionary*) config;

- (void) install :(CDVInvokedUrlCommand*)command;

- (void) showFAQs:(CDVInvokedUrlCommand*)command;

- (void) showConversation:(CDVInvokedUrlCommand*)command;

- (void) showFAQSection:(CDVInvokedUrlCommand*)command;

- (void) showSingleFAQ:(CDVInvokedUrlCommand*)command;

- (void) setUserIdentifier:(CDVInvokedUrlCommand *)command;

- (void) setMetadataBlock:(CDVInvokedUrlCommand *)command;

- (void) setNameAndEmail:(CDVInvokedUrlCommand*)command;

- (void) leaveBreadCrumb:(CDVInvokedUrlCommand*)command;

- (void) clearBreadCrumbs:(CDVInvokedUrlCommand*)command;

- (void) login :(CDVInvokedUrlCommand*)command;

- (void) logout :(CDVInvokedUrlCommand*)command;

- (void) getNotificationCount:(CDVInvokedUrlCommand*)command;

- (void) registerDeviceToken:(CDVInvokedUrlCommand*)command;

- (void) handleRemoteNotification:(CDVInvokedUrlCommand *)command;

- (void) handleLocalNotification:(CDVInvokedUrlCommand *)command;

- (void) pauseDisplayOfInAppNotification:(CDVInvokedUrlCommand *)command;

- (void) registerSessionDelegates :(CDVInvokedUrlCommand*)command;

- (void) registerConversationDelegates :(CDVInvokedUrlCommand*)command;

- (void) setSDKLanguage:(CDVInvokedUrlCommand*)command;

- (void) showAlertToRateAppWithURL:(CDVInvokedUrlCommand*) command;

- (void) showDynamicForm:(CDVInvokedUrlCommand*) command;

- (void) logE:(CDVInvokedUrlCommand*)command;

- (void) logW:(CDVInvokedUrlCommand*)command;

- (void) logI:(CDVInvokedUrlCommand*)command;

- (void) logV:(CDVInvokedUrlCommand*)command;

- (void) logD:(CDVInvokedUrlCommand*)command;

@end
