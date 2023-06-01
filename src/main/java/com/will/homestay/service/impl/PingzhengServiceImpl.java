package com.will.homestay.service.impl;

import com.will.homestay.entity.Pingzheng;
import com.will.homestay.mapper.PingzhengMapper;
import com.will.homestay.service.PingzhengService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * @since 2023-05-26
 */
@Service
public class PingzhengServiceImpl extends ServiceImpl<PingzhengMapper, Pingzheng> implements PingzhengService {
    @Autowired
    PingzhengMapper pingzhengMapper;

    @Override
    public void addPingZheng(Pingzheng pingzheng, MultipartFile pic) {
        try {
            if (pic.getSize()>0){
                String path = "E:\\file";
                String filename = pic.getOriginalFilename();
                File targetFile = new File(path,filename);
                pic.transferTo(targetFile);
                pingzheng.setPingzhengPic(filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        pingzhengMapper.insert(pingzheng);
    }

    @Override
    public List<Pingzheng> getAllPingZheng() {
        return pingzhengMapper.selectList(null);
    }
}
