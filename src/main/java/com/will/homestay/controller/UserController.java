package com.will.homestay.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.will.homestay.entity.*;
import com.will.homestay.pojo.ShowLandlord;
import com.will.homestay.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
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
@RequestMapping("/homestay/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RoomService roomService;
    @Autowired
    UserDetailsService detailsService;
    @Autowired
    OverviewService overviewService;
    @Autowired
    AdvertiseService advertiseService;
    @Autowired
    PingzhengService pingzhengService;

    @Autowired
    private RedisTemplate redisTemplate;
    private String feedback= "欢迎光临伴城民宿公寓出租系统";
    @RequestMapping("/toCommon-user-index")
    public ModelAndView toCommonUserIndex(@RequestParam(name = "pageNum",required = false ,defaultValue = "") Integer pageNum1){
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        ModelAndView mv = new ModelAndView();
        //展示所有房源
        List<Room> rooms = roomService.queryRooms();
        List<Room> top3Room = roomService.bestSellTop(3);
        List<Advertise> advertises = advertiseService.getAllAdvertise();
        //返回视图
        mv.addObject("top3Room", top3Room);
        mv.addObject("advertises", advertises);
        mv.addObject("rooms",rooms);
        mv.addObject("user",user);
        mv.setViewName("/user/common-user-index");
        return mv;
    }
    @RequestMapping("/toLandlord-user-index")
    public ModelAndView toLardlordIndex(){
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        ModelAndView mv = new ModelAndView();

        //订阅消息
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        connection.subscribe(new MessageListener() {
            @Override
            public void onMessage(org.springframework.data.redis.connection.Message message, byte[] pattern) {
                // 收到消息后的处理
                feedback = new String(message.getBody(), StandardCharsets.UTF_8);
                //添加到set集合中
                redisTemplate.opsForSet().add("feedback",feedback);


            }
        }, "admin".getBytes(StandardCharsets.UTF_8));

        mv.addObject("feedback", feedback);
        Integer totalUser = overviewService.totalUserByLandlordId(user.getUserId());
        Integer totalRoom = overviewService.totalRoomByLandlordId(user.getUserId());
        BigDecimal totalIncome = overviewService.totalIncomeByLandlordId(user.getUserId());
        Integer totalOrder = overviewService.totalOrderByLandlordId(user.getUserId());
        mv.addObject("totalUser",totalUser);
        mv.addObject("totalRoom",totalRoom);
        mv.addObject("totalIncome",totalIncome);
        mv.addObject("totalOrder",totalOrder);
        mv.addObject("user",user);
        mv.setViewName("/landlord/landlord-user-index");
        return mv;
    }
    @RequestMapping("/toManager-user-index")
    public ModelAndView toManagerIndex(){
        //获取用户
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        //获取Overview对象
        Overview overview = overviewService.getOverview();

        //返回视图
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",user);
        mv.addObject("overview",overview);
        mv.setViewName("/manager/manager-user-index");
        return mv;
    }
    @RequestMapping("/register")
    public ModelAndView register(User user,@RequestParam("file")MultipartFile file){
        Message registerMessage = new Message();
        Overview overview = overviewService.getOverview();
        if (user.getUserType().equals("ROLE_LANDLORD")){
            registerMessage = userService.register(user);
            Pingzheng pingzheng = new Pingzheng();
            pingzheng.setUsername(user.getUsername());
            pingzhengService.addPingZheng(pingzheng,file);


            overview.setUserNum(overview.getUserNum()+1);
            overview.setLandlordNum(overview.getLandlordNum()+1);

        }else {
            registerMessage = userService.register(user);

            overview.setUserNum(overview.getUserNum()+1);
        }
        //更新概要数据
        overviewService.updateOverview(overview);
        ModelAndView mv = new ModelAndView();
        mv.addObject("message",registerMessage);
        mv.setViewName("registration");
        return mv;
    }

    @RequestMapping("/self-message")
    public ModelAndView selfMessage(){
        ModelAndView mv = new ModelAndView();
        //获取用户
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        mv.addObject("user",user);
        mv.setViewName("/user/self-message");
        return mv;
    }
    @RequestMapping("/toResetMessage")
    public ModelAndView toResetMessage(){
        Message message = new Message();
        ModelAndView mv = new ModelAndView();
        //获取用户
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        mv.addObject("user",user);
        mv.addObject("message",message.getMessage());
        mv.setViewName("/user/reset-message");
        return mv;
    }
    @RequestMapping("/resetMessage")
    public ModelAndView resetMessage(User user,String newPassword,@RequestParam("file") MultipartFile pic){
        //获取用户
        User preUser = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setUserId(preUser.getUserId());
        Message message = new Message();
        User showUser = new User();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(passwordEncoder.matches(user.getPassword(),preUser.getPassword())){
            //修改密码
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setUserPic(preUser.getUserPic());
            message = userService.updateUser(user,pic);
            showUser = userService.getUserById(preUser.getUserId());
            detailsService.loadUserByUsername(user.getUsername());

        }else{
            message.setMessage("原密码输入不正确，请重新输入！");
            showUser = preUser;

        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",showUser);
        mv.addObject("message",message.getMessage());
        mv.setViewName("/user/reset-message");
        return mv;
    }

    //获取普通用户和房东用户信息
    @RequestMapping("/getUsers")
    public ModelAndView getUsers(@RequestParam(name = "pageNum1",required = false ,defaultValue = "1") Integer pageNum1,@RequestParam(name = "pageNum2",required = false ,defaultValue = "1") Integer pageNum2){
        if (pageNum1<1){//判断分页的下界，上届的判断在前端页面处，若当前页大于总页数，则设置当前页为尾页
            //pageInfo.getCurrent()<pageInfo.getPages()?pageInfo.getCurrent()+1:pageInfo.getPages()
            pageNum1 = 1;
        }
        if (pageNum2<1){//判断分页的下界，上届的判断在前端页面处，若当前页大于总页数，则设置当前页为尾页
            //pageInfo.getCurrent()<pageInfo.getPages()?pageInfo.getCurrent()+1:pageInfo.getPages()
            pageNum2 = 1;
        }
        //获取当前用户信息
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        ModelAndView mv = new ModelAndView();
        IPage<User> pageInfo1 = userService.getAllTenant(new Page(pageNum1, 3));
        IPage<User> pageInfo2 = userService.getAllLandlord(new Page(pageNum2, 3));
        List<User> tenants = pageInfo1.getRecords();
        List<User> landlords1 = pageInfo2.getRecords();
        List<Pingzheng> allPingZheng = pingzhengService.getAllPingZheng();
        List<ShowLandlord> allLandlord = new ArrayList<>();
        //封装房东信息
        for (User landlord : landlords1) {
            ShowLandlord showLandlord = new ShowLandlord();
            showLandlord.setLandlord(landlord);
            for (Pingzheng pingzheng : allPingZheng) {
                if (pingzheng.getUsername().equals(landlord.getUsername())){
                    showLandlord.setPingZheng(pingzheng.getPingzhengPic());
                }
            }
            allLandlord.add(showLandlord);
        }
        mv.addObject("pageInfo1",pageInfo1);
        mv.addObject("pageInfo2",pageInfo2);
        mv.addObject("user",user);
        mv.addObject("tenants",tenants);
        mv.addObject("showLandlord",allLandlord);
        mv.setViewName("/manager/user-list");
        return mv;
    }
    //删除用户
    @RequestMapping("/deleteUser")
    public ModelAndView deleteUser(@RequestParam("userId") Integer userId){
        //获取当前用户信息
        User user = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        ModelAndView mv = new ModelAndView();
        Message message = userService.deleteUser(userId);
        IPage<User> pageInfo1 = userService.getAllTenant(new Page(1, 3));
        IPage<User> pageInfo2 = userService.getAllLandlord(new Page(1, 3));

        List<User> tenants = pageInfo1.getRecords();
        List<User> landlords = pageInfo2.getRecords();
        mv.addObject("pageInfo1",pageInfo1);
        mv.addObject("pageInfo2",pageInfo2);
        mv.addObject("user",user);
        mv.addObject("tenants",tenants);
        mv.addObject("landlords",landlords);
        mv.addObject("message",message.getMessage());
        mv.setViewName("/manager/user-list");
        return mv;
    }
}
