//
//  GBTagListView.h
//  升级版流式标签支持点击
//
//  Created by 张国兵 on 15/8/16.
//  Copyright (c) 2015年 zhangguobing. All rights reserved.
//


/*
 
 GBTagListView *tagList=[[GBTagListView alloc]initWithFrame:CGRectMake(0, 80, ScreenWidth, 0)];
 tagList.canTouch=YES;
 tagList.canTouchNum =0;
 tagList.isSingleSelect = YES;
 tagList.signalTagColor=[UIColor lightGrayColor];
 tagList.selectTagColor = [UIColor lightGrayColor];
 
 [tagList setTagWithTagArray:strArray];
 [tagList setDidselectItemBlock:^(NSArray *arr) {
 NSLog(@"选中的标签%@",arr);
 
 }];
 */



#import <UIKit/UIKit.h>


@interface GBTagListView : UIView{
    CGRect previousFrame ;
    int totalHeight ;
    NSMutableArray*_tagArr;
    
}

@property(nonatomic,assign) CGFloat LABEL_MARGIN;  /**< 水平间距 */

@property(nonatomic,assign) CGFloat cornerRadius;

@property(nonatomic,assign) CGFloat tagHight;

@property(nonatomic,assign) CGFloat tagFontSize;

@property(nonatomic,assign) CGFloat lineTwoLeftMagin;//fix  左间距

@property (nonatomic, assign) CGFloat horizontalPadding;

/**
 * 整个view的背景色
 */
@property(nonatomic,strong) UIColor* GBbackgroundColor;
/**
 *  设置单一颜色
 */
@property(nonatomic,strong) UIColor * signalTagColor;
//选中了之后的颜色
@property(nonatomic,strong) UIColor * selectTagColor;

//边框颜色
@property(nonatomic,strong) UIColor * nomalBordColor;
@property(nonatomic,strong) UIColor * selectBordColor;

//文字颜色
@property(nonatomic,strong) UIColor * titleTagNomalColor;
@property(nonatomic,strong) UIColor * titleTagHighColor;

/**
 *  回调统计选中tag
 */
@property(nonatomic,copy)void (^didselectItemBlock)(NSArray*arr);

/**
 *  是否可点击
 */
@property(nonatomic) BOOL canTouch;



/**

 *  限制点击个数
 *  0->不限制
 *  不设置此属性默认不限制
 */
@property(nonatomic) NSInteger canTouchNum;

/** 单选模式,该属性的优先级要高于canTouchNum */

@property(nonatomic) BOOL isSingleSelect;

//单选模式 下，是否可以取消选中的tag
@property(nonatomic,assign) BOOL canCanselSelect;


/**
 *  标签文本赋值
 */
-(void)setTagWithTagArray:(NSArray*)arr;

//修改按钮的状态
- (void)configAllBtnsCanClick:(NSArray *)tagList;

/**
 *  设置tag之间的距离
 *
 *  @param Margin 间距
 *  @param BottomMargin 底部距离
 */
-(void)setMarginBetweenTagLabel:(CGFloat)Margin AndBottomMargin:(CGFloat)BottomMargin;

@end
