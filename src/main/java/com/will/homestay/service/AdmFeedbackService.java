package com.will.homestay.service;

import com.will.homestay.entity.AdmFeedback;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
public interface AdmFeedbackService extends IService<AdmFeedback> {
    int insertFeedback(String feedback);

    List<AdmFeedback> getFeedbacks();
}
