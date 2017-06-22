package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;

public interface ItemService {

	TbItem getItemById(long itemId);
	EUDataGridResult getItemList(int page, int rows);
	TaotaoResult createItem(TbItem item, String desc, String itemParam) throws Exception;
	TaotaoResult deleteItem(String ids);
	//下架
	TaotaoResult instockItem(String ids);
	//上架
	TaotaoResult reshelfItem(String ids);
	//查询商品描述
	TbItemDesc queryItemDesc( Long itemId);
	//加载商品规格
	TbItemParamItem queryItemParam( Long itemId);
	
	
}
