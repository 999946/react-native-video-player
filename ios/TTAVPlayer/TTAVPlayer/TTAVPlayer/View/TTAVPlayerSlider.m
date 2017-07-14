//
//  TTAVPlayerSlider.m
//  Multimedia
//
//  Created by dylan.tang on 17/2/6.
//  Copyright © 2017年 dylan.tang. All rights reserved.
//

#import "TTAVPlayerSlider.h"

@interface TTAVPlayerSlider ()

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
    
    [self setMinimumTrackTintColor:[UIColor colorWithWhite:1.0f alpha:1.0f]];
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
    rect.origin.y -= 10.0f;
    rect.size.height += 20.0f;
    return CGRectInset ([super thumbRectForBounds:bounds trackRect:rect value:value], 10 ,10);
    
}

-(UIImage*) originImage:(UIImage*)image scaleToSize:(CGSize)size{
    UIGraphicsBeginImageContext(size);//size为CGSize类型，即你所需要的图片尺寸
    
    [image drawInRect:CGRectMake(0,0, size.width, size.height)];
    
    UIImage* scaledImage =UIGraphicsGetImageFromCurrentImageContext();
    
    UIGraphicsEndImageContext();
    
    return scaledImage;
}

@end
