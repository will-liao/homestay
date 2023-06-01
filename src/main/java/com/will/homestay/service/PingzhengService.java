package com.will.homestay.service;

import com.will.homestay.entity.Pingzheng;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author will
 * @since 2023-05-26
 */
public interface PingzhengService extends IService<Pingzheng> {
    void addPingZheng(Pingzheng pingzheng, MultipartFile pic);
    List<Pingzheng> getAllPingZheng();
}
