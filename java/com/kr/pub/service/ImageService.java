package com.kr.pub.service;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kr.pub.PublicProjectApplication;
import com.kr.pub.dao.ImageDAO;
import com.kr.pub.dao.ItemDAO;
import com.kr.pub.dao.MenuDAO;
import com.kr.pub.dto.ImageDTO;
import com.kr.pub.dto.MenuDTO;

import jakarta.servlet.ServletContext;


@Service
public class ImageService {
	
	private static String CURR_IMAGE_REPO_PATH = "C:\\file_repo" ;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private ImageDAO imageDAO;
	
	@Autowired
	private MenuDAO menuDAO;

	
	public boolean insertImage(ImageDTO image) {
		imageDAO.insertImage(image);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>" + image.getImgId());
		
		MenuDTO menu = MenuDTO.builder()
						.itemId(image.getItemId())
						.imgId(image.getImgId())
						.build();
		menuDAO.updateMenu(menu);
		return true;
	}
	
	public Map<String, Object> insertImage2(ImageDTO image) {
		imageDAO.insertImage(image);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>" + image.getImgId());
		
		MenuDTO menu = MenuDTO.builder()
						.itemId(image.getItemId())
						.imgId(image.getImgId())
						.build();
		menuDAO.updateMenu(menu);
		return menuDAO.getMenu(image.getItemId());
	}
	
	public ImageDTO imageProcess(MultipartHttpServletRequest multipartRequests) throws IOException {
		CURR_IMAGE_REPO_PATH  = (String) servletContext.getAttribute("newPath");
		ImageDTO newFile = new ImageDTO();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy\\MM\\dd");
        String today = dateFormat.format(new Date());
        
            //MultipartFile mFile = multipartRequests.getFile("menuImage");
            MultipartFile mFile = multipartRequests.getFile("menuImage");
            String originalFileName = mFile.getOriginalFilename(); // 파일명.jpg
            
            String fileExtension = "";
            int lastDotIndex = originalFileName.lastIndexOf(".");
            if (lastDotIndex > 0) {
                fileExtension = originalFileName.substring(lastDotIndex + 1);
            }
            
            String contentType = "image/" + fileExtension;

            if (mFile.getSize() != 0) {
                String uuid = UUID.randomUUID().toString();
                String savePath = CURR_IMAGE_REPO_PATH;
                // String savePath = CURR_IMAGE_REPO_PATH + "\\" + today;
                String saveFileName = uuid;
                
                File file = new File(savePath, saveFileName);
                
                newFile = ImageDTO.builder()
                		.imgNameOrg(originalFileName)
                		//.imgNameReal("\\" + today+ "\\" +saveFileName)
                		.imgNameReal(saveFileName)
                		.length(mFile.getSize())
                		.contentType(contentType)
                		.build();
                		

                if (!file.exists()) {
                    if (file.getParentFile().mkdirs()) {
                        file.createNewFile();
                    }
                }
                
                System.out.println("originalFileName" + " : " + originalFileName);
                System.out.println("fileNameOrg" + " : " + originalFileName);
                System.out.println("fileNameReal" + " : " + saveFileName);
                System.out.println("length" + " : " + file.length());
                System.out.println("contentType " + fileExtension);
                System.out.println();
                mFile.transferTo(file);
            }
            
        	return newFile;
	}
	
	public ImageDTO imageProcess(MultipartFile fileData) throws IOException {
		CURR_IMAGE_REPO_PATH  = (String) servletContext.getAttribute("newPath");
		ImageDTO newFile = new ImageDTO();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy\\MM\\dd");
        String today = dateFormat.format(new Date());
        
            //MultipartFile mFile = multipartRequests.getFile("menuImage");
            MultipartFile mFile = fileData;
            String originalFileName = mFile.getOriginalFilename(); // 파일명.jpg
            
            String fileExtension = "";
            int lastDotIndex = originalFileName.lastIndexOf(".");
            if (lastDotIndex > 0) {
                fileExtension = originalFileName.substring(lastDotIndex + 1);
            }
            
            String contentType = "image/" + fileExtension;

            if (mFile.getSize() != 0) {
                String uuid = UUID.randomUUID().toString();
                String savePath = CURR_IMAGE_REPO_PATH;
                // String savePath = CURR_IMAGE_REPO_PATH + "\\" + today;
                String saveFileName = uuid;
                
                File file = new File(savePath, saveFileName);
                
                newFile = ImageDTO.builder()
                		.imgNameOrg(originalFileName)
                		//.imgNameReal("\\" + today+ "\\" +saveFileName)
                		.imgNameReal(saveFileName)
                		.length(mFile.getSize())
                		.contentType(contentType)
                		.build();
                		

                if (!file.exists()) {
                    if (file.getParentFile().mkdirs()) {
                        file.createNewFile();
                    }
                }
                
                System.out.println("originalFileName" + " : " + originalFileName);
                System.out.println("fileNameOrg" + " : " + originalFileName);
                System.out.println("fileNameReal" + " : " + saveFileName);
                System.out.println("length" + " : " + file.length());
                System.out.println("contentType " + fileExtension);
                System.out.println();
                mFile.transferTo(file);
            }
            
        	return newFile;
	}

	public ImageDTO getImage(String imgId) {
		return imageDAO.getImage(imgId);
	}
	
    public boolean removeFile(String imgId){
    	String savePath  = (String) servletContext.getAttribute("newPath");
        String srcFileName = imageDAO.getImage(imgId).getImgNameReal();
        System.out.println("savePath >> " + savePath + srcFileName);
            File file = new File(savePath + srcFileName);
            boolean result = file.delete();
            return result;
    }

}
