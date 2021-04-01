package org.jeecg.modules.demo.appeal.mapper;

import java.util.List;
import org.jeecg.modules.demo.appeal.entity.MdPictureInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 诉求图片管理
 * @Author: jeecg-boot
 * @Date:   2020-05-05
 * @Version: V1.0
 */
public interface MdPictureInfoMapper extends BaseMapper<MdPictureInfo> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<MdPictureInfo> selectByMainId(@Param("mainId") String mainId);
}
