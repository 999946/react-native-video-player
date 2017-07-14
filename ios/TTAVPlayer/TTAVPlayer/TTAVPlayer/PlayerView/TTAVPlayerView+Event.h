//
//  TTAVPlayerView+Action.h
//  Multimedia
//
//  Created by dylan.tang on 17/2/7.
//  Copyright © 2017年 dylan.tang. All rights reserved.
//

#import "TTAVPlayerView.h"

@interface TTAVPlayerView (Event)

// click buttons && videoView

- (void)clickPlayBtn;

- (void)clickFullScreenBtn;

- (void)clickCloseBtn;

- (void)clickBackBtn;

- (void)tapVideoView;

// Slider

- (void)seek:(UISlider*)slider;

- (void)refreshProgressSlider;

- (void)pauseRefreshProgressSlider;

- (void)resumeRefreshProgressSlider:(UISlider*)slider;



@end
