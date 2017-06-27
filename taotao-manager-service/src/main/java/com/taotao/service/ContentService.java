package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {

	TaotaoResult insertContent(TbContent content);
	EUDataGridResult getContent(TbContent content,Integer page, Integer rows);
	void deletetContent(String ids);
	TaotaoResult updateContent(TbContent content);
}
