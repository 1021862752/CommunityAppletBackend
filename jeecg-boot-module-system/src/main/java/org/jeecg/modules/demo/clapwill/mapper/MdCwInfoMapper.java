package org.jeecg.modules.demo.clapwill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.clapwill.entity.MdCwInfo;

import java.util.List;

/**
 * @Description: 随手怕图片管理
 * @Author: jeecg-boot
 * @Date:   2020-05-02
 * @Version: V1.0
 */
public interface MdCwInfoMapper extends BaseMapper<MdCwInfo> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<MdCwInfo> selectByMainId(@Param("mainId") String mainId);
}
