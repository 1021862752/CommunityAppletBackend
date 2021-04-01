package org.jeecg.modules.demo.volunteeractivity.service.impl;

import org.jeecg.modules.demo.volunteeractivity.entity.MdVolunteerActivity;
import org.jeecg.modules.demo.volunteeractivity.entity.MdActivityTimeDetail;
import org.jeecg.modules.demo.volunteeractivity.mapper.MdActivityTimeDetailMapper;
import org.jeecg.modules.demo.volunteeractivity.mapper.MdVolunteerActivityMapper;
import org.jeecg.modules.demo.volunteeractivity.service.IMdVolunteerActivityService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;
import java.util.Map;

/**
 * @Description: 志愿活动
 * @Author: jeecg-boot
 * @Date:   2020-06-14
 * @Version: V1.0
 */
@Service
public class MdVolunteerActivityServiceImpl extends ServiceImpl<MdVolunteerActivityMapper, MdVolunteerActivity> implements IMdVolunteerActivityService {

    @Autowired
    private MdVolunteerActivityMapper mdVolunteerActivityMapper;
    @Autowired
    private MdActivityTimeDetailMapper mdActivityTimeDetailMapper;

    @Override
    @Transactional
    public void saveMain(MdVolunteerActivity mdVolunteerActivity, List<MdActivityTimeDetail> mdActivityTimeDetailList) {
        mdVolunteerActivityMapper.insert(mdVolunteerActivity);
        if(mdActivityTimeDetailList!=null && mdActivityTimeDetailList.size()>0) {
            for(MdActivityTimeDetail entity:mdActivityTimeDetailList) {
                //外键设置
                entity.setAlFkId(mdVolunteerActivity.getId());
                mdActivityTimeDetailMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional
    public void updateMain(MdVolunteerActivity mdVolunteerActivity,List<MdActivityTimeDetail> mdActivityTimeDetailList) {
        mdVolunteerActivityMapper.updateById(mdVolunteerActivity);

        //1.先删除子表数据
        mdActivityTimeDetailMapper.deleteByMainId(mdVolunteerActivity.getId());

        //2.子表数据重新插入
        if(mdActivityTimeDetailList!=null && mdActivityTimeDetailList.size()>0) {
            for(MdActivityTimeDetail entity:mdActivityTimeDetailList) {
                //外键设置
                entity.setAlFkId(mdVolunteerActivity.getId());
                mdActivityTimeDetailMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional
    public void delMain(String id) {
        mdActivityTimeDetailMapper.deleteByMainId(id);
        mdVolunteerActivityMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void delBatchMain(Collection<? extends Serializable> idList) {
        for(Serializable id:idList) {
            mdActivityTimeDetailMapper.deleteByMainId(id.toString());
            mdVolunteerActivityMapper.deleteById(id);
        }
    }

    @Override
    public MdVolunteerActivity getActivityInfoBy(String id) {
        return mdVolunteerActivityMapper.getActivityInfoBy(id);
    }

    @Override
    public List<Map> getVolunteerActivityList(String vaStatuso, String type) {
        return mdVolunteerActivityMapper.getVolunteerActivityList(vaStatuso, type);
    }

}
