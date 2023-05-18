package com.will.homestay.service;

import com.will.homestay.entity.AdditionalFees;
import com.baomidou.mybatisplus.extension.service.IService;
import com.will.homestay.entity.Message;
import com.will.homestay.entity.Room;
import com.will.homestay.pojo.ShowAdditional;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
public interface AdditionalFeesService extends IService<AdditionalFees> {
    List<AdditionalFees> queryAllAdditional();
    List<ShowAdditional> showRoomAdditional();
    Message addAdditional(Room room, AdditionalFees additionalFees);
}
