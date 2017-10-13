package com.liketry.interaction.opt.common.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.liketry.interaction.opt.common.service.CommonService;
import com.taikang.iu.com.CommonUtil;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.util.web.WebUtils;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;

import net.sf.json.JSONObject;

@Controller("common_controller")
@RequestMapping(value="/common")
public class CommonController {

	
	@Resource(name="commonService")
	private CommonService commonService;
	
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("/findPage")
	public String showFindPage(String findSql, String fId, String fNum, String fName, String opId, String opNum, String opName, Model model) {
		
		model.addAttribute("findSql",findSql);
		model.addAttribute("fId",fId);
		model.addAttribute("fNum",fNum);
		model.addAttribute("fName",fName);
		model.addAttribute("opId",opId);
		model.addAttribute("opNum",opNum);
		model.addAttribute("opName",opName);
		
		return "common/findPage"; 
	}
	
	/**
	 * 打开主查询页面-级联
	 * @return 页面地址
	 */
	@RequestMapping("/findPage_cascade")
	public String showFindPage_cascade(String findSql, String fId, String fNum, String fName, String opId, String opNum, String opName, 
			String cKey, String cValue, Model model) {
		
		model.addAttribute("findSql",findSql);
		model.addAttribute("fId",fId);
		model.addAttribute("fNum",fNum);
		model.addAttribute("fName",fName);
		model.addAttribute("opId",opId);
		model.addAttribute("opNum",opNum);
		model.addAttribute("opName",opName);
		model.addAttribute("cKey",cKey);
		model.addAttribute("cValue",cValue);
		
		return "common/findPage"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/findPageList")
	@ResponseBody
	public Map<String,Object> getFindPageList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		Dto reqDto = WebUtils.getParamAsDto(request);
		
		// 级联时执行
		String cKey = reqDto.getAsString("cKey");
		String cValue = reqDto.getAsString("cValue");
		if(cKey != null && !"".equals(cKey)){
			reqDto.put(cKey, cValue);
		}
		
		page.setParamObject(reqDto);
		
		CurrentPage currentPage = commonService.queryForPage(page,reqDto.getAsString("findSql"));
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}
	
//	@SuppressWarnings("rawtypes")
//	@RequestMapping(value = "/upload/", method = RequestMethod.POST)
//	public void fileUpload(HttpServletRequest  request,@RequestParam(value = "file") MultipartFile[] files,
//			HttpServletResponse response) throws ServletException, IOException,
//			FileUploadException { 
//		
//		String basePath = request.getSession().getServletContext().getRealPath("/");
//		String savePath = basePath;
//
//		// 文件保存目录URL
//		String saveUrl = "http://localhost:8080/lti-opt/common/";
//
//		// 定义允许上传的文件扩展名
//		HashMap<String, String> extMap = new HashMap<String, String>();
//		extMap.put("image", "gif,jpg,jpeg,png,bmp");
//
//		// 最大文件大小
//		long maxSize = 10000000;
//		response.reset();
//		response.setContentType("text/html;charset=UTF-8");
//		PrintWriter writer = response.getWriter();
//		
//		// 校验
//		// 检查文件
//		ObjectMapper objectMapper = new ObjectMapper();
//		if (!ServletFileUpload.isMultipartContent(request)) {
//			writer.println(objectMapper.writeValueAsString(getError("请选择文件。")));
//			return;
//			
//		}
//		// 检查目录
//		File uploadDir = new File(savePath);
//		if (!uploadDir.isDirectory()) {
//			writer.println(objectMapper.writeValueAsString(getError("上传目录不存在。")));
//			return;
//		}
//		// 检查目录写权限
//		if (!uploadDir.canWrite()) {
//			writer.println(objectMapper.writeValueAsString(getError("上传目录没有写权限。")));
//			return;
//		}
//
//		// 检查目录名
//		String dirName = request.getParameter("dir");
//		if (dirName == null) {
//			dirName = "image";
//		}
//		if (!extMap.containsKey(dirName)) {
//			writer.println(objectMapper.writeValueAsString(getError("目录名不正确。")));
//			return;
//		}
//		
//		// 创建文件夹
//		savePath += dirName + "/";
//		saveUrl += dirName + "";
//		File saveDirFile = new File(savePath);
//		if (!saveDirFile.exists()) {
//			saveDirFile.mkdirs();
//		}
//		
//		// 获取当前日期
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		String ymd = sdf.format(new Date());
//		
//		// 创建文件夹
//		File dirFile = new File(savePath);
//		if (!dirFile.exists()) {
//			dirFile.mkdirs();
//		}
//
//		FileItemFactory factory = new DiskFileItemFactory();
//		ServletFileUpload upload = new ServletFileUpload(factory);
//		upload.setHeaderEncoding("UTF-8");
//		List items = upload.parseRequest(request);
//		Iterator itr = items.iterator();
//		while (itr.hasNext()) {
//			FileItem item = (FileItem) itr.next();
//			String fileName = item.getName();
//			if (!item.isFormField()) {
//				// 检查文件大小
//				if (item.getSize() > maxSize) {
//					writer.println(objectMapper.writeValueAsString(getError("上传文件大小超过10M限制。")));
//					//return;
//				}
//				
//				// 检查扩展名
//				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
//				if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {
//					writer.println(objectMapper.writeValueAsString(getError("上传文件扩展名是不允许的扩展名。\n只允许"+ extMap.get(dirName) + "格式。")));
//					//return;
//				}
//
//				String newFileName =  "img_"+ UUID.randomUUID().toString().replace("-", "");
//				String allNewFileName = newFileName+ "."  + fileExt;
//				
//				// 创建文件
//				try {
//					File uploadedFile = new File(savePath, allNewFileName);
//					item.write(uploadedFile);
//				} catch (Exception e) {
//					writer.println(objectMapper.writeValueAsString(getError("上传文件失败。")));
//					//return;
//				}
//
//				Map<String, Object> msg = new HashMap<String, Object>();
//				msg.put("error", 0);
//				
//				String imageUrl = saveUrl +"?fileName=" +newFileName+"."+fileExt+"";
//				msg.put("url", imageUrl);
//				writer.println(objectMapper.writeValueAsString(msg));
//				//return;
//			}
//		}
//		return;
//	}
	
