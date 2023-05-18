package com.will.homestay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.will.homestay.entity.Message;
import com.will.homestay.entity.OrderDetail;
import com.will.homestay.entity.Room;
import com.will.homestay.entity.User;
import com.will.homestay.mapper.OrderDetailMapper;
import com.will.homestay.mapper.RoomMapper;
import com.will.homestay.mapper.UserMapper;
import com.will.homestay.pojo.ShowOrder;
import com.will.homestay.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
    @Autowired
    OrderDetailMapper orderDetailMapper;
    @Autowired
    RoomMapper roomMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public Message order(OrderDetail order, int userId) {
        //根据order的roomId获取房屋，并判断当前房屋是否在锁定状态
        Room room = roomMapper.selectById(order.getRoomId());
        if (room.getRoomCondition()==true){
            //根据userId获取用户信息
            User user = userMapper.selectById(order.getLandlordId());
            Message message = new Message();
            message.setMessage("该房间正在打扫或者维修升级！暂不可预定,可联系房东"+user.getPhone()+"进行咨询");
            return message;
        }

        //填充订单信息
        order.setUserId(userId);
        order.setOrderCondition(false);
        LocalDateTime localDateTime = LocalDateTime.now();
        order.setDealTime(localDateTime);

        //封装返回信息
        Message message = new Message();
        //生成订单
        int insert = orderDetailMapper.insert(order);
        if (insert==0)
            message.setMessage("订购失败！");
        else message.setMessage("订购成功！");
        return message;
    }

    @Override
    public List<ShowOrder> queryOrders(int userId) {
        return orderDetailMapper.queryOrders(userId);
    }

    @Override
    public List<ShowOrder> queryOrdersByLandlordId(int userId) {
        return orderDetailMapper.queryOrdersByLandlordId(userId);
    }

    @Override
    public Message tuiFang(int orderId) {
        Message message = new Message();
        //获取订单和房间
        OrderDetail orderDetail = orderDetailMapper.selectById(orderId);
        Room room = roomMapper.selectById(orderDetail.getRoomId());
        //更改房间表的房间状态
        room.setRoomCondition(true);
        int roomUpdate = roomMapper.updateById(room);
        //更改订单表的房间状态
        OrderDetail update = orderDetail.setOrderCondition(true);
        int orderUpdated = orderDetailMapper.updateById(update);
        if (roomUpdate==1 && orderUpdated ==1)
            message.setMessage("退房成功！您可以进行评论");
        else message.setMessage("退房失败！");
        return message;
    }

    @Override
    public Message clear(int roomId) {
        Message message = new Message();
        //将room_condition改为false
        Room room = roomMapper.selectById(roomId);
        room.setRoomCondition(false);
        int update = roomMapper.updateById(room);
        if (update==1){
            message.setMessage("清扫房间成功！可以安排入住");
            return message;
        }else{
            message.setMessage("操作失败！");
        }
        return message;
    }
}
