package org.jeecg.modules.demo.information.mapper;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.information.entity.MdInformationInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.system.model.AnnouncementSendModel;

/**
 * @Description: 资讯管理
 * @Author: jeecg-boot
 * @Date:   2020-05-07
 * @Version: V1.0
 */
public interface MdInformationInfoMapper extends BaseMapper<MdInformationInfo> {

    public List<MdInformationInfo> getInformationInfoData(Page<MdInformationInfo> page, @Param("mdInformationInfo") MdInformationInfo mdInformationInfo);

}
