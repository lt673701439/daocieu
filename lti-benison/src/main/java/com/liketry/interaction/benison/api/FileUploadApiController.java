package com.liketry.interaction.benison.api;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.Base64;
import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.sdk.Result;
import com.liketry.interaction.benison.util.MakeImgUtils;
import com.liketry.interaction.benison.util.PropertiesUtils;
import com.liketry.interaction.benison.util.StringUtils;
import com.liketry.interaction.benison.util.UserUtils;

/**
 * 文件接口
 * @author pengyy
 */
@RestController
@RequestMapping("fileUpload_api")
public class FileUploadApiController {
	
	private static final Logger log = LoggerFactory.getLogger(OrderApiController.class);

	
	 /**
	  * 上传图片(ios)
	  * @param binaryFile 二进制流转换的字符串 
	  * @return
	  */
	 @RequestMapping("/uploadPicture") 
    Result<String> uploadPicture(@RequestBody String json) {
    	
    	log.info("<========json:=="+json+"=========>");
    	
    	byte[] binaryFile = null;
    	String imgName = null;
    	String imgUrl = null;
    	
    	if(json!=null){
    		JSONObject data = JSONObject.parseObject(json);
    		binaryFile = Base64.decodeFast(data.getString("binaryFile"));
    		imgName = data.getString("imgName");
    		imgUrl = data.getString("imgUrl");
    	}
    	
    	if(binaryFile==null){ 
    		return new Result<>(SystemConstants.RESULT_FALSE,"二进制文件不能为null",null);
    	}
    	
    	if(StringUtils.isEmpty(imgName)){
    		return new Result<>(SystemConstants.RESULT_FALSE,"文件名不能为null",null);
    	}
    	
    	String imgPath = "BENISONIMG/CUSTBMG/";//图片路径
    	String localPath = PropertiesUtils.getInstance().getValue("default_upload_filepath");//属性文件中路径
    	
    	//删除之前上一次的图片
    	if(!StringUtils.isEmpty(imgUrl)){
    		File file = new File(PropertiesUtils.getInstance().getValue("default_upload_filepath")+imgUrl);
    		if(file.exists()){
    			file.delete();
    		}
    	}
    	
    	imgName = "CUSTBMG"+UUID.randomUUID().toString().replace("-", "")+imgName.substring(imgName.indexOf("."),imgName.length());//重新定义文件名称
    	
    	Boolean flag = MakeImgUtils.createDir(localPath+"/"+imgPath);
    	
    	if(!flag){
    		return new Result<>(SystemConstants.RESULT_FALSE,"文件夹创建失败",null);
    	}
    	
        try {  
            // 将字符串转换成二进制，用于显示图片    
            // 将上面生成的图片格式字符串 binaryFile，还原成图片显示    
            InputStream in = new ByteArrayInputStream(binaryFile);  
  
            File file=new File(localPath+"/"+imgPath,imgName);//可以是任何图片格式.jpg,.png等  
            FileOutputStream fos=new FileOutputStream(file);  
                
            byte[] b = new byte[1024];  
            int nRead = 0;  
            while ((nRead = in.read(b)) != -1) {  
                fos.write(b, 0, nRead);  
            }  
            fos.flush();  
            fos.close();  
            in.close();  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
        }  
    	
        return new Result<>(SystemConstants.RESULT_SUCCESS,"调用成功",imgPath+imgName);
    }
	 
