package com.will.homestay.controller;

import com.will.homestay.entity.Message;
import com.will.homestay.entity.OrderDetail;
import com.will.homestay.entity.User;
import com.will.homestay.pojo.ShowOrder;
import com.will.homestay.service.OrderDetailService;
import com.will.homestay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/homestay/order")
public class OrderController {
    @Autowired
    UserService userService;
    @Autowired
    OrderDetailService orderDetailService;
    @RequestMapping("/showOrders")
    public ModelAndView showOrders(){
        ModelAndView mv = new ModelAndView();
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        int userId = userService.getUserIdByName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<ShowOrder> orders = orderDetailService.queryOrders(userId);
        mv.addObject("orders",orders);
        mv.addObject("user",user);
        mv.addObject("message","");
        mv.setViewName("/user/order_list");
        return mv;
    }
    @RequestMapping("/tuiFang")
    public ModelAndView tuiFang(@RequestParam("orderId") int orderId,@RequestParam("userId") int userId){
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        ModelAndView mv = new ModelAndView();
        Message message = orderDetailService.tuiFang(orderId);
        List<ShowOrder> orders = orderDetailService.queryOrders(userId);
        mv.addObject("orders",orders);
        mv.addObject("user",user);
        mv.addObject("message",message.getMessage());
        mv.setViewName("/user/order_list");
        return mv;
    }

    @RequestMapping("/showOrdersbyLandlordId")
    public ModelAndView showOrdersbyLandlordId(){
        ModelAndView mv = new ModelAndView();
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        int userId = userService.getUserIdByName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<ShowOrder> orders = orderDetailService.queryOrdersByLandlordId(userId);
        mv.addObject("orders",orders);
        mv.addObject("user",user);
        mv.addObject("message","");
        mv.setViewName("/landlord/order_list");
        return mv;
    }

    @RequestMapping("/clear")
    public ModelAndView clear(@RequestParam("roomId") int roomId ,@RequestParam("userId") int userId){
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        ModelAndView mv = new ModelAndView();
        Message message = orderDetailService.clear(roomId);
        List<ShowOrder> orders = orderDetailService.queryOrdersByLandlordId(userId);
        mv.addObject("orders",orders);
        mv.addObject("user",user);
        mv.addObject("message",message.getMessage());
        mv.setViewName("/landlord/order_list");
        return mv;
    }
}
