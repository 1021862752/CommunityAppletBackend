package org.jeecg.modules.demo.volunteer.service;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.volunteer.entity.MdVolunteer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 志愿者
 * @Author: jeecg-boot
 * @Date: 2020-05-27
 * @Version: V1.0
 */
public interface IMdVolunteerService extends IService<MdVolunteer> {
    public MdVolunteer getVolunteerByid(String vtOpenId);
}
