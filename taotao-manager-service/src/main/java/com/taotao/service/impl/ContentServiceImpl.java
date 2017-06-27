package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;

/**
 * 内容管理
 * <p>Title: ContentServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年9月8日上午11:09:53
 * @version 1.0
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper  contentMapper;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	
	
	@Override
	public TaotaoResult insertContent(TbContent content) {
		//补全pojo内容
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		
//		//添加缓存同步逻辑
//		try {
//			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		return TaotaoResult.ok();
	}
	
	public TaotaoResult updateContent(TbContent content){
		content.setUpdated(new Date());
		contentMapper.updateByPrimaryKey(content);
		
//		//添加缓存同步逻辑
//		try {
//			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		return TaotaoResult.ok();
	}
	
	@Override
	public EUDataGridResult getContent(TbContent content,Integer page, Integer rows){
		 
		TbContentExample example = new TbContentExample();
		//分页处理
		PageHelper.startPage(page, rows);
		if(0 != content.getCategoryId()){
			example.createCriteria().andCategoryIdEqualTo(content.getCategoryId());
		}
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		//创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		//取记录总条数
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	@Override
	public void deletetContent(String ids){
		String[] idList = ids.split(",");
		if(null !=idList &&idList.length>0){
			for (String id : idList) {
				contentMapper.deleteByPrimaryKey(Long.valueOf(id));
			}
		}
	}
}
