package org.jeecg.modules.demo.appeal.service.impl;

import org.jeecg.modules.demo.appeal.entity.MdPictureInfo;
import org.jeecg.modules.demo.appeal.mapper.MdPictureInfoMapper;
import org.jeecg.modules.demo.appeal.service.IMdPictureInfoService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 诉求图片管理
 * @Author: jeecg-boot
 * @Date:   2020-05-05
 * @Version: V1.0
 */
@Service
public class MdPictureInfoServiceImpl extends ServiceImpl<MdPictureInfoMapper, MdPictureInfo> implements IMdPictureInfoService {
	
	@Autowired
	private MdPictureInfoMapper mdPictureInfoMapper;
	
	@Override
	public List<MdPictureInfo> selectByMainId(String mainId) {
		return mdPictureInfoMapper.selectByMainId(mainId);
	}
}
