package com.will.homestay;

import com.will.homestay.mapper.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import redis.clients.jedis.JedisPooled;

@SpringBootTest
class HomestayApplicationTests {
    @Autowired
    AdditionalFeesMapper additionalFeesMapper;
    @Autowired
    OrderDetailMapper orderDetailMapper;
    @Autowired
    DayDataMapper dayDataMapper;
    @Autowired
    MouthDataMapper mouthDataMapper;
    @Autowired
    OverviewMapper overviewMapper;
    @Autowired
    AdvertiseMapper advertiseMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
    }
    @Test
    public void generateManagerPassword(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("111"));
    }
    @Test
    public void showRoomAdditional(){
        int landlord = 0;
        System.out.println(additionalFeesMapper.showRoomAdditional(landlord));
    }

    @Test
    public void textGetUser(){
        }

    @Test
    public void textShowOrder(){
        System.out.println(orderDetailMapper.queryOrders(6));
    }

    @Test
    public void textShowData(){
        //每日数据
        System.out.println(dayDataMapper.dayIncome());
        System.out.println(dayDataMapper.dayOrder());
        System.out.println(dayDataMapper.dayLiving());
        //每月数据
        System.out.println(mouthDataMapper.mouthIncome());
        System.out.println(mouthDataMapper.mouthOrder());
        System.out.println(mouthDataMapper.mouthLiving());
        //总数据
        System.out.println(overviewMapper.totalIncome());
        System.out.println(overviewMapper.totalOrder());
        System.out.println(overviewMapper.totalUser());
    }

    @Test
    public void testRedis(){

        System.out.println(stringRedisTemplate.opsForValue().get("test"));
        System.out.println(redisTemplate.opsForValue().get("mykey"));
    }
    @Test
    public void testProCon(){
        //测试订阅发布

    }

    @Test
    public void testJedis(){
        JedisPooled jedis = new JedisPooled("localhost", 6379);
        jedis.set("foo", "bar");
        System.out.println(jedis.get("foo"));
    }
}
