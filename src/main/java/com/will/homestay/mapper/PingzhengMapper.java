package com.will.homestay.mapper;

import com.will.homestay.entity.Pingzheng;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author will
 * @since 2023-05-26
 */
public interface PingzhengMapper extends BaseMapper<Pingzheng> {
    void addPingZheng(Pingzheng pingzheng);
    List<Pingzheng> getAllPingZheng();
}
