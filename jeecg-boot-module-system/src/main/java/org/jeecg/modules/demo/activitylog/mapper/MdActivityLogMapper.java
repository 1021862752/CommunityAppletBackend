package org.jeecg.modules.demo.activitylog.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.activitylog.entity.MdActivityLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.demo.volunteeractivity.entity.MdVolunteerActivity;

/**
 * @Description: 活动参与记录
 * @Author: jeecg-boot
 * @Date: 2020-05-31
 * @Version: V1.0
 */
public interface MdActivityLogMapper extends BaseMapper<MdActivityLog> {
    public List<MdActivityLog> getActivityLogById(@Param("alId") String alId, @Param("alOpenId") String alOpenId);

    public List<MdActivityLog> getActivityLogByDetailId(@Param("alId") String alId, @Param("alOpenId") String alOpenId, @Param("alDetailId") String alDetailId);

    void updateSign(@Param("alSignIn") String alSignIn, @Param("alId") String alId, @Param("alOpenId") String alOpenId, @Param("alDetailId") String alDetailId);

    void deleteByAlId(@Param("alId") String alId);

    public List<Map> getActivityLogByUser(@Param("alOpenId") String alOpenId);
}
