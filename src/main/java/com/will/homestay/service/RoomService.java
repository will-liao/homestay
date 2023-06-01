package com.will.homestay.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.will.homestay.entity.Message;
import com.will.homestay.entity.Room;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
public interface RoomService extends IService<Room> {
    Message uploadRoom(Room room, MultipartFile pic);
    List<Room> queryMyRooms();
    List<Room> queryRooms();
    IPage<Room> queryAllRoom(Page page);
    List<Room> queryRoomsOrderByPriceDesc();
    List<Room> queryRoomsOrderByPriceAsc();
    List<Room> bestSellTop(int number);
    List<Room> bestRate(int number);
    Room queryRoomById(int roomId);
    Room queryRoomByAddress(String roomAddress);
    Message updateRoom(Room room,MultipartFile pic);
    Message deleteRoom(int roomId);
    List<Room> filterRoom(String roomType, double minPrice, double maxPrice, Date startTime,Date endTime);
    Boolean judgeBook(int roomId, Date startTime, Date endTime);

}
