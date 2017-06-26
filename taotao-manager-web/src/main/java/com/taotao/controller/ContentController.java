package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

/**
 * 内容管理Controller
 * <p>Title: ContentController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年9月8日上午11:13:52
 * @version 1.0
 */
@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult insertContent(TbContent content) {
		TaotaoResult result = contentService.insertContent(content);
		return result;
	}
	

	@RequestMapping("/query/list")
	@ResponseBody
	public EUDataGridResult getContentList(TbContent content,Integer page, Integer rows) {
		EUDataGridResult result = contentService.getContent(content, page, rows);
		return result;
		
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult deletetContent(String ids) {
		TaotaoResult result = new TaotaoResult();
		if(null != ids){
			contentService.deletetContent(ids);
			result.setStatus(200);
			result.setMsg("删除成功");
		}else {
			result.setMsg("删除失败");
		}
		return result;
		
	}

}
