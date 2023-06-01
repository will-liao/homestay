package com.will.homestay.controller;


import ch.qos.logback.core.model.Model;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.will.homestay.entity.*;
import com.will.homestay.pojo.Avg_rate;
import com.will.homestay.pojo.ShowRoom;
import com.will.homestay.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
@Controller
@RequestMapping("/homestay/room")
public class RoomController {
    @Autowired
    RoomService roomService;
    @Autowired
    UserService userService;
    @Autowired
    AdvertiseService advertiseService;
    @Autowired
    OrderCommentService orderCommentService;
    @Autowired
    OverviewService overviewService;

    @RequestMapping("/toUploadRoom")
    public String toUploadRoom(){
        return "/landlord/upload-room";
    }
    //上传房屋
    @RequestMapping("/uploadRoom")
    public ModelAndView uploadRoom(Room room,@RequestParam("file") MultipartFile pic){
        //给民宿添加用户信息
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        room.setLandlordId(userService.getUserIdByName(userDetails.getUsername()));//添加房东id
        room.setRoomCondition(false);//预定状况

        ModelAndView mv = new ModelAndView();
        Message message = roomService.uploadRoom(room,pic);
        //更新overview
        Overview overview = overviewService.getOverview();
        overview.setRoomNum(overview.getRoomNum()+1);
        overviewService.updateOverview(overview);

        mv.addObject("message",message.getMessage());
        mv.setViewName("/landlord/upload-room");
        return mv;
    }
    //展示我的房屋
    @RequestMapping("/toRoomList")
    public ModelAndView toRoomList(){
        ModelAndView mv = new ModelAndView();
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Room> myRooms = roomService.queryMyRooms();
        mv.addObject("myRooms",myRooms);
        mv.addObject("user",user);
        mv.addObject("message","");
        mv.setViewName("/landlord/room-list");
        return mv;
    }
    @RequestMapping("/toUpdateRoom")
    public ModelAndView toUpdateRoom(int roomId){
        Room room = roomService.queryRoomById(roomId);
        ModelAndView mv = new ModelAndView();
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        mv.addObject("user",user);
        mv.addObject("room",room);
        mv.addObject("message","");
        mv.setViewName("/landlord/update-room");
        return mv;
    }
    @RequestMapping("/updateRoom")
    public ModelAndView updateRoom(Room room,@RequestParam("file") MultipartFile pic){
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Message message = roomService.updateRoom(room, pic);
        ModelAndView mv = new ModelAndView();
        List<Room> myRooms = roomService.queryMyRooms();
        mv.addObject("user",user);
        mv.addObject("myRooms",myRooms);
        mv.addObject("message",message.getMessage());
        mv.setViewName("/landlord/room-list");
        return mv;
    }

    @RequestMapping("/deleteRoom")
    public ModelAndView deleteRoom(int roomId){
        ModelAndView mv = new ModelAndView();
        Message message = roomService.deleteRoom(roomId);
        List<Room> myRooms = roomService.queryMyRooms();
        mv.addObject("myRooms",myRooms);
        mv.addObject("message",message.getMessage());
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        mv.addObject("user",user);
        mv.setViewName("/landlord/room-list");
        return mv;
    }

