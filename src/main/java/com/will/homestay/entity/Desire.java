package com.will.homestay.entity;

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
@TableName("tb_desire")
public class Desire implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 心愿单id
     */
    @TableId(value = "desire_id", type = IdType.AUTO)
    private Integer desireId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 房间id
     */
    private Integer roomId;


}
