package com.taikang.iu.com;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
 * 类的定义:XXXXX
 * Class <code>Constants.java</code>   
 * @author liu lianghui
 * @created 3 2, 2012
 * @version v1.0  
 */

public class Constants {
	public static final String UPLOAD_IMAGE_PATH="upload/image/";  //上传图片的相对路径
	public static final String UPLOAD_FILE_PATH="upload/file/";     //上传文档的相对路径
	public static final long UPLOAD_IMAGE_SIZE=10240000;  //上传图片的最大字节限制10Mb
	public static final String UPLOAD_IMAGE_PATHS="D:/upload/image/";  //上传图片的相对路径
	
	
	public static String copyFile(HttpServletRequest request, MultipartFile imgFile,String Path)
	  {
	    String destImagePath = "";
	    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMM");
	    try {
	      String currentDate = fmt.format(new Date());
	      String basePath = Path;
	      String filename = imgFile.getOriginalFilename();
	      String suffix = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
	      destImagePath = "img/" + currentDate + "/" + UUID.randomUUID().toString().replace("-", "") + "." + suffix;
	      File destImageFile = new File(basePath + destImagePath);
	      File destImageParentFile = destImageFile.getParentFile();
	      if (!destImageParentFile.isDirectory()) {
	        destImageParentFile.mkdirs();
	      }
	      imgFile.transferTo(destImageFile);
//	      logger.info("FileUploadHandler处理文件上传至文件目录成功");
	    } catch (Exception e) {
	      e.printStackTrace();
	      destImagePath = "";
//	      logger.error("FileUploadHandler处理文件上传至文件目录出现错误");
	    }
	    return destImagePath;
	  }
	
}
