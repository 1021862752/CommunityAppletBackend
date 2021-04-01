package org.jeecg.modules.demo.clapwill.service.impl;

import org.jeecg.modules.demo.clapwill.entity.MdCwInfo;
import org.jeecg.modules.demo.clapwill.mapper.MdCwInfoMapper;
import org.jeecg.modules.demo.clapwill.service.IMdCwInfoService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 随手怕图片管理
 * @Author: jeecg-boot
 * @Date:   2020-05-02
 * @Version: V1.0
 */
@Service
public class MdCwInfoServiceImpl extends ServiceImpl<MdCwInfoMapper, MdCwInfo> implements IMdCwInfoService {
	
	@Autowired
	private MdCwInfoMapper mdCwInfoMapper;
	
	@Override
	public List<MdCwInfo> selectByMainId(String mainId) {
		return mdCwInfoMapper.selectByMainId(mainId);
	}
}
