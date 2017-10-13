package com.taikang.iu.com;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class UploadFile {
	
	private static ArrayList<HashMap<String, String>> fileList = new ArrayList<HashMap<String, String>>();
	
	public static List<HashMap<String, String>> getUpFileList() {
		return fileList;
	}
	
	private static final Log logger = LogFactory.getLog(UploadFile.class);

	/**
	 * 上传图片
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public static String uploadFile(HttpServletRequest request,String upLoadPath,
			String url,String fileNamePre) throws Exception{
		
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		
		StringBuffer pictureUrl = new StringBuffer();
		String imgUrl = "";
		upLoadPath = upLoadPath+ url+"/";
		
		// 判断是否有文件上传
		if (multipartResolver.isMultipart(request)) {
			
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			fileList = new ArrayList<HashMap<String, String>>();
			while (iter.hasNext()) {
				
				// 记录上传过程起始时的时间，用来计算上传时间
				int pre = (int) System.currentTimeMillis();
				logger.info("文件上传开始时间：" + pre);
				
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					if (myFileName.trim() != "") {
						
						logger.info("上传文件：" + myFileName);
						
						// 重命名上传后的文件名
						String fileName = file.getOriginalFilename();
						if (!CommonUtil.isEmpty(fileNamePre)) {
							fileName = fileNamePre+ UUID.randomUUID().toString().replace("-", "")+fileName.substring(fileName.lastIndexOf("."),fileName.length());
						}
						
						File fileDir = new File(upLoadPath);
						if (!fileDir.exists()) {
							if(!fileDir.mkdirs()) {
								throw new Exception("文件上传出错：文件路径配置错误！");
							}
						}
						
						// 定义上传路径
						String path = upLoadPath + fileName;
						File localFile = new File(path);
						HashMap<String, String> map = new HashMap<String, String>();
						
						try {
							if (localFile.exists()) {
								upLoadPath = upLoadPath + UUID.randomUUID(); 
								path = upLoadPath + "//"+ fileName;
								File dir = new File(path);
								
								if (!dir.exists()) {
									if(!dir.mkdirs()) {
										throw new Exception("文件上传出错：文件路径配置错误！");
									}
								}
								localFile = new File(path);
							}
							
							file.transferTo(localFile);
							pictureUrl.append(url+"/"+fileName + ",");
						
						} catch (IllegalStateException e) {
							throw new Exception("文件上传出错：文件状态错误！");
						} catch (IOException e) {
							throw new Exception("文件上传出错：文件流读取出错！");
						}
					}
				}
				
				// 记录上传该文件后的时间
				int finaltime = (int) System.currentTimeMillis();
				logger.info("文件上传用时：" + (finaltime - pre));
			}
			
			if(pictureUrl.toString()!=null && !"".equals(pictureUrl.toString())){
				imgUrl = pictureUrl.substring(0, pictureUrl.toString().length()-1);
			}
		}
		
		return imgUrl;
	}
}
