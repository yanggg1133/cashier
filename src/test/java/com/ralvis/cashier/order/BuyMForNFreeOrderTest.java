 /**
 * @author elvis.zhang
 * Description: 
 * 测试打印省钱的订单
 * 2016年3月11日下午3:42:05
 */
package com.ralvis.cashier.order;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.ralvis.cashier.discount.BuyMForNFreeDiscount;
import com.ralvis.cashier.discount.NormalDiscount;
import com.ralvis.cashier.item.entity.BuyMForNFreeItemDetail;
import com.ralvis.cashier.item.entity.Item;
import com.ralvis.cashier.item.entity.ItemDetail;
import com.ralvis.cashier.item.entity.NormalDiscountItemDetail;
import com.ralvis.cashier.item.service.ItemService;
import com.ralvis.cashier.item.service.MockItemService;
import com.ralvis.cashier.utils.Settings;

public class BuyMForNFreeOrderTest {
	@Test
	public void testPrintThreeItems() {
		Order order = new BuyMForNFreeOrder();
		
		BuyMForNFreeDiscount discount2For1 = new BuyMForNFreeDiscount(Settings.getBuyM(), 
				Settings.getFreeN());
		NormalDiscount normalDiscount95 = new NormalDiscount(95);
		
		ItemService itemService = new MockItemService();
		Item coco = itemService.findByItemCode("ITEM000001");
		ItemDetail cocoDetail = new BuyMForNFreeItemDetail(coco, 3, discount2For1);
		
		order.addItemDetail(cocoDetail);
		
		Item ball = itemService.findByItemCode("ITEM000002");
		ItemDetail ballDetail = new BuyMForNFreeItemDetail(ball, 6, discount2For1);
		order.addItemDetail(ballDetail);
		
		Item apple = itemService.findByItemCode("ITEM000003");
		ItemDetail appleDetail = new NormalDiscountItemDetail(apple, 2, normalDiscount95);
		order.addItemDetail(appleDetail);
		
		order.buildPurchasingList();
		
		List<String> lines = order.getPurchasingList();
		List<String> expectedLines = new ArrayList<>();
		expectedLines.add("***<没钱赚商店>购物清单***");
		expectedLines.add("名称：可口可乐，数量：3瓶，单价：3.00(元)，小计：6.00(元)");
		expectedLines.add("名称：羽毛球，数量：6个，单价：1.00(元)，小计：4.00(元)");
		expectedLines.add("名称：苹果，数量：2斤，单价：5.50(元)，小计：10.45(元)，节省0.55(元)");
		expectedLines.add("----------------------");
		expectedLines.add("买二赠一商品：");
		expectedLines.add("名称：可口可乐，数量：1瓶");
		expectedLines.add("名称：羽毛球，数量：2个");
		expectedLines.add("----------------------");
		expectedLines.add("总计：20.45(元)");
		expectedLines.add("节省：5.55(元)");
		expectedLines.add("**********************");
		
		Assert.assertEquals(lines, expectedLines);
	}
}
