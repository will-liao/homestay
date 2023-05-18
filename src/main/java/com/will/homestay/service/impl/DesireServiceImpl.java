package com.will.homestay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.will.homestay.entity.Desire;
import com.will.homestay.entity.Message;
import com.will.homestay.mapper.DesireMapper;
import com.will.homestay.service.DesireService;
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
public class DesireServiceImpl extends ServiceImpl<DesireMapper, Desire> implements DesireService {
    @Autowired
    DesireMapper desireMapper;

    @Override
    public Message addDesire(int roomId,int userId) {
        Message message = new Message();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("room_id",roomId);
        List<Desire> list = desireMapper.selectMaps(queryWrapper);
        System.out.println(list);
        if (list!=null && list.size()!=0){
            message.setMessage("您已收藏过该民宿");
            return message;
        }

        Desire desire = new Desire();
        desire.setUserId(userId);
        desire.setRoomId(roomId);
        int insert = desireMapper.insert(desire);
        if (insert==0)
            message.setMessage("收藏失败！");
        else message.setMessage("收藏成功！");
        return message;
    }

    @Override
    public List<Desire> showMyDesires(int userId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",userId);
        return desireMapper.selectList(wrapper);
    }

    @Override
    public Message deleteDesire(int desireId) {
        Message message = new Message();
        int delete = desireMapper.deleteById(desireId);
        if (delete==0)
            message.setMessage("删除收藏失败！");
        else message.setMessage("删除收藏成功！");
        return message;
    }

    @Override
    public int deleteDesireByRoomId(int roomId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("room_id",roomId);
        return desireMapper.delete(queryWrapper);
    }
}
