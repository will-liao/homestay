package com.will.homestay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.will.homestay.entity.Advertise;
import com.will.homestay.entity.Overview;
import com.will.homestay.mapper.AdvertiseMapper;
import com.will.homestay.mapper.OverviewMapper;
import com.will.homestay.service.AdvertiseService;
import com.will.homestay.service.OverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
@Service
public class OverviewServiceImpl extends ServiceImpl<OverviewMapper, Overview> implements OverviewService {
    @Autowired
    OverviewMapper overviewMapper;

    @Override
    public Integer totalOrder() {
        return overviewMapper.totalOrder();
    }

    @Override
    public BigDecimal totalIncome() {
        return overviewMapper.totalIncome();
    }

    @Override
    public Integer totalUser() {
        return overviewMapper.totalUser();
    }

    @Override
    public void updateOverview(Overview overview) {
        overview.setOverviewId(1);
        overviewMapper.updateById(overview);
    }

    @Override
    public Integer totalLandlord() {
        return overviewMapper.totalLandlord();
    }

    @Override
    public Integer totalRoom() {
        return overviewMapper.totalRoom();
    }

    @Override
    public Overview getOverview() {
        return overviewMapper.selectById(1);
    }
}
