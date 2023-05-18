package com.will.homestay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

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
@TableName("tb_overview")
public class Overview implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 概览表id
     */
    @TableId(value = "overview_id", type = IdType.AUTO)
    private Integer overviewId;

    /**
     * 用户数量
     */
    private Integer userNum;

    /**
     * 房间数量
     */
    private Integer roomNum;

    /**
     * 订单数量
     */
    private Integer totalOrder;

    /**
     * 总房东数
     */
    private Integer landlordNum;

    /**
     * 总收入
     */
    @TableField("total_income")
    private BigDecimal totalIncome;

}
