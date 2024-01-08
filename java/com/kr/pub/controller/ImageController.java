package com.kr.pub.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kr.pub.PublicProjectApplication;
import com.kr.pub.dto.ImageDTO;
import com.kr.pub.service.ImageService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ImageController {
	
	private static String CURR_IMAGE_REPO_PATH = "C:\\file_repo";
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private ImageService imageService;
	
	@ResponseBody
	@PostMapping(value="/image/insertImage")
	public Map<String, Object> imageInsert(MultipartHttpServletRequest multipartRequest, HttpServletResponse res) {
		//List<ImageDTO> imageList = fileProcess(multipartRequest);
		
		return null;
	}
	
	@GetMapping("/image/download/{imgId}")
	public void download(@PathVariable String imgId,
			                 HttpServletResponse response) throws Exception {
		CURR_IMAGE_REPO_PATH  = (String) servletContext.getAttribute("newPath");
		OutputStream out = response.getOutputStream();
		
		ImageDTO image = imageService.getImage(imgId); 
		
        String fileExtension = image.getContentType();
        String [] str = fileExtension.split("/");
        fileExtension  = "." + str[str.length -1];
		
		if (image != null) {
			String filePath = CURR_IMAGE_REPO_PATH + image.getImgNameReal();  
			File imageFile = new File(filePath);
			FileInputStream in = new FileInputStream(imageFile);
			
			byte[] buffer = new byte[1024 * 8];
			while (true) {
				int count = in.read(buffer); 
				if (count == -1) 
					break;
				out.write(buffer, 0, count);
			}
			in.close();
		}
		out.close();
	}
	
}
