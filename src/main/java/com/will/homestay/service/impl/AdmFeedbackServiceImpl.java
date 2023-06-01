package com.will.homestay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.will.homestay.entity.AdmFeedback;
import com.will.homestay.mapper.AdmFeedbackMapper;
import com.will.homestay.service.AdmFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private UserServiceImpl userService;

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
    public int insertFeedback(AdmFeedback feedback) {
        int userId = userService.selectUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId();
        feedback.setUserId(userId);
        LocalDateTime localDateTime = LocalDateTime.now();
        feedback.setFeedbackTime(new Date());
        int insert = admFeedbackMapper.insert(feedback);
        if(insert == 1){
            return 1;
        }
        return 0;
    }

    @Override
    public boolean whetherFeedback(Integer orderId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id",orderId);
        return admFeedbackMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public int updateFeedback(AdmFeedback feedback) {
        //根据订单id获取反馈对象
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id",feedback.getOrderId());
        AdmFeedback admFeedback = admFeedbackMapper.selectOne(queryWrapper);
        admFeedback.setFeedbackContent(feedback.getFeedbackContent());
        LocalDateTime localDateTime = LocalDateTime.now();
        feedback.setFeedbackTime(new Date());
        //更新反馈内容
        return admFeedbackMapper.updateById(admFeedback);
    }

    @Override
    public List<AdmFeedback> getFeedbacks() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",4);
        return admFeedbackMapper.selectList(queryWrapper);
    }
}
