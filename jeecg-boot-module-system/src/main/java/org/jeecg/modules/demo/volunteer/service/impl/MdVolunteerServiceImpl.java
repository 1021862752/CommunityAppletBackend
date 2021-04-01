package org.jeecg.modules.demo.volunteer.service.impl;

import org.jeecg.modules.demo.activitylog.mapper.MdActivityLogMapper;
import org.jeecg.modules.demo.volunteer.entity.MdVolunteer;
import org.jeecg.modules.demo.volunteer.mapper.MdVolunteerMapper;
import org.jeecg.modules.demo.volunteer.service.IMdVolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 志愿者
 * @Author: jeecg-boot
 * @Date:   2020-05-27
 * @Version: V1.0
 */
@Service
public class MdVolunteerServiceImpl extends ServiceImpl<MdVolunteerMapper, MdVolunteer> implements IMdVolunteerService {

    @Autowired
    private MdVolunteerMapper mdVolunteerMapper;

    @Override
    public MdVolunteer getVolunteerByid(String vtOpenId) {
        return mdVolunteerMapper.getVolunteerByid(vtOpenId);
    }
}
