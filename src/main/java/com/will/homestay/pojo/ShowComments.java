package com.will.homestay.pojo;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ShowComments {
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
}
