package org.jeecg.modules.demo.volunteeractivity.service;

import org.jeecg.modules.demo.volunteeractivity.entity.MdActivityTimeDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 活动时间域信息
 * @Author: jeecg-boot
 * @Date:   2020-06-14
 * @Version: V1.0
 */
public interface IMdActivityTimeDetailService extends IService<MdActivityTimeDetail> {

	public List<MdActivityTimeDetail> selectByMainId(String mainId);
}
