package org.jeecg.modules.demo.clapwill.service;

import org.jeecg.modules.demo.clapwill.entity.MdCwInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 随手怕图片管理
 * @Author: jeecg-boot
 * @Date:   2020-05-02
 * @Version: V1.0
 */
public interface IMdCwInfoService extends IService<MdCwInfo> {

	public List<MdCwInfo> selectByMainId(String mainId);
}
