package org.jeecg.modules.demo.appeal.service;

import org.jeecg.modules.demo.appeal.entity.MdPictureInfo;
import org.jeecg.modules.demo.appeal.entity.MdAppealInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 诉求管理
 * @Author: jeecg-boot
 * @Date:   2020-05-05
 * @Version: V1.0
 */
public interface IMdAppealInfoService extends IService<MdAppealInfo> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(MdAppealInfo mdAppealInfo,List<MdPictureInfo> mdPictureInfoList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(MdAppealInfo mdAppealInfo,List<MdPictureInfo> mdPictureInfoList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
