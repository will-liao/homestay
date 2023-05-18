package com.will.homestay.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ShowOrder {
    private Integer orderId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 房东id
     */
    private Integer landlordId;

    /**
     * 房间id
     */
    private Integer roomId;

    /**
     * 房间地址
     */
    private String roomAddress;

    /**
     * 房间类型 -大床房 -双人房 -海景单人房 -本地特色单人房
     */
    private String roomType;

    /**
     * 房间价格
     */
    private BigDecimal pay;

    /**
     * 入住时间
     */
    private String startTime;

    /**
     * 离店时间
     */
    private String endTime;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 订单状态 -0 未退房 -1已退房
     */
    private Boolean orderCondition;

    /**
     * 房间状态 -0 可入住 -1已退房，未清理
     */
    private Boolean roomCondition;
}
