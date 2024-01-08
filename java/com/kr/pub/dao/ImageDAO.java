package com.kr.pub.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kr.pub.dto.ImageDTO;

@Mapper
public interface ImageDAO {
	
	public void insertImage(ImageDTO image);

	public ImageDTO getImage(String imgId);
	
	public void deleteImage(String imgId);

}
