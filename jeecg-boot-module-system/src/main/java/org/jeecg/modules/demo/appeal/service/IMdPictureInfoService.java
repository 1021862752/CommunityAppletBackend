package org.jeecg.modules.demo.appeal.service;

import org.jeecg.modules.demo.appeal.entity.MdPictureInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 诉求图片管理
 * @Author: jeecg-boot
 * @Date:   2020-05-05
 * @Version: V1.0
 */
public interface IMdPictureInfoService extends IService<MdPictureInfo> {

	public List<MdPictureInfo> selectByMainId(String mainId);
}
