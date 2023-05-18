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
import java.util.Date;

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
@TableName("tb_day")
public class DayData  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 概览表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 日期
     */
    private Date date;

    /**
     * 每日订单量
     */
    private Integer dayOrder;

    /**
     * 每日收入
     */
    @TableField("day_income")
    private BigDecimal dayIncome;

    /**
     * 每日入住人数
     */
    private Integer dayLiving;
}
