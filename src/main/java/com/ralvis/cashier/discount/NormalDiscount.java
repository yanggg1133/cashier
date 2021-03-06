 /**
 * @author elvis.zhang
 * Description: 
 * 商品享受固定折扣, 例如95折
 * 2016年3月8日下午4:21:45
 */
package com.ralvis.cashier.discount;

import java.math.BigDecimal;
import java.util.List;

import com.ralvis.cashier.item.entity.Item;
import com.ralvis.cashier.item.entity.ItemDetail;
import com.ralvis.cashier.item.entity.NormalDiscountItemDetail;
import com.ralvis.cashier.order.Order;
import com.ralvis.cashier.order.SavedMoneyOrder;
import com.ralvis.cashier.setting.Settings;

public class NormalDiscount extends OriginalCostDiscount implements SavedMoney{
	private static final int MIN_DISCOUNT = 1;
	private static final int MAX_DISCOUNT = 100;
	private static final BigDecimal BIGDECIMAL_100 = BigDecimal.valueOf(100);
	
	private int discount;
	
	public NormalDiscount(int discount) {
		this.discount = discount;
		if (discount < MIN_DISCOUNT) {
			throw new RuntimeException(String.format("折扣不能小于%d", MIN_DISCOUNT));
		}
		if (discount >= MAX_DISCOUNT) {
			throw new RuntimeException(String.format("折扣不能大于%d", MAX_DISCOUNT));
		}
	}
	
	@Override
	public int getPriority() {
		return Settings.getNormalDiscountPriority();
	}
	
	/**
	 * 技术折扣商品省的钱
	 */
	public BigDecimal computeSavedMoney(int amount, BigDecimal unitPrice) {
		return super.compute(amount, unitPrice)
				.multiply(BIGDECIMAL_100.subtract(BigDecimal.valueOf(discount)))
				.divide(BIGDECIMAL_100);
	}
	
	@Override
	public BigDecimal compute(int amount, BigDecimal unitPrice) {
		return super.compute(amount, unitPrice)
				.multiply(BigDecimal.valueOf(discount))
				.divide(BIGDECIMAL_100);
	}
	
	@Override
	public ItemDetail generateItemDetail(Item item, int amount) {
		return new NormalDiscountItemDetail(item, amount, this);
	}
	
	@Override
	public Order generateOrder(List<ItemDetail> itemDetails) {
		return new SavedMoneyOrder(itemDetails);
	}
}
