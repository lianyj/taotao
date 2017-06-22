package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;

/**
 * 商品管理Controller
 * <p>Title: ItemController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年9月2日上午10:52:46
 * @version 1.0
 */

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EUDataGridResult getItemList(Integer page, Integer rows) {
		EUDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	
	@RequestMapping(value="/item/save", method=RequestMethod.POST)
	@ResponseBody
	private TaotaoResult createItem(TbItem item, String desc, String itemParams) throws Exception {
		TaotaoResult result = itemService.createItem(item, desc, itemParams);
		return result;
	}
	
	@RequestMapping(value="/item/delete", method=RequestMethod.POST)
	@ResponseBody
	private TaotaoResult deleteItem(String ids){
		TaotaoResult result = itemService.deleteItem(ids);
		return result;
	}
	
	/**
	 * 下架
	 */
	@RequestMapping(value="/item/instock", method=RequestMethod.POST)
	@ResponseBody
	private TaotaoResult InstockItem(String ids){
		TaotaoResult result = itemService.instockItem(ids);
		return result;
	}
	
	/**
	 * 上架
	 */
	@RequestMapping(value="/item/reshelf", method=RequestMethod.POST)
	@ResponseBody
	private TaotaoResult ReshelfItem(String ids){
		TaotaoResult result = itemService.reshelfItem(ids);
		return result;
	}
	
	/**
	 *  加载商品描述
	 */
	@RequestMapping(value="/item/query/item/desc/{itemId}", method=RequestMethod.GET)
	@ResponseBody
	private TaotaoResult queryItemDesc(@PathVariable Long itemId){
		TaotaoResult result = new TaotaoResult();
		TbItemDesc tbItemDesc = itemService.queryItemDesc(itemId);
		result.setData(tbItemDesc);
		result.setStatus(200);
		return result;
	}
	
	/**
	 *  加载商品规格
	 */
	 @RequestMapping(value="/item/param/item/query/{itemId}", method=RequestMethod.GET)
		@ResponseBody
		private TaotaoResult queryItemParam(@PathVariable Long itemId){
		 	TaotaoResult result = new TaotaoResult();
		 	TbItemParamItem tbItemParamItem = itemService.queryItemParam(itemId);
		 	result.setData(tbItemParamItem);
			result.setStatus(200);
			return result;
		}
	
}
