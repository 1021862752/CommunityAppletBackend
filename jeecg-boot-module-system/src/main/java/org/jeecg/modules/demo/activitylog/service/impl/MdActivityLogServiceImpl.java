package org.jeecg.modules.demo.activitylog.service.impl;

import org.jeecg.modules.demo.activitylog.entity.MdActivityLog;
import org.jeecg.modules.demo.activitylog.mapper.MdActivityLogMapper;
import org.jeecg.modules.demo.activitylog.service.IMdActivityLogService;
import org.jeecg.modules.demo.volunteeractivity.mapper.MdVolunteerActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Description: 活动参与记录
 * @Author: jeecg-boot
 * @Date: 2020-05-31
 * @Version: V1.0
 */
@Service
public class MdActivityLogServiceImpl extends ServiceImpl<MdActivityLogMapper, MdActivityLog> implements IMdActivityLogService {

    @Autowired
    private MdActivityLogMapper mdActivityLogMapper;

    @Override
    public List<MdActivityLog> getActivityLogById(String alId, String alOpenId) {
        return mdActivityLogMapper.getActivityLogById(alId, alOpenId);
    }

    @Override
    public List<MdActivityLog> getActivityLogByDetailId(String alId, String alOpenId, String alDetailId) {
        return mdActivityLogMapper.getActivityLogByDetailId(alId, alOpenId, alDetailId);
    }

    @Override
    public void updateSign(String alSignIn, String alId, String alOpenId, String alDetailId) {
        mdActivityLogMapper.updateSign(alSignIn, alId, alOpenId, alDetailId);
    }

    @Override
    public void deleteByAlId(String alId) {
        mdActivityLogMapper.deleteByAlId(alId);
    }

    @Override
    public List<Map> getActivityLogByUser(String alOpenId) {
        return mdActivityLogMapper.getActivityLogByUser(alOpenId);
    }
}
