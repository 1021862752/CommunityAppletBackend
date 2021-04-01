package org.jeecg.modules.demo.shop.service.impl;

import org.jeecg.modules.demo.shop.entity.Borrow;
import org.jeecg.modules.demo.shop.mapper.BorrowMapper;
import org.jeecg.modules.demo.shop.service.IBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 我的借用
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Service
public class BorrowServiceImpl extends ServiceImpl<BorrowMapper, Borrow> implements IBorrowService {

    @Autowired
    private BorrowMapper borrowMapper;

    @Override
    public void updateGoodsCount(String goodsName) {
        borrowMapper.updateGoodsCount(goodsName);
    }
}
