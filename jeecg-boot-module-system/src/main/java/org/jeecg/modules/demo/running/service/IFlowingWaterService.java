package org.jeecg.modules.demo.running.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.demo.running.entity.FlowingWater;
import org.jeecg.modules.system.entity.SysUser;

public interface IFlowingWaterService extends IService<FlowingWater> {

    /**
     * 根据时间域获取流水
     */
    public FlowingWater getWaterByDate(String dateStr);
}
