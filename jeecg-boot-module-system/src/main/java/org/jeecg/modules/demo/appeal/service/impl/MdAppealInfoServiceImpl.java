package org.jeecg.modules.demo.appeal.service.impl;

import org.jeecg.modules.demo.appeal.entity.MdAppealInfo;
import org.jeecg.modules.demo.appeal.entity.MdPictureInfo;
import org.jeecg.modules.demo.appeal.mapper.MdPictureInfoMapper;
import org.jeecg.modules.demo.appeal.mapper.MdAppealInfoMapper;
import org.jeecg.modules.demo.appeal.service.IMdAppealInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 诉求管理
 * @Author: jeecg-boot
 * @Date:   2020-05-05
 * @Version: V1.0
 */
@Service
public class MdAppealInfoServiceImpl extends ServiceImpl<MdAppealInfoMapper, MdAppealInfo> implements IMdAppealInfoService {

	@Autowired
	private MdAppealInfoMapper mdAppealInfoMapper;
	@Autowired
	private MdPictureInfoMapper mdPictureInfoMapper;
	
	@Override
	@Transactional
	public void saveMain(MdAppealInfo mdAppealInfo, List<MdPictureInfo> mdPictureInfoList) {
		mdAppealInfoMapper.insert(mdAppealInfo);
		if(mdPictureInfoList!=null && mdPictureInfoList.size()>0) {
			for(MdPictureInfo entity:mdPictureInfoList) {
				//外键设置
				entity.setAppealFkId(mdAppealInfo.getId());
				mdPictureInfoMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(MdAppealInfo mdAppealInfo,List<MdPictureInfo> mdPictureInfoList) {
		mdAppealInfoMapper.updateById(mdAppealInfo);
		
		//1.先删除子表数据
		mdPictureInfoMapper.deleteByMainId(mdAppealInfo.getId());
		
		//2.子表数据重新插入
		if(mdPictureInfoList!=null && mdPictureInfoList.size()>0) {
			for(MdPictureInfo entity:mdPictureInfoList) {
				//外键设置
				entity.setAppealFkId(mdAppealInfo.getId());
				mdPictureInfoMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		mdPictureInfoMapper.deleteByMainId(id);
		mdAppealInfoMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			mdPictureInfoMapper.deleteByMainId(id.toString());
			mdAppealInfoMapper.deleteById(id);
		}
	}
	
}
