package org.jeecg.modules.demo.clapwill.service.impl;

import org.jeecg.modules.demo.clapwill.entity.MdClapWillInfo;
import org.jeecg.modules.demo.clapwill.entity.MdCwInfo;
import org.jeecg.modules.demo.clapwill.mapper.MdCwInfoMapper;
import org.jeecg.modules.demo.clapwill.mapper.MdClapWillInfoMapper;
import org.jeecg.modules.demo.clapwill.service.IMdClapWillInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 随手拍管理
 * @Author: jeecg-boot
 * @Date:   2020-05-02
 * @Version: V1.0
 */
@Service
public class MdClapWillInfoServiceImpl extends ServiceImpl<MdClapWillInfoMapper, MdClapWillInfo> implements IMdClapWillInfoService {

	@Autowired
	private MdClapWillInfoMapper mdClapWillInfoMapper;
	@Autowired
	private MdCwInfoMapper mdCwInfoMapper;
	
	@Override
	@Transactional
	public void saveMain(MdClapWillInfo mdClapWillInfo, List<MdCwInfo> mdCwInfoList) {
		mdClapWillInfoMapper.insert(mdClapWillInfo);
		if(mdCwInfoList!=null && mdCwInfoList.size()>0) {
			for(MdCwInfo entity:mdCwInfoList) {
				//外键设置
				entity.setCwFkId(mdClapWillInfo.getId());
				mdCwInfoMapper.insert(entity);
				log.error("");
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(MdClapWillInfo mdClapWillInfo,List<MdCwInfo> mdCwInfoList) {
		mdClapWillInfoMapper.updateById(mdClapWillInfo);
		
		//1.先删除子表数据
		mdCwInfoMapper.deleteByMainId(mdClapWillInfo.getId());
		
		//2.子表数据重新插入
		if(mdCwInfoList!=null && mdCwInfoList.size()>0) {
			for(MdCwInfo entity:mdCwInfoList) {
				//外键设置
				entity.setCwFkId(mdClapWillInfo.getId());
				mdCwInfoMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		mdCwInfoMapper.deleteByMainId(id);
		mdClapWillInfoMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			mdCwInfoMapper.deleteByMainId(id.toString());
			mdClapWillInfoMapper.deleteById(id);
		}
	}
	
}
