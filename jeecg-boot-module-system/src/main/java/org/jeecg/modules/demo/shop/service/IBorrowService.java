package org.jeecg.modules.demo.shop.service;

import org.jeecg.modules.demo.shop.entity.Borrow;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 我的借用
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
public interface IBorrowService extends IService<Borrow> {

    void updateGoodsCount(String goodsName);
}
