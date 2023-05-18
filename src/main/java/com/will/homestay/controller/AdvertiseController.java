package com.will.homestay.controller;


import com.will.homestay.entity.Advertise;
import com.will.homestay.entity.Message;
import com.will.homestay.entity.User;
import com.will.homestay.service.AdvertiseService;
import com.will.homestay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
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
@RequestMapping("/homestay/advertise")
public class AdvertiseController {
    @Autowired
    AdvertiseService  advertiseService;
    @Autowired
    UserService userService;
    //获取当前用户信息
    @RequestMapping("/getAllAdvertise")
    public ModelAndView getAllAdvertise(){
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        //获取所有广告
        ModelAndView mv = new ModelAndView();
        List<Advertise> advertises = advertiseService.getAllAdvertise();
        mv.addObject("advertises",advertises);
        mv.addObject("user",user);
        mv.setViewName("manager/advertise-manage");
        return mv;
    }

    @RequestMapping("/updateAdvertise")
    public ModelAndView updateAdvertise(Advertise advertise, MultipartFile file){
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        //更新广告
        Message message = advertiseService.updateAdvertise(advertise,file);
        //获取所有广告
        ModelAndView mv = new ModelAndView();
        List<Advertise> advertises = advertiseService.getAllAdvertise();
        mv.addObject("advertises",advertises);
        mv.addObject("message",message.getMessage());
        mv.addObject("user",user);
        mv.setViewName("manager/advertise-manage");
        return mv;
    }
}
