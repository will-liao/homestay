package com.will.homestay.controller;

import com.will.homestay.entity.AdmFeedback;
import com.will.homestay.entity.Message;
import com.will.homestay.entity.User;
import com.will.homestay.pojo.ShowComments;
import com.will.homestay.pojo.ShowLandlordComments;
import com.will.homestay.service.AdmFeedbackService;
import com.will.homestay.service.OrderCommentService;
import com.will.homestay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

@Controller
public class MessageController {
    @Autowired
    UserService userService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    AdmFeedbackService admFeedbackService;
    @Autowired
    OrderCommentService orderCommentService;

    @RequestMapping("/getMessage")
    public ModelAndView getMessage(){
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        //从redis数据库中获取feedback集合
        List<AdmFeedback> feedbacks = admFeedbackService.getFeedbacks();

        ModelAndView mv = new ModelAndView();
        mv.addObject("user",user);
        mv.addObject("feedbacks",feedbacks);
        mv.setViewName("landlord/messageRoom");
        return mv;
    }
    @RequestMapping("/getMessage2")
    public ModelAndView getMessage2(){
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        //获取该房东的所有房间评论
        List<ShowLandlordComments> comments = orderCommentService.getComments(user.getUserId());
        System.out.println(comments);
        //获取
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",user);
        mv.addObject("comments",comments);

        mv.setViewName("landlord/messageRoom2");
        return mv;
    }

}
