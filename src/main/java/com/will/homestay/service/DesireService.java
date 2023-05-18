package com.will.homestay.service;

import com.will.homestay.entity.Desire;
import com.baomidou.mybatisplus.extension.service.IService;
import com.will.homestay.entity.Message;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
public interface DesireService extends IService<Desire> {
    Message addDesire(int roomId,int userId);

    List<Desire> showMyDesires(int userId);

    Message deleteDesire(int desireId);
    int deleteDesireByRoomId(int roomId);
}
