package com.will.homestay.controller;


import ch.qos.logback.core.model.Model;
import com.will.homestay.entity.Desire;
import com.will.homestay.entity.Message;
import com.will.homestay.entity.Room;
import com.will.homestay.entity.User;
import com.will.homestay.pojo.ShowDesire;
import com.will.homestay.service.DesireService;
import com.will.homestay.service.RoomService;
import com.will.homestay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
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
@RequestMapping("/homestay/desire")
public class DesireController {
    @Autowired
    UserService userService;
    @Autowired
    DesireService desireService;
    @Autowired
    RoomService roomService;
    @RequestMapping("/addDesire")
    public ModelAndView addDesire(int roomId){
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        int userId = userService.getUserIdByName(SecurityContextHolder.getContext().getAuthentication().getName());
        Message message = desireService.addDesire(roomId, userId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("message",message.getMessage());
        mv.addObject("user",user);
        mv.addObject("room",roomService.queryRoomById(roomId));
        mv.setViewName("/user/product_details");
        return mv;
    }

    @RequestMapping("/showMyDesires")
    public ModelAndView showMyDesires(){
        Message message = new Message();
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        int userId = userService.getUserIdByName(SecurityContextHolder.getContext().getAuthentication().getName());
        //查询心愿单
        List<Desire> desires = desireService.showMyDesires(userId);
        //创建心愿单显示对象
        List<ShowDesire> showDesireList = new ArrayList<>();
        for (Desire desire:desires){
            ShowDesire showDesire = new ShowDesire();
            Room room = roomService.queryRoomById(desire.getRoomId());
            showDesire.setDesireId(desire.getDesireId());
            showDesire.setUserId(desire.getUserId());
            showDesire.setRoomId(desire.getRoomId());
            showDesire.setRoomAddress(room.getRoomAddress());
            showDesire.setPic(room.getPic());
            showDesireList.add(showDesire);
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("desires",showDesireList);
        mv.addObject("user",user);
        mv.addObject("message",message.getMessage());
        mv.setViewName("/user/desire_list");
        return mv;
    }

    @RequestMapping("/deleteDesire")
    public ModelAndView deleteDesire(int desireId){
        Message message = desireService.deleteDesire(desireId);
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        int userId = userService.getUserIdByName(SecurityContextHolder.getContext().getAuthentication().getName());
        //查询心愿单
        List<Desire> desires = desireService.showMyDesires(userId);
        //创建心愿单显示对象
        List<ShowDesire> showDesireList = new ArrayList<>();
        for (Desire desire:desires){
            ShowDesire showDesire = new ShowDesire();
            Room room = roomService.queryRoomById(desire.getRoomId());
            showDesire.setDesireId(desire.getDesireId());
            showDesire.setUserId(desire.getUserId());
            showDesire.setRoomId(desire.getRoomId());
            showDesire.setRoomAddress(room.getRoomAddress());
            showDesire.setPic(room.getPic());
            showDesireList.add(showDesire);
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",user);
        mv.addObject("desires",showDesireList);
        mv.addObject("message",message.getMessage());
        mv.setViewName("/user/desire_list");
        return mv;
    }

}
