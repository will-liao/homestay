package com.will.homestay.service;

import com.will.homestay.entity.Message;
import com.will.homestay.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
public interface UserService extends IService<User> {
    User selectUserByUsername(String username);
    Message register(User user);
    int getUserIdByName(String username);
    Message updateUser(User user, MultipartFile pic);
    User getUserById(int userId);
    //获取所有房东用户
    List<User> getAllLandlord();
    //获取所有租客用户
    List<User> getAllTenant();
    //删除用户
    Message deleteUser(int userId);
}
