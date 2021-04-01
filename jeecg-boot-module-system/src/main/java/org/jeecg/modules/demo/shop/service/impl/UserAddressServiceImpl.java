package org.jeecg.modules.demo.shop.service.impl;

import org.jeecg.modules.demo.shop.entity.UserAddress;
import org.jeecg.modules.demo.shop.mapper.UserAddressMapper;
import org.jeecg.modules.demo.shop.service.IUserAddressService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 用户地址管理
 * @Author: jeecg-boot
 * @Date:   2020-07-15
 * @Version: V1.0
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements IUserAddressService {

}
