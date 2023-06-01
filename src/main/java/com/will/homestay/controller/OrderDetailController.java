package com.will.homestay.controller;


import com.will.homestay.entity.*;
import com.will.homestay.pojo.ShowComments;
import com.will.homestay.service.*;
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
    @Autowired
    OverviewService overviewService;
    @RequestMapping("/toRoomDetail")
    public ModelAndView toRoomDetail(@RequestParam("roomId") int roomId){
        //根据id获取所选的民宿
        Room room = roomService.queryRoomById(roomId);
        Message message = new Message();
        //封装视图对象
        ModelAndView mv = new ModelAndView();
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<ShowComments> roomComment = orderCommentService.getRoomComment(roomId);
        Double avg_rate = orderCommentService.avg_rate(roomId);
        if (avg_rate==null)
            avg_rate = 3.0;
        mv.addObject("roomComments",roomComment);
        mv.addObject("user",user);
        mv.addObject("room",room);
        mv.addObject("message",message.getMessage());
        mv.addObject("avg_rate",avg_rate);
        mv.setViewName("/user/product_details");
        return mv;
    }

    @RequestMapping("/getAddress")
    public ModelAndView getAddress(){
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        int userId = userService.getUserIdByName(SecurityContextHolder.getContext().getAuthentication().getName());
        //返回信息
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",user);
        mv.setViewName("/user/address");
        return mv;
    }

    @RequestMapping("/conform")
    public ModelAndView conform(OrderDetail order){
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        int userId = userService.getUserIdByName(SecurityContextHolder.getContext().getAuthentication().getName());

        //返回信息
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",user);
        mv.addObject("room",roomService.queryRoomById(order.getRoomId()));
        mv.addObject("order",order);
        mv.setViewName("/user/order_conform");
        return mv;
    }
    @RequestMapping("/orderRoom")
    public ModelAndView orderRoom(OrderDetail order){
        ModelAndView mv = new ModelAndView();
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        int userId = userService.getUserIdByName(SecurityContextHolder.getContext().getAuthentication().getName());
        if (roomService.judgeBook(order.getRoomId(),order.getStartTime(),order.getEndTime())){
            //根据userId获取用户信息
            Message message = new Message();
            message.setMessage("该时间段不可预定，请重新选择时间段");
            mv.addObject("message",message.getMessage());
            mv.setViewName("支付结果");
            return mv;
        }
        Message message = orderDetailService.order(order, userId);
        //更新overview
        Overview overview = overviewService.getOverview();
        overview.setTotalOrder(overview.getTotalOrder()+1);
        overview.setTotalIncome(overview.getTotalIncome().add(order.getPay()));
        overviewService.updateOverview(overview);

        //返回信息

        mv.addObject("message",message.getMessage());
        mv.setViewName("支付结果");
        return mv;
    }

}
