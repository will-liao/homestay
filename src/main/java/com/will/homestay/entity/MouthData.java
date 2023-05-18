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
@TableName("tb_mouth")
public class MouthData implements Serializable {

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
     * 每月订单量
     */
    private Integer mouthOrder;

    /**
     * 每月收入
     */
    @TableField("mouth_income")
    private BigDecimal mouthIncome;

    /**
     * 每月入住人数
     */
    private Integer mouthLiving;

}
