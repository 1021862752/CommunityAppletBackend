package org.jeecg.modules.demo.running.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.running.entity.FlowingWater;

/**
 * @Description: 流水管理
 * @Author: jeecg-boot
 * @Date: 2020-05-27
 * @Version: V1.0
 */
public interface FlowingWaterMapper extends BaseMapper<FlowingWater> {
    public FlowingWater getWaterByDate(@Param("dateStr") String dateStr);
}
