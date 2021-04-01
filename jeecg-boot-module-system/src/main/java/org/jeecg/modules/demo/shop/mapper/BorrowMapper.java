package org.jeecg.modules.demo.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.shop.entity.Borrow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 我的借用
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
public interface BorrowMapper extends BaseMapper<Borrow> {

    void updateGoodsCount(String goodsName);

}
