package com.xull.order.service;

import com.xull.common.pojo.TaotaoResult;
import com.xull.order.pojo.OrderInfo;

public interface OrderService {
	
	public TaotaoResult createOrder(OrderInfo orderInfo);
}
