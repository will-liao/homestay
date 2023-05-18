package com.will.homestay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.will.homestay.entity.DayData;
import com.will.homestay.entity.MouthData;

import java.math.BigDecimal;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
public interface MouthDataMapper extends BaseMapper<MouthData> {
    //每月的订单数
    Integer mouthOrder();

    //每月的用户数
    Integer mouthLiving();

    //每月的总收入
    BigDecimal mouthIncome();
}
