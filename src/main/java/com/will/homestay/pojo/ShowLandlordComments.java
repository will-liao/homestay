package com.will.homestay.pojo;

import lombok.Data;

@Data
public class ShowLandlordComments {
    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 房间id
     */
    private Integer roomId;

    /**
     * 房间名
     */
    private String roomAddress;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户照片
     */
    private String userPic;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private String time;

    /**
     * 回复内容
     */
    private String feedbackContent;
}
