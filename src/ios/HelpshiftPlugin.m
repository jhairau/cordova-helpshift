#import <Cordova/CDV.h>
#import "HelpshiftPlugin.h"

@implementation HelpshiftPlugin:CDVPlugin

@synthesize fileFormats = _fileFormats;

- (NSMutableDictionary*) convertMetaData : (NSMutableDictionary*) config {
    NSMutableDictionary *metaData = nil;
    NSArray *tags = nil;
    if([config objectForKey:@"HelpshiftSupportCustomMetadataKey"]!= nil) {
        metaData = [config objectForKey:@"HelpshiftSupportCustomMetadataKey"];
        if([metaData objectForKey:@"HelpshiftSupportTagsKey"] != nil) {
            tags = [metaData objectForKey:@"HelpshiftSupportTagsKey"];
            [metaData setObject:tags forKey:HelpshiftSupportTagsKey];
            [metaData removeObjectForKey:@"HelpshiftSupportTagsKey"];
        }
        [config removeObjectForKey:@"HelpshiftSupportCustomMetadataKey"];
        [config setObject:metaData forKey:HelpshiftSupportCustomMetadataKey];
    }
    return config;
}

- (void) install :(CDVInvokedUrlCommand*)command {
    NSString *apiKey = [command argumentAtIndex:0 ];
    NSString *domainName = [command argumentAtIndex:1 ];
    NSString *appId = [command argumentAtIndex:2 ];
    [self.commandDelegate runInBackground:^{
        NSMutableDictionary *configDict = [[command argumentAtIndex: 3] mutableCopy];
        if(!configDict) {
            configDict = [[NSMutableDictionary alloc] init];
        }
        [configDict setObject:@"phonegap" forKey:@"sdkType"];
        if([configDict objectForKey:@"supportedFileFormats"]) {
            _fileFormats = [[configDict objectForKey:@"supportedFileFormats"] valueForKey:@"lowercaseString"];
            [configDict removeObjectForKey:@"supportedFileFormats"];
        }
        [HelpshiftCore initializeWithProvider:[HelpshiftSupport sharedInstance]];
        [HelpshiftCore installForApiKey:apiKey domainName:domainName appID:appId withOptions:configDict];
        [[HelpshiftSupport sharedInstance] setDelegate:self];
    }];
}

- (void) showFAQs:(CDVInvokedUrlCommand*)command {
    if([command.arguments count] > 0) {
        NSMutableDictionary *optionsDict = [[NSMutableDictionary alloc] init];
        optionsDict = [command argumentAtIndex:0 ];
        optionsDict = [self convertMetaData:optionsDict];
        [HelpshiftSupport showFAQs:self.viewController withOptions:optionsDict];
    } else {
        [HelpshiftSupport showFAQs:self.viewController withOptions:nil];
    }
}

- (void) showConversation:(CDVInvokedUrlCommand*)command {
    if([command.arguments count] > 0) {
        NSMutableDictionary *optionsDict = [[NSMutableDictionary alloc] init];
        optionsDict = [command argumentAtIndex:0 ];
        optionsDict = [self convertMetaData:optionsDict];
        [HelpshiftSupport showConversation:self.viewController withOptions:optionsDict];
    } else {
        [HelpshiftSupport showConversation:self.viewController withOptions:nil];
    }
}

- (void) showFAQSection:(CDVInvokedUrlCommand*)command {
    NSString *sectionId = [command argumentAtIndex:0 ];
    if([command.arguments count] > 1) {
        NSMutableDictionary *optionsDict = [[NSMutableDictionary alloc] init];
        optionsDict = [command argumentAtIndex:1 ];
        optionsDict = [self convertMetaData:optionsDict];
        [HelpshiftSupport showFAQSection:sectionId withController:self.viewController withOptions:optionsDict];
    } else {
        [HelpshiftSupport showFAQSection:sectionId withController:self.viewController withOptions:nil];
    }
}

- (void) showSingleFAQ:(CDVInvokedUrlCommand*)command {
    NSString *sectionId = [command argumentAtIndex:0 ];
    if([command.arguments count] > 1) {
        NSMutableDictionary *optionsDict = [[NSMutableDictionary alloc] init];
        optionsDict = [command argumentAtIndex:1 ];
        optionsDict = [self convertMetaData:optionsDict];
        [HelpshiftSupport showSingleFAQ:sectionId withController:self.viewController withOptions:optionsDict];
    } else {
        [HelpshiftSupport showSingleFAQ:sectionId withController:self.viewController withOptions:nil];
    }
}

- (void) setUserIdentifier:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        NSString *userIdentifier = [command argumentAtIndex:0];
        [HelpshiftSupport setUserIdentifier:userIdentifier];
    }];
}