	@RequestMapping(value = "/editorUpload/", method = RequestMethod.POST)
	public void fileUpload(HttpServletRequest  request, HttpServletResponse response, @RequestParam(value = "imgFile") MultipartFile[] files){ 
		
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			
			// 本地路径  (dir = 'image')、虚拟路径
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String savePath = CommonUtil.uploadFilePath() + "editor/" + request.getParameter("dir") + "/" + sdf.format(new Date()) + "/";
			String relaPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() 
					+ CommonUtil.RELATION_UPLOAD_FILEPATH + "editor/" + request.getParameter("dir") + "/" + sdf.format(new Date()) + "/";
			
			// 创建文件夹
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {dirFile.mkdirs();}
			
			// 保存文件
			for(MultipartFile file : files){
				// 文件名 、文件扩展名、新的文件名
				String fileName = file.getOriginalFilename();	
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();	
				String newFileName = sdf.format(new Date()) + "_" + UUID.randomUUID().toString().replace("-", "") + "." + fileExt;
				// 创建文件
				File newFile = new File(savePath, newFileName);
				// 写入文件  
				FileUtils.copyInputStreamToFile(file.getInputStream(), newFile);
				
				JSONObject obj = new JSONObject();    
                obj.put("error", 0);    
                obj.put("url", relaPath + newFileName);    
                  
                out.print(obj.toString());  
                out.close();
//				Map<String, Object> msg = new HashMap<String, Object>();    
//				msg.put("error", 0);    
//				msg.put("url", relaPath + newFileName);    
//		        
//		        out.print(msg);
//		        out.close();
			}
		} catch (Exception e) {
            e.printStackTrace();
            return;
        }
		
		return;
	}
	
	private Map<String, Object> getError(String message) {
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("error", 1);
		msg.put("message", message);
		return msg;
	}
	
	/**
	 * 图片的回显和显示
	 * @param imgid 图片的id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/image", method = RequestMethod.GET)
	public ModelAndView  getImg(String fileName, HttpServletRequest request,HttpServletResponse response) throws IOException{
		String basePath = request.getSession().getServletContext().getRealPath("/");
		
		String url= basePath+"image"+ "/"+fileName;//获取图片的绝对路径

		//创建流读取对象
        OutputStream stream=null;
		
        java.io.BufferedInputStream bis = null;   
        try {   
        	
        	File file = new File(url);
            bis = new BufferedInputStream(new FileInputStream(file));   
            
            response.setContentType("image/jpg");
			stream=response.getOutputStream();
			
            byte[] buff = new byte[2048];   
            int bytesRead;   
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {   
            	stream.write(buff, 0, bytesRead);
    			stream.flush();
    			
            }   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            if (bis != null)   
                bis.close();   
            if (stream != null)   
            	stream.close();   
        }   
		return null;
	}
}
