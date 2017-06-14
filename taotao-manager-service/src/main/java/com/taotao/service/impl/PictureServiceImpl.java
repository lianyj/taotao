package com.taotao.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.UploadFileUtil;
import com.taotao.service.PictureService;

/**
 * 图片上传服务
 * <p>Title: PictureServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年9月4日下午2:50:42
 * @version 1.0
 */
@Service
public class PictureServiceImpl implements PictureService {
	
	/** 服务器上图片地址 */
	@Value("${PIC_SERVICE_URL}")
	private String PIC_SERVICE_URL;
	
	/** 域名图片地址 */
	@Value("${PIC_NET_URL}")
	private String PIC_NET_URL;


	@Override
	public Map<Object,Object> uploadPicture(MultipartFile uploadFile) {
		Map<Object,Object> resultMap = new HashMap<Object,Object>();
		try {
		
			String fileName = uploadFile.getOriginalFilename();
			String filePath = UploadFileUtil.uploadImage(PIC_SERVICE_URL, uploadFile.getInputStream(), fileName);
			if(null != filePath){
				resultMap.put("error", 0);
				resultMap.put("url", PIC_NET_URL + filePath);
				return resultMap;
			}else {
				resultMap.put("error", 1);
				resultMap.put("message", "文件上传失败");
				return resultMap;
			}
		} catch (Exception e) {
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传发生异常");
			return resultMap;
		}
	}

}
