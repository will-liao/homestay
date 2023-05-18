package com.will.homestay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.will.homestay.entity.AdmFeedback;
import com.will.homestay.entity.Advertise;
import com.will.homestay.entity.Message;
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
public interface AdvertiseService extends IService<Advertise> {
    Message updateAdvertise(Advertise advertise, MultipartFile file);

    //获取所有广告
    List<Advertise> getAllAdvertise();
}
