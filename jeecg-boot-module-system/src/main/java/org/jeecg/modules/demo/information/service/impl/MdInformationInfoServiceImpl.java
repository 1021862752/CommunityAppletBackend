package org.jeecg.modules.demo.information.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.demo.information.entity.MdInformationInfo;
import org.jeecg.modules.demo.information.mapper.MdInformationInfoMapper;
import org.jeecg.modules.demo.information.service.IMdInformationInfoService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * @Description: 资讯管理
 * @Author: jeecg-boot
 * @Date: 2020-05-07
 * @Version: V1.0
 */
@Service
public class MdInformationInfoServiceImpl extends ServiceImpl<MdInformationInfoMapper, MdInformationInfo> implements IMdInformationInfoService {

    @Resource
    private MdInformationInfoMapper mdInformationInfoMapper;

    @Override
    public Page<MdInformationInfo> getInformationInfoData(Page<MdInformationInfo> page, MdInformationInfo mdInformationInfo) {
        return page.setRecords(mdInformationInfoMapper.getInformationInfoData(page, mdInformationInfo));
    }
}
