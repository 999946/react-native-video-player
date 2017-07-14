//
//  RCTVideoPlayerManager.m
//  RNVideoPlayer
//
//  Created by lzw on 2017/6/28.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import "RCTVideoPlayerManager.h"
#import "RCTVideoPlayer.h"

@implementation RCTVideoPlayerManager

RCT_EXPORT_MODULE();

@synthesize bridge = _bridge;

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
    return dispatch_get_main_queue();
}

RCT_EXPORT_VIEW_PROPERTY(src, NSString)
RCT_EXPORT_VIEW_PROPERTY(title, NSString)
RCT_EXPORT_VIEW_PROPERTY(placeholderImage, NSString)

@end
