package com.will.homestay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.will.homestay.entity.DayData;
import com.will.homestay.entity.MouthData;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
public interface MouthDataService extends IService<MouthData> {

    Integer mouthOrder();

    BigDecimal mouthIncome();

    Integer mouthLiving();

    void updateMouthData(MouthData mouthData);

    //获取tb_mouth表数据
    List<MouthData> getAllMouthData();
}
