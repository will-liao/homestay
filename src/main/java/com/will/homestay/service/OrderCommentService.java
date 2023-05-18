package com.will.homestay.service;

import com.will.homestay.entity.Message;
import com.will.homestay.entity.OrderComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.will.homestay.pojo.ShowComments;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
public interface OrderCommentService extends IService<OrderComment> {
    Message addComment(OrderComment comment);

    List<ShowComments> getRoomComment(int roomId);
}
