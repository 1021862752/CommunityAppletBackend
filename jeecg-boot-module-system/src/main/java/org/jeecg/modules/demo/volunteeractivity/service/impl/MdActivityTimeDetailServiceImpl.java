package org.jeecg.modules.demo.volunteeractivity.service.impl;

import org.jeecg.modules.demo.volunteeractivity.entity.MdActivityTimeDetail;
import org.jeecg.modules.demo.volunteeractivity.mapper.MdActivityTimeDetailMapper;
import org.jeecg.modules.demo.volunteeractivity.service.IMdActivityTimeDetailService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 活动时间域信息
 * @Author: jeecg-boot
 * @Date:   2020-06-14
 * @Version: V1.0
 */
@Service
public class MdActivityTimeDetailServiceImpl extends ServiceImpl<MdActivityTimeDetailMapper, MdActivityTimeDetail> implements IMdActivityTimeDetailService {
	
	@Autowired
	private MdActivityTimeDetailMapper mdActivityTimeDetailMapper;
	
	@Override
	public List<MdActivityTimeDetail> selectByMainId(String mainId) {
		return mdActivityTimeDetailMapper.selectByMainId(mainId);
	}
}
