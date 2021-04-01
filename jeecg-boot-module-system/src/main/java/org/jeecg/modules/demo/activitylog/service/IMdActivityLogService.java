package org.jeecg.modules.demo.activitylog.service;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.activitylog.entity.MdActivityLog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.demo.volunteeractivity.entity.MdVolunteerActivity;

import java.util.List;
import java.util.Map;

/**
 * @Description: 活动参与记录
 * @Author: jeecg-boot
 * @Date: 2020-05-31
 * @Version: V1.0
 */
public interface IMdActivityLogService extends IService<MdActivityLog> {

    /**
     * 根据id获取数据
     */
    public List<MdActivityLog> getActivityLogById(String alId, String alOpenId);

    public List<MdActivityLog> getActivityLogByDetailId(String alId, String alOpenId, String alDetailId);

    void updateSign(String alSignIn, String alId, String alOpenId, String alDetailId);

    void deleteByAlId(String alId);

    public List<Map> getActivityLogByUser(String alOpenId);
}
