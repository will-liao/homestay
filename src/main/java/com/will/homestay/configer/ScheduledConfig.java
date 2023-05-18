package com.will.homestay.configer;

import com.will.homestay.entity.DayData;
import com.will.homestay.entity.MouthData;
import com.will.homestay.entity.Overview;
import com.will.homestay.service.DayDataService;
import com.will.homestay.service.MouthDataService;
import com.will.homestay.service.OverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.util.Date;

@Configuration
@EnableScheduling
public class ScheduledConfig {
    @Autowired
    DayDataService dayDataService;
    @Autowired
    MouthDataService mouthDataService;
    @Autowired
    OverviewService overviewService;

    //每天更新一次数据库日订单数、日收入、日入住人数、总订单数、总收入、总入住人数
    @Scheduled(cron = "0 0 1 * * ?")
    //@Scheduled(cron = "15 * * * * ?")
    public void updateDay(){
        try {
            System.out.println("开始更新数据库");

            //获取当前时间
            Date date = new Date();

            //获取数据库中的日订单数、日收入、日入住人数、总订单数、总收入、总入住人数
            int dayOrder = dayDataService.dayOrder();
            BigDecimal dayIncome = dayDataService.dayIncome();
            int dayLiving = dayDataService.dayLiving();
            //封装DayData对象
            DayData dayData = new DayData();
            dayData.setDayOrder(dayOrder);
            dayData.setDayIncome(dayIncome);
            dayData.setDayLiving(dayLiving);
            dayData.setDate(date);
            //向tb_day表中插入数据
            dayDataService.updateDayData(dayData);

            //获取数据库中的总订单数、总收入、总入住人数
            int totalOrder = overviewService.totalOrder();
            BigDecimal totalIncome = overviewService.totalIncome();
            int totalUser = overviewService.totalUser();
            int totalLandlord = overviewService.totalLandlord();
            int totalRoom = overviewService.totalRoom();
            //封装Overview对象
            Overview overview = new Overview();
            overview.setTotalOrder(totalOrder);
            overview.setTotalIncome(totalIncome);
            overview.setUserNum(totalUser);
            overview.setLandlordNum(totalLandlord);
            overview.setRoomNum(totalRoom);
            //向tb_overview表中插入数据
            overviewService.updateOverview(overview);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //每月更新一次数据库日订单数、日收入、日入住人数
    @Scheduled(cron = "0 0 0 1 * ?")
    public void updateMonth(){
        System.out.println("更新月数据定时任务启动");
        try {
            //获取当前时间
            Date date = new Date();
            //获取数据库中的月订单数、月收入、月入住人数
            int mouthOrder = mouthDataService.mouthOrder();
            BigDecimal mouthIncome = mouthDataService.mouthIncome();
            int mouthLiving = mouthDataService.mouthLiving();
            //封装MouthData对象
            MouthData mouthData = new MouthData();
            mouthData.setMouthOrder(mouthOrder);
            mouthData.setMouthIncome(mouthIncome);
            mouthData.setMouthLiving(mouthLiving);
            mouthData.setDate(date);
            //向tb_mouth表中插入数据
            mouthDataService.updateMouthData(mouthData);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
