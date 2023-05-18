package com.will.homestay.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_order_detail")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @TableId(value = "order_id", type = IdType.AUTO)
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
     * 房间状态 -0 未退房 -1已退房
     */
    private Boolean orderCondition;

    /**
     * 支付价格
     */
    private BigDecimal pay;

    /**
     * 预约时间
     */
    private LocalDateTime dealTime;

    /**
     * 入住时间
     */
    private Date startTime;

    /**
     * 离店时间
     */
    private Date endTime;


}
