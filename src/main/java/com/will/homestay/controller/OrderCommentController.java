package com.will.homestay.controller;


import ch.qos.logback.core.model.Model;
import com.will.homestay.entity.Message;
import com.will.homestay.entity.OrderComment;
import com.will.homestay.entity.User;
import com.will.homestay.pojo.ShowComments;
import com.will.homestay.pojo.ShowOrder;
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

import java.time.LocalDateTime;
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
@RequestMapping("/homestay/order-comment")
public class OrderCommentController {
    @Autowired
    UserService userService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    RoomService roomService;
    @Autowired
    OrderCommentService orderCommentService;
    @RequestMapping("/comment")
    public ModelAndView comment(OrderComment orderComment,@RequestParam("comment") String comment){
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        //根据orderId判断是否已经评论过
        OrderComment orderComment1 = orderCommentService.queryCommentByOrderId(orderComment.getOrderId());
        if (orderComment1!=null){
            ModelAndView mv = new ModelAndView();
            List<ShowOrder> orders = orderDetailService.queryOrders(orderComment.getUserId());
            mv.addObject("orders",orders);
            mv.addObject("user",user);
            mv.addObject("message","您已经评论过了！");
            mv.setViewName("/user/order_list");
            return mv;
        }
        //插入评论
        LocalDateTime localDateTime = LocalDateTime.now();
        orderComment.setTime(localDateTime);
        orderComment.setContent(comment);
        Message message = orderCommentService.addComment(orderComment);

        ModelAndView mv = new ModelAndView();
        List<ShowOrder> orders = orderDetailService.queryOrders(orderComment.getUserId());
        mv.addObject("orders",orders);
        mv.addObject("user",user);
        mv.addObject("message",message.getMessage());
        mv.setViewName("/user/order_list");
        return mv;
    }
}
