package com.will.homestay.service;

import com.will.homestay.entity.Message;
import com.will.homestay.entity.OrderComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.will.homestay.pojo.Avg_rate;
import com.will.homestay.pojo.ShowComments;
import com.will.homestay.pojo.ShowLandlordComments;

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

    OrderComment queryCommentByOrderId(Integer orderId);

    List<ShowLandlordComments> getComments(int landlordId);

    List<ShowComments> getRoomComment(int roomId);

    Double avg_rate(int roomId);
    List<Avg_rate> avg_allRoom();
}
