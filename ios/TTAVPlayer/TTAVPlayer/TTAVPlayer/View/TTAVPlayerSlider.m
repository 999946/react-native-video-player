//
//  TTAVPlayerSlider.m
//  Multimedia
//
//  Created by dylan.tang on 17/2/6.
//  Copyright © 2017年 dylan.tang. All rights reserved.
//

#import "TTAVPlayerSlider.h"

#define thumbBound_x 10
#define thumbBound_y 20


@interface TTAVPlayerSlider ()
{
    CGRect lastBounds;
}
@property (nonatomic,strong) UIProgressView *loadingProgressView;//缓冲

@end

@implementation TTAVPlayerSlider

- (instancetype)init{
    self = [super init];
    if (self){
        [self initUI];
    }
    return self;
}

- (void)initUI{
    self.backgroundColor = [UIColor clearColor];
    
    _loadingProgressView = [[UIProgressView alloc]initWithFrame:CGRectZero];
    _loadingProgressView.userInteractionEnabled = NO;
    [_loadingProgressView setContentMode:UIViewContentModeCenter];
    
    _loadingProgressView.progress = 0.0f;
    [self addSubview:_loadingProgressView];
    
    [_loadingProgressView setProgressTintColor:[UIColor colorWithWhite:1.0f alpha:0.49f]];
    [_loadingProgressView setTrackTintColor:[UIColor colorWithWhite:1.0f alpha:0.3f]];
    
    // 已播放进度条颜色
    [self setMinimumTrackTintColor:[UIColor colorWithRed:17/255.0 green:178/255.0 blue:245/255.0 alpha:1]];
    [self setMaximumTrackTintColor:[UIColor clearColor]];
    UIImage *image = [self originImage:[UIImage imageNamed:@"TTAVPlayer.bundle/multimedia_slider"] scaleToSize:CGSizeMake(20, 20)];
    [self setThumbImage:image forState:UIControlStateNormal];
}

- (void)layoutSubviews{
    [super layoutSubviews];
    self.loadingProgressView.frame = CGRectMake(3.0f, (self.height - self.loadingProgressView.height)/2, self.width - 3.0f, self.loadingProgressView.height);
}

- (void)refreshLoadingProgress:(float)value{
    self.loadingProgressView.progress = value;
}

- (CGRect)thumbRectForBounds:(CGRect)bounds trackRect:(CGRect)rect value:(float)value{
    rect.origin.x -= 10.0f;
    rect.size.width += 20.f;
    CGRect result = [super thumbRectForBounds:bounds trackRect:rect value:value];
    lastBounds = result;
    return result;
}

- (UIView *)hitTest:(CGPoint)point withEvent:(UIEvent *)event{
    
    UIView *result = [super hitTest:point withEvent:event];
    if ((point.y >= -thumbBound_y) && (point.y < lastBounds.size.height + thumbBound_y)) {
        float value = 0.0;
        value = point.x - self.bounds.origin.x;
        value = value/self.bounds.size.width;
        
        value = value < 0? 0 : value;
        value = value > 1? 1: value;
        
        value = value * (self.maximumValue - self.minimumValue) + self.minimumValue;
        [self setValue:value animated:YES];
    }
    return result;
    
}

- (BOOL)pointInside:(CGPoint)point withEvent:(UIEvent *)event{
    BOOL result = [super pointInside:point withEvent:event];
    if (!result && point.y > -10) {
        if ((point.x >= lastBounds.origin.x - thumbBound_x) && (point.x <= (lastBounds.origin.x + lastBounds.size.width + thumbBound_x)) && (point.y < (lastBounds.size.height + thumbBound_y))) {
            result = YES;
        }
    }
    return result;
}

-(UIImage*) originImage:(UIImage*)image scaleToSize:(CGSize)size{
    UIGraphicsBeginImageContext(size);//size为CGSize类型，即你所需要的图片尺寸
    
    [image drawInRect:CGRectMake(0,0, size.width, size.height)];
    
    UIImage* scaledImage =UIGraphicsGetImageFromCurrentImageContext();
    
    UIGraphicsEndImageContext();
    
    return scaledImage;
}

@end
