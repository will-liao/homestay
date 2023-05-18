package com.will.homestay.service.impl;

import com.will.homestay.entity.Message;
import com.will.homestay.entity.OrderComment;
import com.will.homestay.mapper.OrderCommentMapper;
import com.will.homestay.pojo.ShowComments;
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
    public List<ShowComments> getRoomComment(int roomId) {
        return commentMapper.getRoomComment(roomId);
    }
}
