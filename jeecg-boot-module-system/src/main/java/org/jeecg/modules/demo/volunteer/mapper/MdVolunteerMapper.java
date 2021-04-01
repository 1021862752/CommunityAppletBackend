package org.jeecg.modules.demo.volunteer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.activitylog.entity.MdActivityLog;
import org.jeecg.modules.demo.volunteer.entity.MdVolunteer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 志愿者
 * @Author: jeecg-boot
 * @Date: 2020-05-27
 * @Version: V1.0
 */
public interface MdVolunteerMapper extends BaseMapper<MdVolunteer> {
    public MdVolunteer getVolunteerByid(@Param("vtOpenId") String vtOpenId);
}
