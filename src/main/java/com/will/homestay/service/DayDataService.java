package com.will.homestay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.will.homestay.entity.DayData;

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
public interface DayDataService extends IService<DayData> {
    Integer dayOrder();

    BigDecimal dayIncome();

    Integer dayLiving();

    void updateDayData(DayData dayData);

    //获取tb_day表数据
    List<DayData> getAllDayData();
}
