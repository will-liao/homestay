package com.will.homestay.controller;


import com.will.homestay.entity.AdditionalFees;
import com.will.homestay.entity.Message;
import com.will.homestay.entity.Room;
import com.will.homestay.entity.User;
import com.will.homestay.pojo.ShowAdditional;
import com.will.homestay.service.AdditionalFeesService;
import com.will.homestay.service.RoomService;
import com.will.homestay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
@RequestMapping("/homestay/additional-fees")
public class AdditionalFeesController {


    @Autowired
    RoomService roomService;
    @Autowired
    AdditionalFeesService additionalFeesService;
    @Autowired
    UserService userService;

    @RequestMapping("/toAdditional")
    public ModelAndView toAdditional(){
        //获取房间信息和其附加物品信息
        List<ShowAdditional> showAdditionals = additionalFeesService.showRoomAdditional();
        System.out.println(showAdditionals);
        ModelAndView mv = new ModelAndView();
        List<Room> rooms = roomService.queryMyRooms();
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        mv.addObject("user",user);
        mv.addObject("rooms",rooms);
        mv.addObject("showAdditionals",showAdditionals);
        mv.setViewName("/landlord/additional-manage");
        mv.addObject("message","");
        return mv;
    }
    @RequestMapping("/add-additional")
    public ModelAndView addAdditional(@RequestParam("roomAddress") String roomAddress, AdditionalFees additional){
        //根据民宿地址获取房屋信息
        Room room = roomService.queryRoomByAddress(roomAddress);
        //给民宿添加附加物品
        Message message = additionalFeesService.addAdditional(room, additional);
        ModelAndView mv = new ModelAndView();
        //获取房间信息和其附加物品信息
        List<ShowAdditional> showAdditionals = additionalFeesService.showRoomAdditional();
        //获取房东名下的所有民宿
        List<Room> rooms = roomService.queryMyRooms();
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        //显示信息
        mv.addObject("user",user);
        mv.addObject("rooms",rooms);
        mv.addObject("showAdditionals",showAdditionals);
        mv.setViewName("/landlord/additional-manage");
        mv.addObject("message",message.getMessage());
        return mv;
    }
}
