package com.taotao.portal.pojo;

import java.io.Serializable;

import com.taotao.pojo.TbItem;

public class ItemInfo extends TbItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7923162505612708087L;

	public String[] getImages(){
		String image =getImage();
		if(image !=null){
			String[] images =image.split(",");
			return images;
		}
		return null;
	}
}
