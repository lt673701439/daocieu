//
//  UIImageView+YSSExtention.m
//  B2B_liketry
//
//  Created by GentleZ on 2017/10/19.
//  Copyright © 2017年 GentleZ. All rights reserved.
//

#import "UIImageView+YSSExtention.h"

@implementation UIImageView (YSSExtention)

- (void)setHttpImageWithURL:(NSURL *)url
{
    [self sd_setImageWithURL:url placeholderImage:YSSPlaceholderImage options:SDWebImageDelayPlaceholder completed:^(UIImage * _Nullable image, NSError * _Nullable error, SDImageCacheType cacheType, NSURL * _Nullable imageURL) {
        self.contentMode = UIViewContentModeScaleAspectFit;
    }];
}

@end
