package com.will.homestay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.will.homestay.entity.DayData;
import com.will.homestay.entity.Overview;

import java.math.BigDecimal;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
public interface OverviewMapper extends BaseMapper<Overview> {
    //总订单数
    Integer totalOrder();

    //总收入
    BigDecimal totalIncome();

    //总入住人数
    Integer totalUser();

    Integer totalLandlord();

    Integer totalRoom();

}
