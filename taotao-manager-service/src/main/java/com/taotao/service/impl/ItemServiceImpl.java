package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.ItemService;

/**
 * 商品管理Service
 * <p>Title: ItemServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年9月2日上午10:47:14
 * @version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Override
	public TbItem getItemById(long itemId) {
		
		//TbItem item = itemMapper.selectByPrimaryKey(itemId);
		TbItemExample example = new TbItemExample();
		//添加查询条件
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			TbItem item = list.get(0);
			return item;
		}
		return null;
	}

	/**
	 * 商品列表查询
	 * <p>Title: getItemList</p>
	 * <p>Description: </p>
	 * @param page
	 * @param rows
	 * @return
	 * @see com.taotao.service.ItemService#getItemList(long, long)
	 */
	@Override
	public EUDataGridResult getItemList(int page, int rows) {
		//查询商品列表
		TbItemExample example = new TbItemExample();
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		//创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		//取记录总条数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public TaotaoResult createItem(TbItem item, String desc, String itemParam) throws Exception {
		//item补全
		//生成商品ID
		Long itemId = IDUtils.genItemId();
		item.setId(itemId);
		// '商品状态，1-正常，2-下架，3-删除',
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//插入到数据库
		itemMapper.insert(item);
		//添加商品描述信息
		TaotaoResult result = insertItemDesc(itemId, desc);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		//添加规格参数
		result = insertItemParamItem(itemId, itemParam);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		return TaotaoResult.ok();
	}
	
	public TaotaoResult updateItem(TbItem item, String desc, String itemParam) {
		
		//修改商品信息
		item.setUpdated(new Date());
		itemMapper.updateByPrimaryKeySelective(item);
		
		//修改商品描述信息
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(new Date());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
		
		//修改规格参数
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(item.getId());
		itemParamItem.setParamData(itemParam);
		itemParamItem.setUpdated(new Date());
		itemParamItemMapper.updateByPrimaryKeySelective(itemParamItem);
		
		return TaotaoResult.ok();
	}
	
	/**
	 * 添加商品描述
	 * <p>Title: insertItemDesc</p>
	 * <p>Description: </p>
	 * @param desc
	 */
	private TaotaoResult insertItemDesc(Long itemId, String desc) {
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		itemDescMapper.insert(itemDesc);
		return TaotaoResult.ok();
	}
	
	/**
	 * 添加规格参数
	 * <p>Title: insertItemParamItem</p>
	 * <p>Description: </p>
	 * @param itemId
	 * @param itemParam
	 * @return
	 */
	private TaotaoResult insertItemParamItem(Long itemId, String itemParam) {
		//创建一个pojo
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		//向表中插入数据
		itemParamItemMapper.insert(itemParamItem);
		
		return TaotaoResult.ok();
		
	}
	
	public TaotaoResult deleteItem(String ids){
		String[] idList = ids.split(",");
		if(null !=idList &&idList.length>0){
			for (String id : idList) {
				itemMapper.deleteByPrimaryKey(Long.valueOf(id));
			}
		}
		return TaotaoResult.ok();
		 
	}
	
	
	public TaotaoResult instockItem(String ids){
		String[] idList = ids.split(",");
		if(null !=idList &&idList.length>0){
			for (String id : idList) {
				TbItem item = new TbItem();
				item.setId(Long.valueOf(id));
				// '商品状态，1-正常，2-下架，3-删除',
				item.setStatus((byte) 2);
				itemMapper.updateByPrimaryKeySelective(item);
			}
		}
		return TaotaoResult.ok();
	}
	
	
	public TaotaoResult reshelfItem(String ids){
		String[] idList = ids.split(",");
		if(null !=idList &&idList.length>0){
			for (String id : idList) {
				TbItem item = new TbItem();
				item.setId(Long.valueOf(id));
				// '商品状态，1-正常，2-下架，3-删除',
				item.setStatus((byte) 1);
				itemMapper.updateByPrimaryKeySelective(item);
			}
		}
		return TaotaoResult.ok();
	}
	
	//查询商品描述
	public TbItemDesc queryItemDesc(Long itemId){
		TbItemDesc  tbItemDesc= itemDescMapper.selectByPrimaryKey(itemId);
		return tbItemDesc;
	}
	//加载商品规格
	public TbItemParamItem queryItemParamItem( Long itemId){
		TbItemParamItemExample example = new TbItemParamItemExample();
		TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> tbItemParamItem = itemParamItemMapper.selectByExampleWithBLOBs(example);
		return tbItemParamItem.get(0);
	}
}
