package com.will.homestay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.will.homestay.entity.Advertise;
import com.will.homestay.entity.DayData;
import com.will.homestay.mapper.AdvertiseMapper;
import com.will.homestay.mapper.DayDataMapper;
import com.will.homestay.service.AdvertiseService;
import com.will.homestay.service.DayDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
public class DayDataServiceImpl extends ServiceImpl<DayDataMapper, DayData> implements DayDataService {
    @Autowired
    DayDataMapper dayDataMapper;

    @Override
    public Integer dayOrder() {
        return dayDataMapper.dayOrder();
    }

    @Override
    public BigDecimal dayIncome() {
        return dayDataMapper.dayIncome();
    }

    @Override
    public Integer dayLiving() {
        return dayDataMapper.dayLiving();
    }

    @Override
    public void updateDayData(DayData dayData) {
        dayDataMapper.insert(dayData);
    }

    @Override
    public List<DayData> getAllDayData() {
        return dayDataMapper.selectList(null);
    }
}
