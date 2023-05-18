package com.will.homestay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.will.homestay.entity.Advertise;
import com.will.homestay.entity.MouthData;
import com.will.homestay.mapper.AdvertiseMapper;
import com.will.homestay.mapper.MouthDataMapper;
import com.will.homestay.service.AdvertiseService;
import com.will.homestay.service.MouthDataService;
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
public class MouthDataServiceImpl extends ServiceImpl<MouthDataMapper, MouthData> implements MouthDataService {
    @Autowired
    MouthDataMapper mouthDataMapper;

    @Override
    public Integer mouthOrder() {
        return mouthDataMapper.mouthOrder();
    }

    @Override
    public BigDecimal mouthIncome() {
        return mouthDataMapper.mouthIncome();
    }

    @Override
    public Integer mouthLiving() {
        return mouthDataMapper.mouthLiving();
    }

    @Override
    public void updateMouthData(MouthData mouthData) {
        mouthDataMapper.insert(mouthData);
    }

    @Override
    public List<MouthData> getAllMouthData() {
        return mouthDataMapper.selectList(null);
    }
}
