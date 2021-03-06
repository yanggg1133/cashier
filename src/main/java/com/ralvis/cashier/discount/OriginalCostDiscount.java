 /**
 * @author elvis.zhang
 * Description: 
 * 商品按照原价计算，不享受折扣
 * 2016年3月8日下午4:08:33
 */
package com.ralvis.cashier.discount;

import java.math.BigDecimal;
import java.util.List;

import com.ralvis.cashier.item.entity.Item;
import com.ralvis.cashier.item.entity.ItemDetail;
import com.ralvis.cashier.item.entity.NormalItemDetail;
import com.ralvis.cashier.order.NormalOrder;
import com.ralvis.cashier.order.Order;
import com.ralvis.cashier.setting.Settings;

public class OriginalCostDiscount implements Discount {

	@Override
	public BigDecimal compute(int amount, BigDecimal unitPrice) {
		Settings.checkBuyAmountCondition(amount);
		Settings.checkBuyUnitPriceCondition(unitPrice);
		return unitPrice.multiply(BigDecimal.valueOf(amount));
	}
	
	@Override
	public int getPriority() {
		return 0;
	}
	
	@Override
	public ItemDetail generateItemDetail(Item item, int amount) {
		return new NormalItemDetail(item, amount);
	}
	
	@Override
	public Order generateOrder(List<ItemDetail> itemDetails) {
		return new NormalOrder(itemDetails);
	}
}
