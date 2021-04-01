package org.jeecg.modules.demo.information.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.demo.information.entity.MdInformationInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.system.model.AnnouncementSendModel;

/**
 * @Description: 资讯管理
 * @Author: jeecg-boot
 * @Date: 2020-05-07
 * @Version: V1.0
 */
public interface IMdInformationInfoService extends IService<MdInformationInfo> {

    public Page<MdInformationInfo> getInformationInfoData(Page<MdInformationInfo> page, MdInformationInfo mdInformationInfo);

}
