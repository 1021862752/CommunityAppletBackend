package org.jeecg.modules.demo.clapwill.service;

import org.jeecg.modules.demo.clapwill.entity.MdCwInfo;
import org.jeecg.modules.demo.clapwill.entity.MdClapWillInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 随手拍管理
 * @Author: jeecg-boot
 * @Date:   2020-05-02
 * @Version: V1.0
 */
public interface IMdClapWillInfoService extends IService<MdClapWillInfo> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(MdClapWillInfo mdClapWillInfo,List<MdCwInfo> mdCwInfoList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(MdClapWillInfo mdClapWillInfo,List<MdCwInfo> mdCwInfoList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