	 /**
	  * 上传图片(微信客户端)
	  * @param 
	  * @return
	  */
	 @RequestMapping(consumes = "multipart/form-data", value = "/uploadPictureBywx", method = RequestMethod.POST)
    Result<String> uploadPicture(HttpServletRequest request) { 
		 
        String imgUrl = request.getParameter("imgUrl");//待删的图片
        String imgPath = "BENISONIMG/CUSTBMG/";//图片路径
        String imgName = null;
        String localPath = PropertiesUtils.getInstance().getValue("default_upload_filepath");//属性文件中路径
        
        //删除之前上一次的图片
        if(!StringUtils.isEmpty(imgUrl)){
        	
    		File fileDelete = new File(PropertiesUtils.getInstance().getValue("default_upload_filepath")+imgUrl);
    		if(fileDelete.exists()){
    			fileDelete.delete();
    		}
        }
        
        try{
    		// 转换成多部分request
    		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
    		
    		// 取得request中的所有文件名
    		List<MultipartFile> fileList = multiRequest.getFiles("file");  
    		
    		for (MultipartFile file : fileList) {  
    			
    			if (file != null) {
    				
    				// 取得当前上传文件的文件名称
    				imgName = file.getOriginalFilename();
    				
    				if(!StringUtils.isEmpty(imgName)){
    					
    					log.info("上传文件：" + imgName);
    					
    					String fileFormat = imgName.substring(imgName.indexOf(".")+1,imgName.length());
    					
    					imgName = "CUSTBMG"+UUID.randomUUID().toString().replace("-", "")+"."+fileFormat;//重新定义文件名称
    					
    					Boolean flag = MakeImgUtils.createDir(localPath+"/"+imgPath);
    					
    					if(!flag){
    						return new Result<>(SystemConstants.RESULT_FALSE,"文件夹创建失败",null);
    					}
    					
    					BufferedImage buffImg = ImageIO.read(file.getInputStream());
    					FileOutputStream fos=new FileOutputStream(localPath+"/"+imgPath+"/"+imgName);
    					
    					//固定缩放
    			        BufferedImage inputbig = MakeImgUtils.zoomPic(buffImg,1200);
    			        
    			        ImageIO.write(inputbig,fileFormat,fos);
    			        
    			        fos.flush();
    			        fos.close();
    			        
    				}
    				
    			}
    		}
        	
        } catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
        return new Result<>(SystemConstants.RESULT_SUCCESS,"调用成功",imgPath+imgName);

	} 
	 
	 
	 /**
	  * 上传结果图(微信客户端)
	  * @param 
	  * @return
	  */
	 @RequestMapping(consumes = "multipart/form-data", value = "/uploadResultPictureBywx", method = RequestMethod.POST)
    Result<String> uploadResultPicture(HttpServletRequest request) { 
		 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = sdf.format(UserUtils.getCurrentDate());//日期
        String imgUrl = request.getParameter("imgUrl");//待删的图片
        String imgPath = "order/"+date+"/";//图片路径
        String imgName = null;
        String localPath = PropertiesUtils.getInstance().getValue("default_upload_filepath");//属性文件中路径
        
        //删除之前上一次的图片
        if(!StringUtils.isEmpty(imgUrl)){
        	
    		File fileDelete = new File(PropertiesUtils.getInstance().getValue("default_upload_filepath")+imgUrl);
    		if(fileDelete.exists()){
    			fileDelete.delete();
    		}
        }
        
        try{
    		// 转换成多部分request
    		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
    		
    		// 取得request中的所有文件名
    		List<MultipartFile> fileList = multiRequest.getFiles("file");  
    		
    		for (MultipartFile file : fileList) {  
    			
    			if (file != null) {
    				
    				// 取得当前上传文件的文件名称
    				imgName = file.getOriginalFilename();
    				
    				if(!StringUtils.isEmpty(imgName)){
    					
    					log.info("上传文件：" + imgName);
    					
    					imgName = "ORDER"+UUID.randomUUID().toString().replace("-", "")+imgName.substring(imgName.indexOf("."),imgName.length());//重新定义文件名称
    					
    					Boolean flag = MakeImgUtils.createDir(localPath+"/"+imgPath);
    					
    					if(!flag){
    						return new Result<>(SystemConstants.RESULT_FALSE,"文件夹创建失败",null);
    					}
    					
    					InputStream in = file.getInputStream();  
    					
    					FileOutputStream fos=new FileOutputStream(localPath+"/"+imgPath+"/"+imgName);  
    					
    					byte[] b = new byte[1024];  
    					int nRead = 0;  
    					while ((nRead = in.read(b)) != -1) {  
    						fos.write(b, 0, nRead);  
    					}  
    					fos.flush();  
    					fos.close();  
    					in.close();  
    				}
    				
    			}
    		}
        	
        } catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
        return new Result<>(SystemConstants.RESULT_SUCCESS,"调用成功",imgPath+imgName);

	}
	 
}