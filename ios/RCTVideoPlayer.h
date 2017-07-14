//
//  RCTVideoPlayer.h
//  RNVideoPlayer
//
//  Created by lzw on 2017/7/5.
//  Copyright © 2017年 Facebook. All rights reserved.
//
#import "RCTView.h"
#import <Foundation/Foundation.h>
#import "TTAVPlayer.h"

@class RCTEventDispatcher;

@interface RCTVideoPlayer : TTAVPlayerView<TTAVPlayerViewDelegate>

- (instancetype)initWithEventDispatcher:(RCTEventDispatcher *)eventDispatcher NS_DESIGNATED_INITIALIZER;


@end
