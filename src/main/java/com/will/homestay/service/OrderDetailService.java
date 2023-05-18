package com.will.homestay.service;

import com.will.homestay.entity.Message;
import com.will.homestay.entity.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.will.homestay.entity.Room;
import com.will.homestay.pojo.ShowOrder;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
public interface OrderDetailService extends IService<OrderDetail> {
    Message order(OrderDetail order,int userId);
    List<ShowOrder> queryOrders(int userId);
    List<ShowOrder> queryOrdersByLandlordId(int userId);
    Message tuiFang(int orderId);

    Message clear(int roomId);

}
