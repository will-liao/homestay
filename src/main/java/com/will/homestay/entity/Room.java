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
@TableName("tb_room")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 房间id
     */
    @TableId(value = "room_id", type = IdType.AUTO)
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


}
