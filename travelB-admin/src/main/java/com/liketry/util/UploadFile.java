package com.liketry.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UploadFile {
	
	private static ArrayList<HashMap<String, String>> fileList = new ArrayList<HashMap<String, String>>();
	
	public static List<HashMap<String, String>> getUpFileList() {
		return fileList;
	}
	
	private static UploadFile instance = null;
	
	public static synchronized UploadFile getInstance(){
		
		if (instance == null){
			instance = new UploadFile();
		}
		
		return instance;
	}
	
	/**
	 * 上传多张图片(返回路径由,隔开)
	 * @param request 
	 * @param upLoadPath 配置文件根路径
	 * @param folderNames 文件夹名称(多级/连接)
	 * @param fileNamePre 文件名称前缀
	 * @return
	 * @throws Exception 
	 */
	public String uploadFile(HttpServletRequest request,String upLoadPath,
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
			
			while (iter.hasNext()) {
				
				// 记录上传过程起始时的时间，用来计算上传时间
				int pre = (int) System.currentTimeMillis();
				log.info("文件上传开始时间：" + pre);
				
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					if (myFileName.trim() != "") {
						
						log.info("上传文件：" + myFileName);
						
						// 重命名上传后的文件名
						String fileName = file.getOriginalFilename();
						if (StringUtils.isNotBlank(fileNamePre)) {
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
							
							byte[] bytes = file.getBytes();  
		                    BufferedOutputStream buffStream =   
		                            new BufferedOutputStream(new FileOutputStream(localFile));  
		                    buffStream.write(bytes);  
		                    buffStream.close();  
//							file.transferTo(localFile);//相对路径时使用报错
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
				log.info("文件上传用时：" + (finaltime - pre));
			}
			
			if(pictureUrl.toString()!=null && !"".equals(pictureUrl.toString())){
				imgUrl = pictureUrl.substring(0, pictureUrl.toString().length()-1);
			}
		}
		
		return imgUrl;
	}
}
