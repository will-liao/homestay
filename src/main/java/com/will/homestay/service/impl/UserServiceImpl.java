package com.will.homestay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.will.homestay.entity.Message;
import com.will.homestay.entity.User;
import com.will.homestay.mapper.RoomMapper;
import com.will.homestay.mapper.UserMapper;
import com.will.homestay.service.OverviewService;
import com.will.homestay.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    UserMapper userMapper;
    @Autowired
    RoomMapper roomMapper;
    @Override
    public User selectUserByUsername(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public Message register(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Message message = new Message();
        int insert = userMapper.insert(user);

        if (insert==0)
            message.setMessage("注册失败！");
        else message.setMessage("用户注册成功！");
        return message;
    }

    @Override
    public int getUserIdByName(String username) {
        return selectUserByUsername(username).getUserId();
    }

    @Override
    public Message updateUser(User user, MultipartFile pic) {
        try {
            if (pic.getSize()>0){
                String path = "E:\\file";
                String filename = pic.getOriginalFilename();
                File targetFile = null;
                if (filename != null) {
                    targetFile = new File(path,filename);
                }
                pic.transferTo(targetFile);//持久化到磁盘
                user.setUserPic(filename);//设置器材图片名称
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message message = new Message();
        int update = userMapper.updateById(user);
        if (update==0)
            message.setMessage("修改失败！");
        else message.setMessage("修改成功！");
        return message;
    }

    @Override
    public User getUserById(int userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public IPage<User> getAllLandlord(Page page) {
        //获取所有房东用户
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_type","ROLE_LANDLORD");
        return userMapper.selectPage(page,queryWrapper);
    }

    @Override
    public IPage<User> getAllTenant(Page page) {
        //获取所有房东用户
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_type","ROLE_COMMON");

        return userMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Message deleteUser(int userId) {
        Message message = new Message();
        User user = userMapper.selectById(userId);
        //如果是房东，则删除所有房东id为该房东的的民宿房间
        if (user.getUserType().equals("ROLE_LANDLORD")){
            //删除该房东的所有房间
            System.out.println("删除房东");
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("landlord_id",userId);
            int deleteLandlord = userMapper.deleteById(userId);
            int deleteRoom = roomMapper.delete(queryWrapper);
            if (deleteLandlord == 0)
                message.setMessage("删除房东失败！");
            else message.setMessage("删除房东成功！该房东的"+deleteRoom+"个房间被删除");
            return message;
        }else {
            System.out.println("删除租客");
            int delete = userMapper.deleteById(userId);
            if (delete == 0)
                message.setMessage("删除租客失败！");
            else message.setMessage("删除租客成功！");
            return message;
        }
    }
}
