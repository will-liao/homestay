package com.will.homestay.mapper;

import com.will.homestay.entity.Message;
import com.will.homestay.entity.OrderComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.will.homestay.pojo.Avg_rate;
import com.will.homestay.pojo.ShowComments;
import com.will.homestay.pojo.ShowLandlordComments;
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
public interface OrderCommentMapper extends BaseMapper<OrderComment> {
    List<ShowComments> getRoomComment(int roomId);
    List<ShowLandlordComments> getComments(int landlordId);

    Double avg_rate(int roomId);

    List<Avg_rate> avg_allRoom();
}
