package com.will.homestay.controller;


import com.will.homestay.entity.AdmFeedback;
import com.will.homestay.pubsub.Publish;
import com.will.homestay.service.AdmFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
@Controller
@RequestMapping("/homestay/adm-feedback")
public class AdmFeedbackController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private Publish publish;
    @Autowired
    AdmFeedbackService admFeedbackService;

    //发布房东公告控制器
    @RequestMapping("/publish")
    public String publish(@RequestParam("feedback") String feedback) {
        publish.publish(feedback,"admin");
        return "redirect:/homestay/user/toManager-user-index";
    }

    @RequestMapping("reply")
    public String reply(AdmFeedback feedback) {
        System.out.println(feedback+"进入reply");
        if (admFeedbackService.whetherFeedback(feedback.getOrderId())) {
            admFeedbackService.updateFeedback(feedback);
        }else {
            admFeedbackService.insertFeedback(feedback);
        }
        return "redirect:/getMessage2";
    }

    @RequestMapping("/getMessage")
    @ResponseBody
    public String getFeedback() {

        System.out.println("进入getFeedback");
        return "";
    }
}