- (void) setMetadataBlock:(CDVInvokedUrlCommand *)command {
    [self.commandDelegate runInBackground:^{
        [HelpshiftSupport setMetadataBlock:[command argumentAtIndex:0]];
    }];
}

- (void) setNameAndEmail:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSString *userName = [command argumentAtIndex:0 ];
        userName = (userName == NULL ? nil : userName);
        NSString *userEmail = [command argumentAtIndex:1 ];
        userEmail = (userEmail == NULL ? nil : userEmail);
        [HelpshiftCore setName:userName andEmail:userEmail];
    }];
}

- (void) leaveBreadCrumb:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSString *breadCrumb = [command.arguments objectAtIndex:0];
        [HelpshiftSupport leaveBreadCrumb:breadCrumb];
    }];
}

- (void) clearBreadCrumbs:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        [HelpshiftSupport clearBreadCrumbs];
    }];
}

-(void)login :(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSString *identifier = [command argumentAtIndex:0 ];
        NSString *name = [command argumentAtIndex:1 ];
        NSString *email = [command argumentAtIndex:2 ];
        [HelpshiftCore loginWithIdentifier:identifier withName:name andEmail:email];
    }];
}

-(void)logout :(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        [HelpshiftCore logout];
    }];
}

- (void) getNotificationCount:(CDVInvokedUrlCommand*)command {
    NSString *isAsync = [command argumentAtIndex:0 ];
    NSInteger count = [HelpshiftSupport getNotificationCountFromRemote:isAsync];
    CDVPluginResult *result;
    NSString *countStr = [NSString stringWithFormat: @"%ld", count];
    result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:countStr];
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void) registerDeviceToken:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        [HelpshiftCore registerDeviceToken:[command argumentAtIndex:0]];
    }];
}

- (void) handleRemoteNotification:(CDVInvokedUrlCommand*)command {
    NSDictionary *notification = [command argumentAtIndex:0];
    [HelpshiftCore handleRemoteNotification:notification withController:self.viewController];
}

- (void) handleLocalNotification:(CDVInvokedUrlCommand*)command {
    UILocalNotification *notification = [command argumentAtIndex:0];
    [HelpshiftCore handleLocalNotification:notification withController:self.viewController];
}

- (void) pauseDisplayOfInAppNotification:(CDVInvokedUrlCommand*)command {
    BOOL pauseInApp = [command argumentAtIndex:0];
    [HelpshiftSupport pauseDisplayOfInAppNotification:pauseInApp];
}

-(void) registerSessionDelegates :(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        [[HelpshiftSupport sharedInstance] setDelegate:self];
    }];
}

-(void) registerConversationDelegates :(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        [[HelpshiftSupport sharedInstance] setDelegate:self];
    }];
}

- (void) setSDKLanguage:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSString *languageCode = [command argumentAtIndex:0 ];
        [HelpshiftSupport setSDKLanguage:languageCode];
    }];
}

- (void) showAlertToRateAppWithURL : (CDVInvokedUrlCommand*) command {
    NSString *urlString = [command argumentAtIndex:0  withDefault:nil];
    [HelpshiftSupport showAlertToRateAppWithURL:urlString
                     withCompletionBlock:^(HelpshiftSupportAlertToRateAppAction action) {
            NSString *rateAppAction = @"";
            switch(action) {
            case HelpshiftSupportAlertToRateAppActionClose:
                rateAppAction = @"HelpshiftSupportAlertToRateAppActionCloseE";
                break;
            case HelpshiftSupportAlertToRateAppActionFeedback:
                rateAppAction = @"HelpshiftSupportAlertToRateAppActionFeedback";
                break;
            case HelpshiftSupportAlertToRateAppActionSuccess:
                rateAppAction = @"HelpshiftSupportAlertToRateAppActionSuccess";
                break;
            case HelpshiftSupportAlertToRateAppActionFail:
                rateAppAction = @"HelpshiftSupportAlertToRateAppActionFail";
            default:
                break;
            }
            NSString *jsString = [NSString stringWithFormat:@"HelpshiftPlugin._nativeAppRateResponseCall(\"%s\");", [rateAppAction UTF8String]];
            [self.commandDelegate evalJs:jsString];
        }];
}

- (void) didReceiveNotificationCount:(NSInteger)count{
    [self.commandDelegate runInBackground:^{
        NSString *jsString = nil;
        jsString = [NSString stringWithFormat:@"HelpshiftPlugin._nativeNotificationCall(\"%ld\");", (long)count];
        [self.commandDelegate evalJs:jsString];
    }];
}

- (void) didReceiveInAppNotificationWithMessageCount:(NSInteger)count{
    [self.commandDelegate runInBackground:^{
        NSString *jsString = nil;
        jsString = [NSString stringWithFormat:@"HelpshiftPlugin._nativeInAppNotificationCall(\"%ld\");", (long)count];
        [self.commandDelegate evalJs:jsString];
    }];
}

