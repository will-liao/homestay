package com.will.homestay.service.impl;

import com.will.homestay.entity.AdditionalFees;
import com.will.homestay.entity.Message;
import com.will.homestay.entity.Room;
import com.will.homestay.mapper.AdditionalFeesMapper;
import com.will.homestay.mapper.RoomMapper;
import com.will.homestay.pojo.ShowAdditional;
import com.will.homestay.service.AdditionalFeesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.will.homestay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class AdditionalFeesServiceImpl extends ServiceImpl<AdditionalFeesMapper, AdditionalFees> implements AdditionalFeesService {
    @Autowired
    AdditionalFeesMapper additionalFeesMapper;
    @Autowired
    RoomMapper roomMapper;
    @Autowired
    UserService userService;

    @Override
    public List<AdditionalFees> queryAllAdditional() {
        return additionalFeesMapper.selectList(null);
    }

    @Override
    public List<ShowAdditional> showRoomAdditional() {
        return additionalFeesMapper.showRoomAdditional(userService.getUserIdByName(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @Override
    public Message addAdditional(Room room, AdditionalFees additionalFees) {
        Message message = new Message();
        additionalFees.setRoomId(room.getRoomId());
        int insert = additionalFeesMapper.insert(additionalFees);
        if (insert==0)
            message.setMessage("添加失败！");
        else message.setMessage("添加成功！");
        return message;
    }
}
