//
//  GBTagListView.m
//  升级版流式标签支持点击
//
//  Created by 张国兵 on 15/8/16.
//  Copyright (c) 2015年 zhangguobing. All rights reserved.
//

#import "GBTagListView.h"
#define HORIZONTAL_PADDING 4.0f
#define VERTICAL_PADDING   3.0f
//#define LABEL_MARGIN       10.0f
#define BOTTOM_MARGIN      10.0f
#define KBtnTag            1000
#define R_G_B_16(rgbValue)\
\
[UIColor colorWithRed:((float)((rgbValue & 0xFF0000) >> 16))/255.0 \
green:((float)((rgbValue & 0xFF00) >> 8))/255.0 \
blue:((float)(rgbValue & 0xFF))/255.0 \
alpha:1.0]
@interface GBTagListView(){
    
    CGFloat _KTagMargin;//左右tag之间的间距
    CGFloat _KBottomMargin;//上下tag之间的间距
    NSInteger _kSelectNum;//实际选中的标签数
    UIButton*_tempBtn;//临时保存对象

}
@end
@implementation GBTagListView
-(id)initWithFrame:(CGRect)frame{
    
    self = [super initWithFrame:frame];
    if (self) {
        _kSelectNum=0;
        totalHeight=0;
        self.frame=frame;
        _tagArr=[[NSMutableArray alloc]init];
        /**默认是多选模式 */
        self.isSingleSelect=NO;
        _tagFontSize = 14.0f;
        _LABEL_MARGIN = 14.0f;
        _tagHight = Value(36);
        _lineTwoLeftMagin = 14.0f;
        
        _horizontalPadding = 7.0f;
    }
    return self;
    
}

- (void)setLABEL_MARGIN:(CGFloat)LABEL_MARGIN
{
    _LABEL_MARGIN = LABEL_MARGIN;
    
    if(LABEL_MARGIN ==0)
    {
        _LABEL_MARGIN = 10.0f;
    }
}






-(void)setTagWithTagArray:(NSArray*)arr{
    
//    [_tagArr removeAllObjects];
    
    previousFrame = CGRectZero;
    [_tagArr addObjectsFromArray:arr];
    
    
    [arr enumerateObjectsUsingBlock:^(id str1, NSUInteger idx, BOOL *stop) {
    
        NSString * str = @"";
        if([str1 isKindOfClass:[NSString class]])
        {
            str = str1;
            
        }else  if([str1 isKindOfClass:[NSDictionary class]])
        {
            str = [str1 objectForKey:@"tag_name"];
        }
        
        UIButton*tagBtn=[UIButton buttonWithType:UIButtonTypeCustom];
        tagBtn.frame=CGRectZero;
        tagBtn.backgroundColor= _signalTagColor?:[UIColor whiteColor];
        
        if(_canTouch){
            tagBtn.userInteractionEnabled=YES;
            
        }else{
            tagBtn.userInteractionEnabled=NO;
        }
        
        
        if(self.nomalBordColor)
        {
            tagBtn.layer.borderColor = self.nomalBordColor.CGColor;
            tagBtn.layer.borderWidth = 1;
        }
        
        [tagBtn setTitleColor:self.titleTagNomalColor?:[UIColor colorWithHexString:@"#646464"] forState:UIControlStateNormal];
        [tagBtn setTitleColor:self.titleTagHighColor?:[UIColor colorWithHexString:@"#646464"] forState:UIControlStateSelected];
        
        
        
        if(_cornerRadius > 0)
        {
            tagBtn.layer.cornerRadius = _cornerRadius;
            tagBtn.layer.masksToBounds = YES;
        }
        
        
        tagBtn.titleLabel.font = YSSSystemFont(_tagFontSize);
        [tagBtn addTarget:self action:@selector(tagBtnClick:) forControlEvents:UIControlEventTouchUpInside];
        [tagBtn setTitle:str forState:UIControlStateNormal];
        tagBtn.tag = KBtnTag+idx;
        tagBtn.clipsToBounds=YES;
        NSDictionary *attrs = @{NSFontAttributeName : [UIFont boldSystemFontOfSize:14]};
        CGSize Size_str=[str sizeWithAttributes:attrs];
        if(_tagHight ==0)
        {
            Size_str.height = Value(36);
        }else
        {
            Size_str.height = _tagHight;
        }
        
        Size_str.width += _horizontalPadding * 3;
//        Size_str.height += VERTICAL_PADDING*3;
        CGRect newRect = CGRectZero;

        if(_KTagMargin&&_KBottomMargin){
            
            if (previousFrame.origin.x + previousFrame.size.width + Size_str.width + _KTagMargin > self.bounds.size.width) {
                
                newRect.origin = CGPointMake(_KTagMargin, previousFrame.origin.y + Size_str.height + _KBottomMargin);
                totalHeight +=Size_str.height + _KBottomMargin;
            }
            else {
                newRect.origin = CGPointMake(previousFrame.origin.x + previousFrame.size.width + _KTagMargin, previousFrame.origin.y);
                
            }
            [self setHight:self andHight:totalHeight+Size_str.height + _KBottomMargin];

            
        }else{
        if (previousFrame.origin.x + previousFrame.size.width + Size_str.width + _LABEL_MARGIN > self.bounds.size.width) {
            
            newRect.origin = CGPointMake(_lineTwoLeftMagin, previousFrame.origin.y + Size_str.height + BOTTOM_MARGIN);
            totalHeight +=Size_str.height + BOTTOM_MARGIN;
        }
        else {
            newRect.origin = CGPointMake(previousFrame.origin.x + previousFrame.size.width + _LABEL_MARGIN, previousFrame.origin.y);
            
        }
        [self setHight:self andHight:totalHeight+Size_str.height + BOTTOM_MARGIN];
        }
        newRect.size = Size_str;
        [tagBtn setFrame:newRect];
        previousFrame=tagBtn.frame;
        [self setHight:self andHight:totalHeight+Size_str.height + BOTTOM_MARGIN];
        [self addSubview:tagBtn];
    }];
   
    self.backgroundColor=_GBbackgroundColor?:[UIColor whiteColor];

}


