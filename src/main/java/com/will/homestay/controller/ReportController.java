package com.will.homestay.controller;


import com.will.homestay.entity.DayData;
import com.will.homestay.entity.MouthData;
import com.will.homestay.entity.User;
import com.will.homestay.service.DayDataService;
import com.will.homestay.service.MouthDataService;
import com.will.homestay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/homestay/report")
public class ReportController {
    @Autowired
    DayDataService dayDataService;
    @Autowired
    MouthDataService mouthDataService;
    @Autowired
    UserService userService;

    @RequestMapping("/getReport")
    public ModelAndView getDayReport(){
        //获取当前用户
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        //获取日报表
        List<DayData> dayData = dayDataService.getAllDayData();
        //获取月报表
        List<MouthData> mouthData = mouthDataService.getAllMouthData();
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",user);
        mv.addObject("dayData",dayData);
        mv.addObject("mouthData",mouthData);
        mv.setViewName("manager/report");
        return mv;
    }

}
