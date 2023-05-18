package com.will.homestay.mapper;

import com.will.homestay.entity.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.will.homestay.pojo.ShowOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    List<ShowOrder> queryOrders(int userId);
    List<ShowOrder> queryOrdersByLandlordId(int landlordId);
    int monthOrder();
    int monthIncome();
    int monthLiving();
    int dayOrder();
    int dayIncome();
    int dayLiving();



}
