package com.will.homestay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.will.homestay.entity.Advertise;
import com.will.homestay.entity.Message;
import com.will.homestay.mapper.AdvertiseMapper;
import com.will.homestay.service.AdvertiseService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AdvertiseServiceImpl extends ServiceImpl<AdvertiseMapper, Advertise> implements AdvertiseService {
    @Autowired
    AdvertiseMapper advertiseMapper;

    @Override
    public Message updateAdvertise(Advertise advertise, MultipartFile pic) {
        try {
            if (pic.getSize()>0){
                String path = "E:\\file";
                String filename = pic.getOriginalFilename();
                File targetFile = null;
                if (filename != null) {
                    targetFile = new File(path,filename);
                }
                pic.transferTo(targetFile);//持久化到磁盘
                advertise.setAdPic(filename);//设置器材图片名称
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Message message = new Message();
        int i = advertiseMapper.updateById(advertise);
        if (i == 1) {
            message.setMessage("更新广告成功！");
        } else {
            message.setMessage("更新广告失败！");
        }
        return message;
    }

    @Override
    public List<Advertise> getAllAdvertise() {
        return advertiseMapper.selectList(null);
    }
}
