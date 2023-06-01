package com.will.homestay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.will.homestay.entity.Overview;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
public interface OverviewService extends IService<Overview> {
    Integer totalOrder();

    //总收入
    BigDecimal totalIncome();

    //总入住人数
    Integer totalUser();

    void updateOverview(Overview overview);

    Integer totalLandlord();

    Integer totalRoom();

    Overview getOverview();

    Integer totalRoomByLandlordId(Integer landlordId);
    BigDecimal totalIncomeByLandlordId(Integer landlordId);
    Integer totalOrderByLandlordId(Integer landlordId);
    Integer totalUserByLandlordId(Integer landlordId);
}
