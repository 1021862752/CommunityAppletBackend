package org.jeecg.modules.demo.volunteeractivity.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.running.entity.FlowingWater;
import org.jeecg.modules.demo.volunteeractivity.entity.MdVolunteerActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 志愿活动
 * @Author: jeecg-boot
 * @Date: 2020-05-29
 * @Version: V1.0
 */
public interface MdVolunteerActivityMapper extends BaseMapper<MdVolunteerActivity> {
    public MdVolunteerActivity getActivityInfoBy(@Param("id") String id);

    public List<Map> getVolunteerActivityList(@Param("vaStatuso") String vaStatuso, @Param("type") String type);
}
