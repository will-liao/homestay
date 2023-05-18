package com.will.homestay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.will.homestay.entity.AdmFeedback;
import com.will.homestay.entity.DayData;

import java.math.BigDecimal;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
public interface DayDataMapper extends BaseMapper<DayData> {
    //每天的订单数
    Integer dayOrder();

    //每天的用户数
    Integer dayLiving();

    //每天的总收入
    BigDecimal dayIncome();
}
