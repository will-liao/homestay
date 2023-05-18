package com.will.homestay.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShowAdditional {
    /**
     * 房间Id
     */
    private Integer roomId;

    /**
     * 房间地址
     */
    private String roomAddress;

    /**
     * 附加价格物品
     */
    private String feesName;

    /**
     * 附加价格
     */
    private BigDecimal addPrice;
}
