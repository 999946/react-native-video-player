//
//  RCTVideoPlayerManager.m
//  RNVideoPlayer
//
//  Created by lzw on 2017/6/28.
//  Copyright © 2017年 Facebook. All rights reserved.
//
#if __has_include(<React/RCTUIManager.h>)
#import <React/RCTViewManager.h>
#else
#import "RCTUIManager.h"
#endif

#import "RCTVideoPlayerManager.h"
#import "RCTVideoPlayer.h"

@implementation RCTVideoPlayerManager

RCT_EXPORT_MODULE();

- (instancetype)init
{
    self = [super init];
    if (self) {
    }
    return self;
}

- (UIView *)view
{
    return [[RCTVideoPlayer alloc] initWithEventDispatcher:self.bridge.eventDispatcher];
}

- (NSArray *)customDirectEventTypes
{
    return @[
             @"onBack",
             @"onPlay",
             @"onPause",
             @"onEnd",
             @"onError"
             ];
}

- (dispatch_queue_t)methodQueue
{
    return self.bridge.uiManager.methodQueue;
}

RCT_EXPORT_VIEW_PROPERTY(src, NSString)
RCT_EXPORT_VIEW_PROPERTY(title, NSString)
RCT_EXPORT_VIEW_PROPERTY(placeholderImage, NSString)

RCT_EXPORT_METHOD(play: (nonnull NSNumber *)reactTag) {
    [self.bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
        UIView *view = viewRegistry[reactTag];
        if (![view isKindOfClass:[RCTVideoPlayer class]]) {
            RCTLog(@"expecting UIView, got: %@", view);
        }
        else {
            RCTVideoPlayer *player = (RCTVideoPlayer *)view;
            [player play];
        }
    }];
}

RCT_EXPORT_METHOD(pause: (nonnull NSNumber *)reactTag) {
    [self.bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
        UIView *view = viewRegistry[reactTag];
        if (![view isKindOfClass:[RCTVideoPlayer class]]) {
            RCTLog(@"expecting UIView, got: %@", view);
        }
        else {
            RCTVideoPlayer *player = (RCTVideoPlayer *)view;
            [player pause];
        }
    }];
}

@end
