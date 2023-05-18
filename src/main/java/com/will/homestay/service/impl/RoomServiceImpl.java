package com.will.homestay.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.will.homestay.entity.Message;
import com.will.homestay.entity.Room;
import com.will.homestay.mapper.OrderDetailMapper;
import com.will.homestay.mapper.RoomMapper;
import com.will.homestay.service.DesireService;
import com.will.homestay.service.RoomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.will.homestay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
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
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {
    @Autowired
    RoomMapper roomMapper;
    @Autowired
    OrderDetailMapper orderDetailMapper;
    @Autowired
    UserService userService;
    @Autowired
    DesireService desireService;
    @Override
    public Message uploadRoom(Room room, MultipartFile pic) {
        try {
            if (pic.getSize()>0){
                String path = "E:\\file";
                String filename = pic.getOriginalFilename();
                File targetFile = null;
                if (filename != null) {
                    targetFile = new File(path,filename);
                }
                pic.transferTo(targetFile);//持久化到磁盘
                room.setPic(filename);//设置器材图片名称
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Message message = new Message();
        int insert = roomMapper.insert(room);
        if (insert==0)
            message.setMessage("上传失败！");
        else message.setMessage("上传成功！");
        return message;
    }

    @Override
    public List<Room> queryMyRooms() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("landlord_id",userService.getUserIdByName(username));
        return roomMapper.selectList(wrapper);
    }

    @Override
    public List<Room> queryRooms() {
        //mybatis-puls查询所有民宿
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("room_condition",0);
        return roomMapper.selectList(wrapper);
    }

    @Override
    public List<Room> queryRoomsOrderByPriceDesc() {
        //mybatis-puls查询所有民宿，并根据民宿价格降序排序
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("room_price");
        wrapper.eq("room_condition",0);
        return roomMapper.selectList(wrapper);
    }

    @Override
    public List<Room> queryRoomsOrderByPriceAsc() {
        //mybatis-puls查询所有民宿，并根据民宿价格降序排序
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByAsc("room_price");
        wrapper.eq("room_condition",0);
        return orderDetailMapper.selectList(wrapper);
    }

    @Override
    public List<Room> bestSellTop(int num) {
        //mybatis-puls查询订单表，根据民宿id分组，统计每个民宿的销量，根据销量降序排序，并展示前六个民宿
        return roomMapper.bestSellTop(num);
    }

    @Override
    public Room queryRoomById(int roomId) {
        return roomMapper.selectById(roomId);
    }

    @Override
    public Room queryRoomByAddress(String roomAddress) {
        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq("room_address",roomAddress);
        return roomMapper.selectOne(wrapper);
    }


    @Override
    public Message updateRoom(Room room,MultipartFile pic) {
        try {
            if (pic.getSize()>0){
                String path = "E:\\file";
                String filename = pic.getOriginalFilename();
                File targetFile = new File(path,filename);
                pic.transferTo(targetFile);
                room.setPic(filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Message message = new Message();
        int update = roomMapper.updateById(room);
        if (update==0)
            message.setMessage("更新失败！");
        else message.setMessage("更新成功！");
        return message;
    }

    @Override
    public Message deleteRoom(int roomId) {
        //删除心愿单里的名宿
        int desireId = desireService.deleteDesireByRoomId(roomId);

        Message message = new Message();
        int delete = roomMapper.deleteById(roomId);
        if (delete==0 && desireId == 0)
            message.setMessage("删除失败！");
        else message.setMessage("删除成功！");
        return message;
    }

    @Override
    public List<Room> filterRoom(String roomType, double minPrice, double maxPrice, Date startTime, Date endTime) {
        return roomMapper.filterRoom(roomType,minPrice,maxPrice,startTime,endTime);
    }

}
