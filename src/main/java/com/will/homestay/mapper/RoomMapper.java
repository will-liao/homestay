package com.will.homestay.mapper;

import com.will.homestay.entity.Room;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
public interface RoomMapper extends BaseMapper<Room> {
    List<Room> filterRoom(String roomType, double minPrice, double maxPrice, Date startTime, Date endTime);
    List<Room> judgeBook( Date startTime, Date endTime);

    List<Room> bestSellTop(int number);
    List<Room> bestRate(int number);
}
