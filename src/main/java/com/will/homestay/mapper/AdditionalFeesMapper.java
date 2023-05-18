package com.will.homestay.mapper;

import com.will.homestay.entity.AdditionalFees;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.will.homestay.pojo.ShowAdditional;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author will
 * @since 2023-03-30
 */
public interface AdditionalFeesMapper extends BaseMapper<AdditionalFees> {
    List<ShowAdditional> showRoomAdditional(int landlordId);
}
