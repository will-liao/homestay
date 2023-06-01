package com.will.homestay.service.impl;

import com.will.homestay.entity.AdmFeedback;
import com.will.homestay.mapper.AdmFeedbackMapper;
import com.will.homestay.service.AdmFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
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
public class AdmFeedbackServiceImpl extends ServiceImpl<AdmFeedbackMapper, AdmFeedback> implements AdmFeedbackService {

    @Autowired
    private AdmFeedbackMapper admFeedbackMapper;

    @Override
    public int insertFeedback(String feedback) {
        AdmFeedback admFeedback = new AdmFeedback();
        admFeedback.setUserId(4);
        admFeedback.setFeedbackContent(feedback);
        //定义LocalDateTime格式
        LocalDateTime localDateTime = LocalDateTime.now();
        admFeedback.setFeedbackTime(new Date());
        int insert = admFeedbackMapper.insert(admFeedback);
        if(insert == 1){
            return 1;
        }
        return 0;
    }

    @Override
    public List<AdmFeedback> getFeedbacks() {
        return admFeedbackMapper.selectList(null);
    }
}
