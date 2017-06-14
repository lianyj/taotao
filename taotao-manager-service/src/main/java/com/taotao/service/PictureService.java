package com.taotao.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface PictureService {

	Map<Object,Object> uploadPicture(MultipartFile uploadFile);
}
