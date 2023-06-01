package com.will.homestay.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ShowRoom {
    /**
     * 房间id
     */
    private Integer roomId;

    /**
     * 房东id
     */
    private Integer landlordId;

    /**
     * 房间地址
     */
    private String roomAddress;

    /**
     * 房间描述
     */
    private String roomDescrible;

    /**
     * 房间价格
     */
    private BigDecimal roomPrice;

    /**
     * 房间类型 -大床房 -双人房 -海景单人房 -本地特色单人房
     */
    private String roomType;

    /**
     * 房间状况 -0可预订 -1不可预定
     */
    private Boolean roomCondition;

    /**
     * 房间大小 -单位平方米
     */
    private Integer roomSize;

    /**
     * 房间照片
     */
    private String pic;

    private double avgRate;
}
