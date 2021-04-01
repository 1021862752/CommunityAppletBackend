package org.jeecg.modules.demo.volunteeractivity.service;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.running.entity.FlowingWater;
import org.jeecg.modules.demo.volunteeractivity.entity.MdActivityTimeDetail;
import org.jeecg.modules.demo.volunteeractivity.entity.MdVolunteerActivity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description: 志愿活动
 * @Author: jeecg-boot
 * @Date: 2020-05-29
 * @Version: V1.0
 */
public interface IMdVolunteerActivityService extends IService<MdVolunteerActivity> {

    /**
     * 添加一对多
     *
     */
    public void saveMain(MdVolunteerActivity mdVolunteerActivity,List<MdActivityTimeDetail> mdActivityTimeDetailList) ;

    /**
     * 修改一对多
     *
     */
    public void updateMain(MdVolunteerActivity mdVolunteerActivity,List<MdActivityTimeDetail> mdActivityTimeDetailList);

    /**
     * 删除一对多
     */
    public void delMain (String id);

    /**
     * 批量删除一对多
     */
    public void delBatchMain (Collection<? extends Serializable> idList);
    /**
     * 根据id获取数据
     */
    public MdVolunteerActivity getActivityInfoBy(String id);

    public List<Map> getVolunteerActivityList(String vaStatuso, String type);
}

