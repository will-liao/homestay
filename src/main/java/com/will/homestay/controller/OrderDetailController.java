package com.will.homestay.controller;


import com.will.homestay.entity.Message;
import com.will.homestay.entity.OrderDetail;
import com.will.homestay.entity.Room;
import com.will.homestay.entity.User;
import com.will.homestay.pojo.ShowComments;
import com.will.homestay.service.OrderCommentService;
import com.will.homestay.service.OrderDetailService;
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
@RequestMapping("/homestay/order-detail")
public class OrderDetailController {
    @Autowired
    RoomService roomService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    OrderCommentService orderCommentService;
    @Autowired
    UserService userService;
    @RequestMapping("/toRoomDetail")
    public ModelAndView toRoomDetail(@RequestParam("roomId") int roomId){
        //根据id获取所选的民宿
        Room room = roomService.queryRoomById(roomId);
        Message message = new Message();
        //封装视图对象
        ModelAndView mv = new ModelAndView();
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<ShowComments> roomComment = orderCommentService.getRoomComment(roomId);
        mv.addObject("roomComments",roomComment);
        mv.addObject("user",user);
        mv.addObject("room",room);
        mv.addObject("message",message.getMessage());
        mv.setViewName("/user/product_details");
        return mv;
    }
    @RequestMapping("/orderRoom")
    public ModelAndView orderRoom(OrderDetail order){
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        int userId = userService.getUserIdByName(SecurityContextHolder.getContext().getAuthentication().getName());
        Message message = orderDetailService.order(order,userId);

        //返回信息
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",user);
        mv.addObject("message",message.getMessage());
        mv.addObject("room",roomService.queryRoomById(order.getRoomId()));
        mv.setViewName("/user/product_details");
        return mv;
    }

}