- (void) helpshiftSupportSessionHasBegun {
    [self.commandDelegate runInBackground:^{
        NSString *jsString = nil;
        jsString = [NSString stringWithFormat:@"HelpshiftPlugin._nativeSessionBeganCall();"];
        [self.commandDelegate evalJs:jsString];
    }];
}

- (void) helpshiftSupportSessionHasEnded {
    [self.commandDelegate runInBackground:^{
        NSString *jsString = nil;
        jsString = [NSString stringWithFormat:@"HelpshiftPlugin._nativeSessionEndedCall();"];
        [self.commandDelegate evalJs:jsString];
    }];
}

- (void) newConversationStartedWithMessage:(NSString *)newConversationMessage {
    [self.commandDelegate runInBackground:^{
        NSString *jsString = nil;
        jsString = [NSString stringWithFormat:@"HelpshiftPlugin._nativeNewConversationStartedWithMessageCall(\"%s\");", [newConversationMessage UTF8String]];
        [self.commandDelegate evalJs:jsString];
    }];
}

- (void) userRepliedToConversationWithMessage:(NSString *)newMessage {
    [self.commandDelegate runInBackground:^{
        NSString *jsString = nil;
        jsString = [NSString stringWithFormat:@"HelpshiftPlugin._nativeUserRepliedToConversationWithMessageCall(\"%s\");", [newMessage UTF8String]];
        [self.commandDelegate evalJs:jsString];
    }];
}

- (void) userCompletedCustomerSatisfactionSurvey:(NSInteger)rating withFeedback:(NSString *)feedback{
    [self.commandDelegate runInBackground:^{
        NSString *jsString = nil;
        jsString = [NSString stringWithFormat:@"HelpshiftPlugin._nativeUserCompletedCustomerSatisfactionSurveyCall(\"%ld\",\"%s\");", (long)rating,[feedback UTF8String]];
        [self.commandDelegate evalJs:jsString];
    }];
}

- (BOOL) displayAttachmentFileAtLocation:(NSURL *)fileLocation onViewController:(UIViewController *)parentViewController{
    NSString *format = [fileLocation pathExtension];
    if(_fileFormats){
        if ([_fileFormats containsObject:format.lowercaseString]) {
            [self.commandDelegate runInBackground:^{
                NSString *jsString = nil;
                jsString = [NSString stringWithFormat:@"HelpshiftPlugin._nativeDisplayAttachmentFileAtLocation(\"%@\");", [fileLocation absoluteString]];
                [self.commandDelegate evalJs:jsString];
            }];
            return YES;
        } else {
            return NO;
        }
    }
    return NO;
}

#pragma mark Dynamic Forms

- (void) showDynamicForm:(CDVInvokedUrlCommand*)command {
    NSString *title = [command argumentAtIndex:1];
    NSArray *flowsData = [command argumentAtIndex:0];
    NSDictionary *configOptions = nil;
    if([command.arguments count] > 2) {
         configOptions = [command argumentAtIndex:2];
    }
    [HelpshiftSupport showDynamicFormOnViewController:self.viewController withTitle:title andFlowsData:flowsData withConfigOptions:configOptions];

}

- (void) logE:(CDVInvokedUrlCommand*)command {
    NSString *tag = [command argumentAtIndex:0 ];
    NSString *log = [command argumentAtIndex:1 ];
    NSLog(@"%@ %s %@ %s", @"Error::", [tag UTF8String], @"::", [log UTF8String]);
}

- (void) logW:(CDVInvokedUrlCommand*)command {
    NSString *tag = [command argumentAtIndex:0 ];
    NSString *log = [command argumentAtIndex:1 ];
    NSLog(@"%@ %s %@ %s", @"Warn::", [tag UTF8String], @"::", [log UTF8String]);
}

- (void) logI:(CDVInvokedUrlCommand*)command {
    NSString *tag = [command argumentAtIndex:0 ];
    NSString *log = [command argumentAtIndex:1 ];
    NSLog(@"%@ %s %@ %s", @"Info::", [tag UTF8String], @"::", [log UTF8String]);
}

- (void) logV:(CDVInvokedUrlCommand*)command {
    NSString *tag = [command argumentAtIndex:0 ];
    NSString *log = [command argumentAtIndex:1 ];
    NSLog(@"%@ %s %@ %s", @"Verbose::", [tag UTF8String], @"::", [log UTF8String]);
}

- (void) logD:(CDVInvokedUrlCommand*)command {
    NSString *tag = [command argumentAtIndex:0 ];
    NSString *log = [command argumentAtIndex:1 ];
    NSLog(@"%@ %s %@ %s", @"Debug::", [tag UTF8String], @"::", [log UTF8String]);
}

@end