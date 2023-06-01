package com.will.homestay.controller;

import com.will.homestay.entity.Message;
import com.will.homestay.entity.User;
import com.will.homestay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RounterController {
    @Autowired
    UserService userService;
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }
    @RequestMapping("/toRegester")
    public ModelAndView toRegester(){
        Message message = new Message();
        ModelAndView mv = new ModelAndView();
        mv.addObject("message",message);
        mv.setViewName("registration");
        return mv;
    }

}
