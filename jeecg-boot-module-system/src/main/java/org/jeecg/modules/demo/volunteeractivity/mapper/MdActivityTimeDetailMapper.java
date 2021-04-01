package org.jeecg.modules.demo.volunteeractivity.mapper;

import java.util.List;
import org.jeecg.modules.demo.volunteeractivity.entity.MdActivityTimeDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 活动时间域信息
 * @Author: jeecg-boot
 * @Date:   2020-06-14
 * @Version: V1.0
 */
public interface MdActivityTimeDetailMapper extends BaseMapper<MdActivityTimeDetail> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<MdActivityTimeDetail> selectByMainId(@Param("mainId") String mainId);
}
