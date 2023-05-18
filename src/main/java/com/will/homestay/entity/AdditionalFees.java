package com.will.homestay.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
@TableName("tb_additional_fees")
public class AdditionalFees implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 附加物品id
     */
    @TableId(value = "additional_id", type = IdType.AUTO)
    private Integer additionalId;

    /**
     * 房间id
     */
    private Integer roomId;

    /**
     * 附加价格物品
     */
    private String feesName;

    /**
     * 附加价格
     */
    private BigDecimal addPrice;


}
