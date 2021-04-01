package org.jeecg.modules.demo.shop.service.impl;

import org.jeecg.modules.demo.shop.entity.BookingOrder;
import org.jeecg.modules.demo.shop.mapper.BookingOrderMapper;
import org.jeecg.modules.demo.shop.service.IBookingOrderService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 预约订单管理
 * @Author: jeecg-boot
 * @Date:   2020-07-20
 * @Version: V1.0
 */
@Service
public class BookingOrderServiceImpl extends ServiceImpl<BookingOrderMapper, BookingOrder> implements IBookingOrderService {

}
