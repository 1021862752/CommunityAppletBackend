package org.jeecg.modules.demo.running.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.demo.running.entity.FlowingWater;
import org.jeecg.modules.demo.running.mapper.FlowingWaterMapper;
import org.jeecg.modules.demo.running.service.IFlowingWaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 流水管理
 * @Author: jeecg-boot
 * @Date: 2020-05-27
 * @Version: V1.0
 */
@Service
public class FlowingWaterServiceImpl extends ServiceImpl<FlowingWaterMapper, FlowingWater> implements IFlowingWaterService {
    @Autowired
    private FlowingWaterMapper flowingWaterMapper;

    @Override
    public FlowingWater getWaterByDate(String dateStr) {
        return flowingWaterMapper.getWaterByDate(dateStr);
    }
}
