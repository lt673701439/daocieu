package com.liketry.interaction.benison.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.liketry.interaction.benison.model.BenisonTemplate;
import com.liketry.interaction.benison.model.FontText;
import com.liketry.interaction.benison.model.Order;

 
/**
 * 作图类
 * 
 * @author pengyy
 */
public class MakeImgUtils {
	
	private static final Logger log = LoggerFactory.getLogger(MakeImgUtils.class);
	
	private static MakeImgUtils instance = null;
	
	public static synchronized MakeImgUtils getInstance(){
		
		if (instance == null){
			instance = new MakeImgUtils();
		}
		return instance;
	}
	
	/**
	 * 根据模板格式制作图片
	 * @param benisonTemplate 模板对象
	 * @param writeName 落款人
	 * @param blessName 祝福人
	 * @param path 存储路径
	 * @param path 图片规定宽度
	 * @return 
	 */
	public static Map<String,String> makeImg(BenisonTemplate benisonTemplate,String writeName,String blessName,String path,int width){  
	
		Map<String,String> map = new HashMap<String,String>();
		String newImgUrl = null;
	
		 try {  
			 
			//设置抬头的字体
			FontText titleText = new FontText(blessName,benisonTemplate.getTitleColour(), Integer.valueOf(benisonTemplate.getTitleSize()), benisonTemplate.getTitleType());
			//设置主体内容的字体
			FontText bodyText = new FontText(benisonTemplate.getRuleContent(),benisonTemplate.getBodyColour(), Integer.valueOf(benisonTemplate.getBodySize()), benisonTemplate.getBodyType());
			//设置落款内容的字体
			FontText tailText = new FontText(writeName,benisonTemplate.getTailColour(), Integer.valueOf(benisonTemplate.getTailSize()), benisonTemplate.getTailType());
			
			//获取文件格式
			String fileFormat =benisonTemplate.getImgUrl().substring(benisonTemplate.getImgUrl().indexOf(".")+1,benisonTemplate.getImgUrl().length()); 
			log.info("<=========fileFormat:{}=========>",fileFormat);
			String fileName = "order"+UUID.randomUUID().toString().replace("-", "")+"."+fileFormat;//新的文件名
			Boolean dirFlag = createDir(path);
			if(!dirFlag){
				map.put("result", "false");
				map.put("msg", "制作背景图时创建文件夹失败");
				return map;
			}
			
			newImgUrl = copyFile(PropertiesUtils.getInstance().getValue("default_upload_filepath")+benisonTemplate.getImgUrl(),path+fileName);
			if(StringUtils.isEmpty(newImgUrl)){
				map.put("result", "false");
				map.put("msg", "制作背景图时拷贝文件失败");
				return map;
			}
			
			//制作图片
			File file = new File(newImgUrl);
			
			BufferedImage buffImg =ImageIO.read(file);
	        //创建画笔
	        Graphics2D g = buffImg.createGraphics();
	        //写抬头
	        g.setColor(getColor(titleText.getWm_text_color()));
	        g.setBackground(Color.white);
	        g.setFont(getFont(titleText));
	        g.drawString(titleText.getText(), Integer.valueOf(benisonTemplate.getTitleX()), Integer.valueOf(benisonTemplate.getTitleY()));//在图片上写文字:内容,X坐标，Y坐标
	        //写主体
	        g.setColor(getColor(bodyText.getWm_text_color()));
	        g.setBackground(Color.white);
	        g.setFont(getFont(bodyText));
	        //主体文字自动换行处理
	        newline(buffImg,bodyText,g,benisonTemplate,writeName,blessName);
	        //写落款
	        g.setColor(getColor(tailText.getWm_text_color()));
	        g.setBackground(Color.white);
	        g.setFont(getFont(tailText));
	        g.drawString(tailText.getText(), Integer.valueOf(benisonTemplate.getTailX()), Integer.valueOf(benisonTemplate.getTailY()));//在图片上写文字:内容,X坐标，Y坐标
	        //关闭画笔
	        g.dispose();
	
	        FileOutputStream out = new FileOutputStream(newImgUrl);
	        
	        //描边
			if(!StringUtils.isEmpty(benisonTemplate.getStrokeFigure())){
				log.info("<======开始进行描边操作，描边图url:{}===========>",benisonTemplate.getStrokeFigure());
				String realPath = PropertiesUtils.getInstance().getValue("default_upload_filepath");
				File boomFile = new File(realPath+benisonTemplate.getStrokeFigure()); //描边图
				try {
					buffImg = watermark(boomFile, buffImg, Integer.parseInt(benisonTemplate.getStrokeX()), 
							Integer.parseInt(benisonTemplate.getStrokeY()), benisonTemplate.getStrokeAlpha());
				} catch (Exception e) {
					log.info("<====描边错误,模板ID:{},错误信息:{}=======>",benisonTemplate.getTemplateId(),e);
				} 
			}
			
			 //固定缩放
	        BufferedImage inputbig = zoomPic(buffImg,width);
	        
	        //创键编码器，用于编码内存中的图象数据。            
	        ImageIO.write(inputbig,fileFormat,out);
	        out.flush();
	        out.close();
	        
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IIOException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
		 
		 	map.put("result", "true");
			map.put("msg", newImgUrl);
	        return map; 
    	}  
	
	/**
	 * 加工横条图
	 * @param 屏幕名称
	 * @param 日期
	 * @param 输出文件路径
	 * @return url
	 */
	public static Map<String,String> makeCrossbandPic(String screenName,String time,String path){
		
		 Map<String,String> map = new HashMap<String,String>();
		 String newImgUrl = null;
		
		 try {  
				 
				//设置横条内容的字体
				FontText crossbandText = new FontText(screenName,"#1b1818", 20, "方正姚体");
				
				//获取文件格式
				String fileFormat =PropertiesUtils.getInstance().getValue("crossbandPicPath").substring(
						PropertiesUtils.getInstance().getValue("crossbandPicPath").indexOf(".")+1,
						PropertiesUtils.getInstance().getValue("crossbandPicPath").length()); 
				log.info("<=========fileFormat:{}=========>",fileFormat);
				
				String fileName = "order"+UUID.randomUUID().toString().replace("-", "")+"."+fileFormat;//新的文件名
				Boolean dirFlag = createDir(path);
				if(!dirFlag){
					map.put("result", "false");
					map.put("msg", "加工横条图时创建文件夹失败");
					return map;
				}
				
				//将系统图片拷贝出来
				String rootPath = getRootPath();
				log.info("<=========SystemPath:{}=========>",rootPath);
				newImgUrl = copyFile(rootPath,path+fileName);
				if(StringUtils.isEmpty(newImgUrl)){
					map.put("result", "false");
					map.put("msg", "加工横条图时拷贝文件失败");
					return map;
				}
				
				//制作图片
				File file = new File(newImgUrl);
				
				BufferedImage buffImg =ImageIO.read(file);
		        //创建画笔
		        Graphics2D g = buffImg.createGraphics();
		        //写主体
		        g.setColor(getColor(crossbandText.getWm_text_color()));
		        g.setBackground(Color.white);
		        g.setFont(getFont(crossbandText));
		        g.drawString(screenName+" · "+time.replace("-", " . "), 28, 90);
		        //关闭画笔
		        g.dispose();
		
		        FileOutputStream out = new FileOutputStream(newImgUrl);
		        //创键编码器，用于编码内存中的图象数据。            
		        ImageIO.write(buffImg,fileFormat,out);
		        out.flush();
		        out.close();
		        
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IIOException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
		 
		 	map.put("result", "true");
			map.put("msg", newImgUrl);
	        return map; 
		
	}
	
	/**
	 * 为DIY定制订单制作图片
	 * @param Order 订单对象(包括所需的，背景图url，祝福语内容，祝福语坐标，字号，颜色)
	 * @return 
	 */
	public static Map<String,String> makeImgForCustomization(Order order,String path){  
	
		Map<String,String> map = new HashMap<String,String>();
		String newImgUrl = null;
	
		 try {  
			 
			//设置主体内容的字体
			FontText bodyText = new FontText(order.getBenisonContent(),order.getFontcolor(), Integer.parseInt(order.getFontSize()),"黑体");
			
			//获取文件格式
			String fileFormat =order.getBkimgUrl().substring(order.getBkimgUrl().indexOf(".")+1,order.getBkimgUrl().length()); 
			log.info("<=========fileFormat:{}=========>",fileFormat);
			String fileName = "order"+UUID.randomUUID().toString().replace("-", "")+"."+fileFormat;//新的文件名
			Boolean dirFlag = createDir(path);
			if(!dirFlag){
				map.put("result", "false");
				map.put("msg", "制作背景图时创建文件夹失败");
				return map;
			}
			
			newImgUrl = copyFile(PropertiesUtils.getInstance().getValue("default_upload_filepath")+order.getBkimgUrl(),path+fileName);
			if(StringUtils.isEmpty(newImgUrl)){
				map.put("result", "false");
				map.put("msg", "制作背景图时拷贝文件失败");
				return map;
			}
			
			//制作图片
			File file = new File(newImgUrl);
			
			BufferedImage buffImg =ImageIO.read(file);
			
	        //创建画笔
	        Graphics2D g = buffImg.createGraphics();

	        //写主体
	        g.setColor(getColor(bodyText.getWm_text_color()));
	        g.setBackground(Color.white);
	        g.setFont(getFont(bodyText));
	        
	        //主体文字自动换行处理
	        autoNewline(buffImg,bodyText,g,order);
	        
	        //关闭画笔
	        g.dispose();
	
	        FileOutputStream out = new FileOutputStream(newImgUrl);
	        
	        //固定缩放
	        BufferedImage inputbig = zoomPic(buffImg,750);
	        
	        //创键编码器，用于编码内存中的图象数据。            
	        ImageIO.write(inputbig,fileFormat,out);
	        out.flush();
	        out.close();
	        
	        map.put("result", "true");
			map.put("msg", newImgUrl);
			map.put("fileName",fileName);
	        
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IIOException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
		 	
	        return map; 
    	}
	
	/**
	 * 获取图片的绝对路径
	 * @return
	 */
	public static String getRootPath(){
		
		//获取系统名称
		String systemName = System.getProperty("os.name");
		log.info("<==systemName:{}=====>",systemName);
		String path = "";
		
		if(!StringUtils.isEmpty(systemName) && systemName.indexOf("Windows") !=-1){
			path = MakeImgUtils.class.getResource(PropertiesUtils.getInstance().getValue("crossbandPicPath")).getPath();
		}else{
			path = System.getProperty("user.dir")+PropertiesUtils.getInstance().getValue("crossbandPicPath");
		}
		
		return path;
	}
	
	/**
	 *  纵向图片拼接并上传至服务器
	 * @param picUrlList 图片路径列表
	 * @param outPath 输出路径
	 * @return
	 */
	public static Map<String,String> stitchPic(List<String> picUrlList,String outPath){
		
		log.info("<====开始拼接图片，图片列表：{}=======>",picUrlList);
		Map<String,String> map = new HashMap<String,String>();
		
		if (picUrlList == null || picUrlList.size() <= 0) {
			map.put("result", "false");
			map.put("msg", "图片数组不能为空");
			return map;
			
		}
		
		try {
			int height = 0, // 总高度
			width = 0, // 总宽度
			_height = 0, // 临时的高度 , 或保存偏移高度
			__height = 0, // 临时的高度，主要保存每个高度
			picNum = picUrlList.size();// 图片的数量
			File fileImg = null; // 保存读取出的图片
			int[] heightArray = new int[picNum]; // 保存每个文件的高度
			BufferedImage buffer = null; // 保存图片流
			List<int[]> imgRGB = new ArrayList<int[]>(); // 保存所有的图片的RGB
			int[] _imgRGB; // 保存一张图片中的RGB数据
			for (int i = 0; i < picNum; i++) {
				fileImg = new File(picUrlList.get(i));
				buffer = ImageIO.read(fileImg);
				heightArray[i] = _height = buffer.getHeight();// 图片高度
				if (i == 0) {
					width = buffer.getWidth();// 图片宽度
				}
				height += _height; // 获取总高度
				_imgRGB = new int[width * _height];// 从图片中读取RGB
				_imgRGB = buffer.getRGB(0, 0, width, _height, _imgRGB, 0, width);
				imgRGB.add(_imgRGB);
			}
			_height = 0; // 设置偏移高度为0
			// 生成新图片
			BufferedImage imageResult = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			for (int i = 0; i < picNum; i++) {
				__height = heightArray[i];
				if (i > 0) {
					_height = heightArray[i-1]; // 计算偏移高度(现在只支持两张)
				}
				imageResult.setRGB(0, _height, width, __height, imgRGB.get(i), 0, width); // 写入流中
			}
			File outFile = new File(outPath);
			ImageIO.write(imageResult, outPath.substring(outPath.indexOf(".")+1,outPath.length()), outFile);// 写图片
			log.info("<===拼图成功:path:="+outPath+"===>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		map.put("result", "true");
		map.put("msg", outPath);
		
		return map;
	}
	
	/**
	 * 创建文件夹
	 * @param destDirName
	 * @return
	 */
	public static boolean createDir(String destDirName) {  
        File dir = new File(destDirName);  
        if (!dir.exists()) {  
        	if (!destDirName.endsWith(File.separator)) {  
                destDirName = destDirName + File.separator;  
            }  
            //创建目录  
            if (dir.mkdirs()) {  
            	log.info("创建目录" + destDirName + "成功！");  
                return true;  
            } else {  
            	log.error("创建目录" + destDirName + "失败！");  
                return false;  
            }   
        }  
        return true;  
    }
	
	/**
	 * 复制图片
	 * @param src:原文件
	 * @param target:目标文件
	 */
	public static String copyFile(String src,String target)  
    {     
       File srcFile = new File(src);    
       File targetFile = new File(target);    
       try {    
           InputStream in = new FileInputStream(srcFile);     
           OutputStream out = new FileOutputStream(targetFile);    
           byte[] bytes = new byte[1024];    
           int len = -1;    
           while((len=in.read(bytes))!=-1)  
           {    
               out.write(bytes, 0, len);    
           }    
           in.close();    
           out.close();  
           log.info("文件复制成功");   
       } catch (FileNotFoundException e) {    
           e.printStackTrace();    
       } catch (IOException e) {    
           e.printStackTrace();    
       }    
       	return target;
    }  
	
	/**
	 * 文字处理
	 * @param text
	 * @return
	 */
	public static Font getFont(FontText text){
		Font font = null;
        if (!StringUtils.isEmpty(text.getWm_text_font())
                && text.getWm_text_size() != null) {
            font = new Font(text.getWm_text_font(), Font.BOLD,
            		text.getWm_text_size());
        } else {
            font = new Font(null, Font.BOLD, 15);
        }
        
        return font;
	}
	
	/**
	 * 颜色处理
	 * @param color
	 * @return
	 */
	public static Color getColor(String color) {
        if (color.charAt(0) == '#') {
            color = color.substring(1);
        }
        if (color.length() != 6) {
            return null;
        }
        try {
            int r = Integer.parseInt(color.substring(0, 2), 16);
            int g = Integer.parseInt(color.substring(2, 4), 16);
            int b = Integer.parseInt(color.substring(4), 16);
            return new Color(r, g, b);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }
	
	/**
	 * 文字自定义换行
	 * @param image：图片对象
	 * @param bodyText:字体对象
	 * @param g:画笔对象
	 * @param benisonTemplate:模板对象
	 * @param blessName:祝福对象
	 * @param writeName:落款人
	 */
	public static void newline(BufferedImage image,FontText bodyText,Graphics2D g,BenisonTemplate benisonTemplate,String writeName,String blessName){
		
		log.info("《====换行解析内容:{}=====》",bodyText.getText());
		
		//获取图片宽
		int srcImgWidth = image.getWidth(null);
		
		int textAreaWidth = 0;//文本域宽度
		int tempX = Integer.valueOf(benisonTemplate.getBodyX());//中心点横坐标
		int tempY = Integer.valueOf(benisonTemplate.getBodyY());//中心点纵坐标
		
		String text = bodyText.getText();//文字内容
		
		try {
			
			//按条件拆解字符
			if(!StringUtils.isEmpty(text)){
				
				//替换祝福人姓名
				if(text.contains("@blessName")){
					text = text.replace("@blessName", blessName);
				}
				
				//替换落款人姓名
				if(text.contains("@writeName")){
					text = text.replace("@writeName", writeName);
				}
				
				//取出文本域宽度
				if(text.contains("@-")&&text.contains("-@")){
					textAreaWidth = Integer.valueOf(text.substring(text.indexOf("@-")+2, text.indexOf("-@")));
					text = text.replace("@-"+textAreaWidth+"-@","");
				}
				
				log.info("《========图片宽度:"+srcImgWidth+"======文本域宽度:"+ textAreaWidth + "==========>");
				
				String[] strs = text.split("/n");
				
				for(String str:strs){
					
					int width = 0;
					int startX = 0;
					
					if(str.contains("/r")){
						
						String newStr = str.replace("/r", "");
						//如果字符中包含/r，判断字符串是否超出文本域宽度
						width = g.getFontMetrics(g.getFont()).charsWidth(newStr.toCharArray(),0,newStr.length());
						
						if(width>textAreaWidth){
							
							//如果超出长度，则分解开，换行写在图片上
							String[] chidrenStrs = str.split("/r");
							for(String chidrenStr:chidrenStrs){
								
								width = g.getFontMetrics(g.getFont()).charsWidth(chidrenStr.toCharArray(),0,chidrenStr.length());
								
								//每行的起始横坐标
								if(chidrenStr.contains("@*")&&chidrenStr.contains("*@")){
									//保留原来的规则
									startX = Integer.valueOf(chidrenStr.substring(chidrenStr.indexOf("@*")+2, chidrenStr.indexOf("*@")));
									chidrenStr= str.substring(chidrenStr.indexOf("*@")+2,chidrenStr.length());
								}else{
									startX = tempX-width/2;
								}
								g.drawString(chidrenStr, startX, tempY);
								//如果不是最后一行，则换行
								if(!chidrenStr.equals(chidrenStrs[chidrenStrs.length-1])){
									if(bodyText.getWm_text_size() <= 22){
										tempY += bodyText.getWm_text_size() + 9;
									}else if(bodyText.getWm_text_size() > 23 && bodyText.getWm_text_size() <= 39){
										tempY += bodyText.getWm_text_size() + 13;
									}else if(bodyText.getWm_text_size() >= 40){
										tempY += bodyText.getWm_text_size() + 18;
									}
								}
							}
						}else{
							
							width = g.getFontMetrics(g.getFont()).charsWidth(newStr.toCharArray(),0,newStr.length());
							
							//每行的起始横坐标
							if(newStr.contains("@*")&&newStr.contains("*@")){
								//保留原来的规则
								startX = Integer.valueOf(newStr.substring(newStr.indexOf("@*")+2, newStr.indexOf("*@")));
								newStr= str.substring(newStr.indexOf("*@")+2,newStr.length());
							}else{
								startX = tempX-width/2;
							}
							g.drawString(newStr, startX, tempY);
							
						}
						
					}else{
						
						width = g.getFontMetrics(g.getFont()).charsWidth(str.toCharArray(),0,str.length());
						
						//每行的起始横坐标
						if(str.contains("@*")&&str.contains("*@")){
							//保留原来的规则
							startX = Integer.valueOf(str.substring(str.indexOf("@*")+2, str.indexOf("*@")));
							str= str.substring(str.indexOf("*@")+2,str.length());
						}else{
							startX = tempX-width/2;
						}
						g.drawString(str, startX, tempY);
						
					}
					
					//如果不是最后一行，则换行
					if(!str.equals(strs[strs.length-1])){
						if(bodyText.getWm_text_size() <= 22){
							tempY += bodyText.getWm_text_size() + 9;
						}else if(bodyText.getWm_text_size() > 23 && bodyText.getWm_text_size() <= 39){
							tempY += bodyText.getWm_text_size() + 13;
						}else if(bodyText.getWm_text_size() >= 40){
							tempY += bodyText.getWm_text_size() + 18;
						}
						
					}
					log.info("《=====单行水印文字总长度:"+ width +"============>");
				}
			}
			
		} catch (Exception e) {
			log.error("<===MakeImgUtils.newline=====祝福语内容规则解析出现问题！=====>");;
		}
		
	}
	
	/**
	 * 固定缩放
	 * @param buff原图片
	 * @param width缩放后的宽度
	 * @return
	 */
	public static BufferedImage zoomPic(BufferedImage buffImg,int width){
		
		//计算缩放后的宽和高
		double sh = (double)width/(double)buffImg.getWidth();//相应的缩放比例
		int height = (int)(sh * buffImg.getHeight());
				
		BufferedImage zoomPic = new BufferedImage(width, height,BufferedImage.TYPE_INT_BGR);
	    zoomPic.getGraphics().drawImage(buffImg, 0, 0, width, height, null); //画图
	    
	    return zoomPic;
	}
	
	/**
	 * 文字自动换行
	 * @param image：图片对象
	 * @param bodyText:字体对象
	 * @param g:画笔对象
	 * @param benisonTemplateBO:模板对象
	 */
	public static void autoNewline(BufferedImage image,FontText bodyText,Graphics2D g,Order order){
		
		//获取图片宽高
		int srcImgWidth = image.getWidth(null);
		int srcImgHeight = image.getHeight(null);
		//获取水印文字的总长度
		int fontlen = 0;
		if(!StringUtils.isEmpty(bodyText.getText())){
			
			fontlen = g.getFontMetrics(g.getFont()).charsWidth(bodyText.getText().toCharArray(),0,
					bodyText.getText().length());
			
			int line = fontlen/srcImgWidth;//文字长度相对于图片宽度应该有多少行
			int y = (int)((double)srcImgHeight/100 * Double.valueOf(order.getFontY()));
			log.info("水印文字总长度:"+fontlen + ",图片宽度:"+ srcImgWidth +",字符个数:"+ bodyText.getText().length());
			
			int tempX = (int)((double)srcImgWidth/100 * Double.valueOf(order.getFontX()));//起始横坐标
			int tempY = y;//起始纵坐标
			//文字叠加,自动换行叠加
			int tempCharLen = 0;//单字符长度
			int tempLineLen = 0;//单行字符总长度临时计算
			StringBuffer sb =new StringBuffer();
			
			for(int i=0;i<bodyText.getText().length(); i++) {
				
				char tempChar = bodyText.getText().charAt(i);
				tempCharLen = g.getFontMetrics(g.getFont()).charWidth(tempChar);
				tempLineLen += tempCharLen;
				
				if(tempLineLen >= srcImgWidth - tempX) {
					//长度已经满一行,进行文字叠加
					g.drawString(sb.toString(), tempX, tempY);
					
					sb.delete(0, sb.length());//清空内容,重新追加
					
					tempY += bodyText.getWm_text_size();
					
					tempLineLen =0;
				}
				sb.append(tempChar);//追加字符
			}
			g.drawString(sb.toString(), tempX, tempY);//最后叠加余下的文字
			
		}
		
	}
	

	/**
	 * 
	 * @Title: 构造叠加图片
	 * @Description: 生成水印并返回java.awt.image.BufferedImage
	 * @param file
	 *            源文件(图片)
	 * @param waterFile
	 *            水印文件(图片)
	 * @param x
	 *            距离右下角的X偏移量
	 * @param y
	 *            距离右下角的Y偏移量
	 * @param alpha
	 *            透明度, 选择值从0.0~1.0: 完全透明~完全不透明
	 * @return BufferedImage
	 * @throws IOException
	 */
	public static BufferedImage watermark(File file, BufferedImage waterImg, int x, int y, float alpha) throws IOException {
		// 获取底图
		BufferedImage buffImg = ImageIO.read(file);
		// 获取层图
//		BufferedImage waterImg = ImageIO.read(waterFile);
		// 创建Graphics2D对象，用在底图对象上绘图
		Graphics2D g2d = buffImg.createGraphics();
		int waterImgWidth = waterImg.getWidth();// 获取层图的宽度
		int waterImgHeight = waterImg.getHeight();// 获取层图的高度
		// 在图形和图像中实现混合和透明效果
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		// 绘制
		g2d.drawImage(waterImg, x, y, waterImgWidth, waterImgHeight, null);
		g2d.dispose();// 释放图形上下文使用的系统资源
		return buffImg;
	}
	
	//
	
	public static void main(String[] args){
		
//		Order order = new Order();
//		order.setBenisonContent("@-783-@说不尽佳节情，信息三弄。昨夜枯梅有缝雪，相思愁。/n小凤疏雨潇潇地，又催下，千行泪。/n新年人间天上，断肠与谁堪寄。/r情人节快乐@blessName。");
//		order.setBkimgUrl("BENISONIMG/BMG0/3.png");
//		order.setBkimgUrl("BENISONIMG/BMG0/4.png");
//		order.setFontcolor("#D96494");
//		order.setFontSize("32");
//		order.setFontX("448");
//		order.setFontY("250");
		BenisonTemplate benisonTemplate = new BenisonTemplate();
		benisonTemplate.setRuleContent("@-783-@说不尽佳节情，信息三弄。昨夜枯梅有缝雪，相思愁。/n小凤疏雨潇潇地，又催下，千行泪。/n新年人间天上，断肠与谁堪寄。/r情人节快乐@blessName。");
		benisonTemplate.setTitleColour("#D96494");
		benisonTemplate.setTitleSize("27");
		benisonTemplate.setTitleType("黑体");
		benisonTemplate.setTitleX("120");
		benisonTemplate.setTitleY("200");
		benisonTemplate.setTailColour("#D96494");
		benisonTemplate.setTailSize("27");
		benisonTemplate.setTailType("黑体");
		benisonTemplate.setTailX("690");
		benisonTemplate.setTailY("370");
		benisonTemplate.setBodyColour("#D96494");
		benisonTemplate.setBodyType("黑体");
		benisonTemplate.setBodySize("11");
		benisonTemplate.setBodyX("189");
		benisonTemplate.setBodyY("100");
		benisonTemplate.setImgUrl("BENISONIMG/BMG0/3.jpg");
//		benisonTemplate.setBodySize("32");
//		benisonTemplate.setBodyX("448");
//		benisonTemplate.setBodyY("250");
//		benisonTemplate.setImgUrl("BENISONIMG/BMG0/4.jpg");
		
		String path = PropertiesUtils.getInstance().getValue("default_upload_filepath")+"export/order/";
		
		Map<String,String> backMap = makeImg(benisonTemplate, "", "", path, 750);
		
		log.info("result"+backMap.get("result")+"========msg:"+backMap.get("msg"));
	}
	
}
