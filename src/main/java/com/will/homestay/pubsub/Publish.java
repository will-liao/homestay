package com.will.homestay.pubsub;

import com.will.homestay.service.AdmFeedbackService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class Publish {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    AdmFeedbackService admFeedbackService;

    public void publish(String message,String channel){
        // 发送消息
        // 下面这里需要配置发送的CHANNEL名称
        redisTemplate.convertAndSend(channel, message);
        admFeedbackService.insertFeedback(message);
    }
}