    @RequestMapping("/filterRoom")
    public ModelAndView filterRoom(@RequestParam(defaultValue = "一室一厅") String roomType, @RequestParam(defaultValue = "0.0")double minPrice, @RequestParam(defaultValue = "99999.0")double maxPrice,
                                   @RequestParam(name = "startTime",required = false,defaultValue = "2080-06-17 00:27:16")Date startTime,@RequestParam(name = "endTime",required = false,defaultValue = "2090-06-17 00:27:17") Date endTime) {
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        List<Room> rooms = roomService.filterRoom(roomType, minPrice, maxPrice, startTime, endTime);
        ModelAndView mv = new ModelAndView();
        List<Room> top3Room = roomService.bestSellTop(3);
        //返回视图
        List<Advertise> advertises = advertiseService.getAllAdvertise();
        mv.addObject("advertises", advertises);
        mv.addObject("top3Room", top3Room);
        mv.addObject("rooms", rooms);
        mv.addObject("user", user);
        mv.setViewName("/user/common-user-index");
        return mv;
    }
    @RequestMapping("/order")
    public ModelAndView order(int orderOption){
        ModelAndView mv = new ModelAndView();
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        mv.addObject("user",user);
        List<Advertise> advertises = advertiseService.getAllAdvertise();
        mv.addObject("advertises", advertises);


        switch (orderOption){
            case 0:
                List<Room> rooms0 = roomService.queryRoomsOrderByPriceDesc();
                List<Room> top3Room = roomService.bestSellTop(3);
                mv.addObject("top3Room", top3Room);
                mv.addObject("rooms", rooms0);
                mv.setViewName("/user/common-user-index");
                return mv;
            case 1:
                List<Room> rooms1 = roomService.queryRoomsOrderByPriceAsc();
                List<Room> top3Room1 = roomService.bestSellTop(3);
                mv.addObject("top3Room", top3Room1);
                mv.addObject("rooms", rooms1);
                mv.setViewName("/user/common-user-index");
                return mv;
            case 2:
                List<Room> rooms2 = roomService.bestSellTop(600);
                List<Room> top3Room2 = roomService.bestSellTop(3);
                mv.addObject("rooms", rooms2);
                mv.addObject("top3Room", top3Room2);
                mv.setViewName("/user/common-user-index");
                return mv;

            case 3:
                List<Room> rooms3 = roomService.bestRate(600);
                List<Room> top3Room3 = roomService.bestSellTop(3);
                mv.addObject("rooms", rooms3);
                mv.addObject("top3Room", top3Room3);
                mv.setViewName("/user/common-user-index");
                return mv;
            default:
                List<Room> rooms4 = roomService.queryRooms();
                mv.addObject("rooms", rooms4);
                mv.setViewName("/user/common-user-index");
                return mv;
        }
    }

    //获取所有房间
    @RequestMapping("/getAllRooms")
    public ModelAndView getAllRooms(@RequestParam(name = "pageNum1",required = false ,defaultValue = "1") Integer pageNum1){
        if (pageNum1<1){//判断分页的下界，上届的判断在前端页面处，若当前页大于总页数，则设置当前页为尾页
            //pageInfo.getCurrent()<pageInfo.getPages()?pageInfo.getCurrent()+1:pageInfo.getPages()
            pageNum1 = 1;
        }
        ModelAndView mv = new ModelAndView();
        IPage<Room> pageInfo1 = roomService.queryAllRoom(new Page(pageNum1, 7));
        List<Room> rooms_ = pageInfo1.getRecords();
        //获取所有房间的评分
        List<Avg_rate> avgRates = orderCommentService.avg_allRoom();
        List<ShowRoom> rooms = new ArrayList<>();
        //将rooms_和avgRates根据roomId进行匹配，将评分加入rooms中
        for (Room room:rooms_){
            for (Avg_rate avg_rate:avgRates){
                if (room.getRoomId()==avg_rate.getRoomId()){
                    ShowRoom showRoom = new ShowRoom();
                    showRoom.setRoomId(room.getRoomId());
                    showRoom.setRoomSize(room.getRoomSize());
                    showRoom.setRoomType(room.getRoomType());
                    showRoom.setRoomPrice(room.getRoomPrice());
                    showRoom.setRoomAddress(room.getRoomAddress());
                    showRoom.setPic(room.getPic());
                    showRoom.setAvgRate(avg_rate.getAvgRate());
                    rooms.add(showRoom);
                }
            }
        }

        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        mv.addObject("pageInfo1",pageInfo1);
        mv.addObject("user",user);
        mv.addObject("rooms",rooms);
        mv.setViewName("/manager/room-list");
        return mv;
    }
}
