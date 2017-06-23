package com.taotao.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.ChannelSftp;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.SFtpUtil;
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
	
	/** 图片服务器地址 */
	@Value("${PIC_SERVICE_HOST}")
	private String PIC_SERVICE_HOST;
	
	/** 图片服务器端口 */
	@Value("${PIC_SERVICE_PORT}")
	private String PIC_SERVICE_PORT;
	
	/** 图片服务器账号 */
	@Value("${PIC_SERVICE_USER}")
	private String PIC_SERVICE_USER;
	
	/** 图片服务器密码 */
	@Value("${PIC_SERVICE_PWD}")
	private String PIC_SERVICE_PWD;
	
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
			//取原始文件名
			String oldName = uploadFile.getOriginalFilename();
			//生成一个新的文件名
//			String newName =  UUID.randomUUID();
			String newName =  IDUtils.genImageName();
			newName = newName+ oldName.substring(oldName.lastIndexOf("."));
			SFtpUtil sf = new SFtpUtil(); 
			ChannelSftp sftp=sf.connect(PIC_SERVICE_HOST, Integer.valueOf(PIC_SERVICE_PORT), PIC_SERVICE_USER, PIC_SERVICE_PWD);
			sf.upload(PIC_SERVICE_URL, uploadFile.getInputStream(), newName, sftp);
//			String filePath = UploadFileUtil.uploadImage(PIC_SERVICE_URL, uploadFile.getInputStream(), fileName);
			resultMap.put("error", 0);
			resultMap.put("url", PIC_NET_URL + newName);
			resultMap.put("message", "文件上传成功");
			return resultMap;
		} catch (Exception e) {
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传发生异常");
			return resultMap;
		}
	}

}
