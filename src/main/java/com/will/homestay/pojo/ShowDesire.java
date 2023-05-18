package com.will.homestay.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShowDesire {
    private Integer desireId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 房间id
     */
    private Integer roomId;

    /**
     * 房间地址
     */
    private String roomAddress;

    /**
     * 房间照片
     */
    private String pic;
}