// 设置按钮可不可以点击
- (void)configAllBtnsCanClick:(NSArray *)tagList
{
    [_tagArr removeAllObjects];
    [_tagArr addObjectsFromArray:tagList];

    
    id  tagArrLastObject = _tagArr.lastObject;
    if(![tagArrLastObject isKindOfClass:[NSDictionary class]])
    {
        return;
    }
    
    
    for (NSInteger i =0 ; i <_tagArr.count;i ++) {
        
        UIButton * tagBtn = [self viewWithTag:KBtnTag+i];
        NSDictionary * itemDic = _tagArr[i];
        
        NSString * is_valide = [YSSCommonTool parseString:itemDic[@"is_validate"]];
        if([is_valide isEqualToString:@"1"])
        {
            [tagBtn setTitleColor:R_G_B_16(0x818181) forState:0];
            tagBtn.userInteractionEnabled = YES;
            
        }else if ([is_valide isEqualToString:@"2"])
        {
            tagBtn.selected = YES;
            tagBtn.backgroundColor = self.selectTagColor?:[UIColor orangeColor];
            tagBtn.layer.borderColor = self.selectBordColor.CGColor;
        }else
        {
            [tagBtn setTitleColor:[UIColor colorWithWhite:200/255.0 alpha:1.0] forState:0];
            tagBtn.layer.borderColor = [UIColor colorWithWhite:200/255.0 alpha:1.0].CGColor;
            tagBtn.userInteractionEnabled = NO;
        }
    }
    
}




#pragma mark-改变控件高度
- (void)setHight:(UIView *)view andHight:(CGFloat)hight
{
    CGRect tempFrame = view.frame;
    tempFrame.size.height = hight;
    view.frame = tempFrame;
}
-(void)tagBtnClick:(UIButton*)button{
    if(_isSingleSelect){
        if(button.selected){
            
            if(_canCanselSelect) return;
            
            button.selected=!button.selected;
            
        }else{
            
            _tempBtn.selected=NO;
            _tempBtn.backgroundColor = self.selectTagColor?:[UIColor orangeColor];
            _tempBtn.backgroundColor=self.signalTagColor?:[UIColor whiteColor];
            _tempBtn.layer.borderColor = self.nomalBordColor.CGColor;
            
            button.selected=YES;
            _tempBtn=button;
        }
    }else{
        
        button.selected=!button.selected;
    }
    
    if(button.selected==YES){
        button.backgroundColor = self.selectTagColor?:[UIColor orangeColor];
        button.layer.borderColor = self.selectBordColor.CGColor;
        
    }else if (button.selected==NO){
        button.backgroundColor=self.signalTagColor?:[UIColor whiteColor];
        button.layer.borderColor = self.nomalBordColor.CGColor;
    }
    
    [self didSelectItems];
    
}
-(void)didSelectItems{

    NSMutableArray*arr=[[NSMutableArray alloc]init];
    
    for(UIView*view in self.subviews){

        if([view isKindOfClass:[UIButton class]]){

            UIButton*tempBtn=(UIButton*)view;
            tempBtn.enabled=YES;
            if (tempBtn.selected==YES) {
                [arr addObject:_tagArr[tempBtn.tag-KBtnTag]];
                _kSelectNum=arr.count;
            }
        }
    }
    if(_kSelectNum==self.canTouchNum){
        
        for(UIView*view in self.subviews){

            UIButton*tempBtn=(UIButton*)view;

         if (tempBtn.selected==YES) {
             tempBtn.enabled=YES;
             
         }else{
             tempBtn.enabled=NO;
             
         }
    }
    }
    self.didselectItemBlock(arr);
    
    
}
-(void)setMarginBetweenTagLabel:(CGFloat)Margin AndBottomMargin:(CGFloat)BottomMargin{
    
    _KTagMargin=Margin;
    _KBottomMargin=BottomMargin;

}

@end
