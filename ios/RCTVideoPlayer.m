//
//  RCTVideoPlayer.m
//  RNVideoPlayer
//
//  Created by lzw on 2017/7/5.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import "RCTVideoPlayer.h"

#if __has_include(<React/RCTConvert.h>)
#import <React/RCTConvert.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTEventDispatcher.h>
#import <React/UIView+React.h>
#else
#import "RCTConvert.h"
#import "RCTBridgeModule.h"
#import "RCTEventDispatcher.h"
#import "UIView+React.h"
#endif

@implementation RCTVideoPlayer {
    RCTEventDispatcher *_eventDispatcher;
}

- (id)initWithEventDispatcher:(RCTEventDispatcher *)eventDispatcher
{
    self = [super initWithFrame:CGRectZero withViewMode:TTAVPlayerViewNormalMode];
    
    [super setDelegate:self];
    if (self) {
        _eventDispatcher = eventDispatcher;
    }
    
    return self;
}

- (void)setSrc:(NSString *)src {
    NSLog(@"set src -> %@", src);
    
    if(!STRING_IS_BLANK(super.videoUrl) && super.videoUrl != src) {
        NSLog(@"-------SRC 不同");
        [super setVideoUrl:src];
        [self reloadVideo];
    } else {
        [super setVideoUrl:src];
    }
}

- (void)setTitle:(NSString *)title {
    NSLog(@"set title -> %@", title);
    [super setVideoTitle:title];
}

- (void)setPlaceholderImage:(NSString *)placeholderImage {
    NSLog(@"set placeholderImage -> %@", placeholderImage);
    [super setPlaceholderImage:placeholderImage];
}

// Video LifeCycle
- (void)videoUIDidLoad {
    NSLog(@"----------video did loaded -----------");
};

- (void)videoDidPlay {
    if(_eventDispatcher){
        [_eventDispatcher sendInputEventWithName:@"onPlay" body:@{@"target": self.reactTag}];
    }
}

- (void)videoDidPause {
    if(_eventDispatcher){
        [_eventDispatcher sendInputEventWithName:@"onPause" body:@{@"target": self.reactTag}];
    }
}

- (void)videoDidEndPlay:(TTAVPlayerView*)playerView {
    if(_eventDispatcher){
        [_eventDispatcher sendInputEventWithName:@"onEnd" body:@{@"target": self.reactTag}];
    }
};

- (void)videoDidPrepared {}

- (void)backButtonGetTap:(BOOL)isFullScreen {
    
    if(_eventDispatcher && !isFullScreen){
        [_eventDispatcher sendInputEventWithName:@"onBack" body:@{@"target": self.reactTag}];
    }
}

- (void)onVideoPlayError:(NSError*)error {
    if(_eventDispatcher){
        [_eventDispatcher sendInputEventWithName:@"onError" body:@{@"target": self.reactTag}];
    }
}

@end

