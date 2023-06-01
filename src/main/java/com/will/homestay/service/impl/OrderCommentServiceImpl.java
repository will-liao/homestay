package com.will.homestay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.will.homestay.entity.Message;
import com.will.homestay.entity.OrderComment;
import com.will.homestay.mapper.OrderCommentMapper;
import com.will.homestay.pojo.Avg_rate;
import com.will.homestay.pojo.ShowComments;
import com.will.homestay.pojo.ShowLandlordComments;
import com.will.homestay.service.OrderCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class OrderCommentServiceImpl extends ServiceImpl<OrderCommentMapper, OrderComment> implements OrderCommentService {
    @Autowired
    OrderCommentMapper commentMapper;

    @Override
    public Message addComment(OrderComment comment) {
        Message message = new Message();
        int insert = commentMapper.insert(comment);
        if (insert==0)
            message.setMessage("发表评论失败！");
        else message.setMessage("发表评论成功！");
        return message;
    }

    @Override
    public OrderComment queryCommentByOrderId(Integer orderId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("order_id",orderId);
        return commentMapper.selectCount(wrapper)>0?commentMapper.selectOne(wrapper):null;
    }

    @Override
    public List<ShowLandlordComments> getComments(int landlordId) {
        return commentMapper.getComments(landlordId);
    }

    @Override
    public List<ShowComments> getRoomComment(int roomId) {
        return commentMapper.getRoomComment(roomId);
    }

    @Override
    public Double avg_rate(int roomId) {
        return commentMapper.avg_rate(roomId);
    }

    @Override
    public List<Avg_rate> avg_allRoom() {
        return commentMapper.avg_allRoom();
    }
}
