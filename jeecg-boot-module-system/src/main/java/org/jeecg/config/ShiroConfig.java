package org.jeecg.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.shiro.authc.ShiroRealm;
import org.jeecg.modules.shiro.authc.aop.JwtFilter;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: Scott
 * @date: 2018/2/7
 * @description: shiro 配置类
 */

@Slf4j
@Configuration
public class ShiroConfig {

    @Value("${jeecg.shiro.excludeUrls}")
    private String excludeUrls;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String redisPassword;

    /**
     * Filter Chain定义说明
     * <p>
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        if (oConvertUtils.isNotEmpty(excludeUrls)) {
            String[] permissionUrl = excludeUrls.split(",");
            for (String url : permissionUrl) {
                filterChainDefinitionMap.put(url, "anon");
            }
        }
        //志愿者管理
        filterChainDefinitionMap.put("/volunteer/mdVolunteer/list", "anon");
        filterChainDefinitionMap.put("/volunteer/mdVolunteer/add", "anon");
        filterChainDefinitionMap.put("/volunteer/mdVolunteer/queryById", "anon");

        //企业管理
        filterChainDefinitionMap.put("/enterprise/mdEnterpriseInfo/list", "anon");
        filterChainDefinitionMap.put("/enterprise/mdEnterpriseInfo/add", "anon");
        filterChainDefinitionMap.put("/enterprise/mdEnterpriseInfo/queryById", "anon");


        //活动管理
        filterChainDefinitionMap.put("/volunteeractivity/mdVolunteerActivity/listInfo", "anon");
        filterChainDefinitionMap.put("/volunteeractivity/mdVolunteerActivity/list", "anon");
        filterChainDefinitionMap.put("/volunteeractivity/mdVolunteerActivity/queryById", "anon");
        filterChainDefinitionMap.put("/volunteeractivity/mdVolunteerActivity/queryMdActivityTimeDetailByAlId", "anon");

        //报名签到
        filterChainDefinitionMap.put("/activitylog/mdActivityLog/add", "anon");
        filterChainDefinitionMap.put("/activitylog/mdActivityLog/sign", "anon");
        filterChainDefinitionMap.put("/activitylog/mdActivityLog/list", "anon");

        //积分记录
        filterChainDefinitionMap.put("/integrallog/mdIntegralLog/add", "anon");
        filterChainDefinitionMap.put("/integrallog/mdIntegralLog/list", "anon");


        //地区信息
        filterChainDefinitionMap.put("/sys/sysDepart", "anon");
        filterChainDefinitionMap.put("/sys/sysDepart/queryTreeList", "anon");

        //用户信息
        filterChainDefinitionMap.put("/sys/user/edit", "anon");
        filterChainDefinitionMap.put("/sys/user/queryById", "anon");


        //图片上传
        filterChainDefinitionMap.put("/sys/common/upload", "anon");

        //微信登陆
        filterChainDefinitionMap.put("/wechat/login/save", "anon");
        filterChainDefinitionMap.put("/wechat/login/getPhone", "anon");

        //数据字典
        filterChainDefinitionMap.put("/sys/dictItem/list", "anon");
        filterChainDefinitionMap.put("/sys/dictItem/getDictItemsByKey", "anon");

        //一键服务配置
        filterChainDefinitionMap.put("/oneserver/mdOneServer/add", "anon");
        filterChainDefinitionMap.put("/oneserver/mdOneServer/list", "anon");
        filterChainDefinitionMap.put("/oneserver/mdOneServer/queryById", "anon");

        //轮播图管理
        filterChainDefinitionMap.put("/rcinfo/mdRcInfo/add", "anon");
        filterChainDefinitionMap.put("/rcinfo/mdRcInfo/list", "anon");
        filterChainDefinitionMap.put("/rcinfo/mdRcInfo/queryById", "anon");

        //资源管理
        filterChainDefinitionMap.put("/information/mdInformationInfo/add", "anon");
        filterChainDefinitionMap.put("/information/mdInformationInfo/list", "anon");
        filterChainDefinitionMap.put("/information/mdInformationInfo/queryById", "anon");

        //诉求管理去除拦截
        filterChainDefinitionMap.put("/appeal/mdAppealInfo/add", "anon");
        filterChainDefinitionMap.put("/appeal/mdAppealInfo/list", "anon");
        filterChainDefinitionMap.put("/appeal/mdAppealInfo/queryMdPictureInfoByMainId", "anon");

        //随手拍管理去除拦截
        filterChainDefinitionMap.put("/clapwill/mdClapWillInfo/add", "anon");
        filterChainDefinitionMap.put("/clapwill/mdClapWillInfo/list", "anon");
        filterChainDefinitionMap.put("/clapwill/mdClapWillInfo/queryMdPictureInfoByMainId", "anon");
        filterChainDefinitionMap.put("/clapwill/mdClapWillInfo/queryMdCwInfoByMainId", "anon");

        //商城
        //预约订单管理
        filterChainDefinitionMap.put("/shop/bookingOrder/list", "anon");
        filterChainDefinitionMap.put("/shop/bookingOrder/add", "anon");
        filterChainDefinitionMap.put("/shop/bookingOrder/edit", "anon");
        filterChainDefinitionMap.put("/shop/bookingOrder/queryById", "anon");

        //借用订单
        filterChainDefinitionMap.put("/shop/borrow/list", "anon");
        filterChainDefinitionMap.put("/shop/borrow/add", "anon");
        filterChainDefinitionMap.put("/shop/borrow/edit", "anon");
        filterChainDefinitionMap.put("/shop/borrow/queryById", "anon");

        //借用商品管理
        filterChainDefinitionMap.put("/shop/borrowingGoodsInfo/list", "anon");
        filterChainDefinitionMap.put("/shop/borrowingGoodsInfo/add", "anon");
        filterChainDefinitionMap.put("/shop/borrowingGoodsInfo/edit", "anon");
        filterChainDefinitionMap.put("/shop/borrowingGoodsInfo/queryById", "anon");

        //家政订单管理
        filterChainDefinitionMap.put("/shop/domesticOrder/list", "anon");
        filterChainDefinitionMap.put("/shop/domesticOrder/add", "anon");
        filterChainDefinitionMap.put("/shop/domesticOrder/edit", "anon");
        filterChainDefinitionMap.put("/shop/domesticOrder/queryById", "anon");

        // 支付测试
        filterChainDefinitionMap.put("/shop/domesticOrder/toPay", "anon");
        // 支付回调
        filterChainDefinitionMap.put("/transaction/wxNotify/*", "anon");

        //家政服务列表
        filterChainDefinitionMap.put("/shop/domesticServiceInfo/list", "anon");
        filterChainDefinitionMap.put("/shop/domesticServiceInfo/add", "anon");
        filterChainDefinitionMap.put("/shop/domesticServiceInfo/edit", "anon");
        filterChainDefinitionMap.put("/shop/domesticServiceInfo/queryById", "anon");

        //评价管理
        filterChainDefinitionMap.put("/shop/evaluateInfo/list", "anon");
        filterChainDefinitionMap.put("/shop/evaluateInfo/add", "anon");
        filterChainDefinitionMap.put("/shop/evaluateInfo/edit", "anon");
        filterChainDefinitionMap.put("/shop/evaluateInfo/queryById", "anon");

        //预约项目列表
        filterChainDefinitionMap.put("/shop/orderServiceInfo/list", "anon");
        filterChainDefinitionMap.put("/shop/orderServiceInfo/add", "anon");
        filterChainDefinitionMap.put("/shop/orderServiceInfo/edit", "anon");
        filterChainDefinitionMap.put("/shop/orderServiceInfo/queryById", "anon");

        //推荐管理
        filterChainDefinitionMap.put("/shop/recommend/list", "anon");
        filterChainDefinitionMap.put("/shop/recommend/add", "anon");
        filterChainDefinitionMap.put("/shop/recommend/edit", "anon");
        filterChainDefinitionMap.put("/shop/recommend/queryById", "anon");

        //用户地址管理
        filterChainDefinitionMap.put("/shop/userAddress/list", "anon");
        filterChainDefinitionMap.put("/shop/userAddress/add", "anon");
        filterChainDefinitionMap.put("/shop/userAddress/edit", "anon");
        filterChainDefinitionMap.put("/shop/userAddress/queryById", "anon");

        //用户地址管理
        filterChainDefinitionMap.put("/shop/shopRcInfo/list", "anon");
        filterChainDefinitionMap.put("/shop/shopRcInfo/queryById", "anon");

        //支付
        filterChainDefinitionMap.put("/transaction/**", "anon");

        //cas验证登录
        filterChainDefinitionMap.put("/cas/client/validateLogin", "anon");
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/sys/getCheckCode", "anon"); //登录验证码接口排除
        filterChainDefinitionMap.put("/sys/randomImage/**", "anon"); //登录验证码接口排除
        filterChainDefinitionMap.put("/sys/login", "anon"); //登录接口排除
        filterChainDefinitionMap.put("/sys/mLogin", "anon"); //登录接口排除
        filterChainDefinitionMap.put("/sys/logout", "anon"); //登出接口排除
        filterChainDefinitionMap.put("/sys/getEncryptedString", "anon"); //获取加密串
        filterChainDefinitionMap.put("/sys/sms", "anon");//短信验证码
        filterChainDefinitionMap.put("/sys/phoneLogin", "anon");//手机登录
        filterChainDefinitionMap.put("/sys/user/checkOnlyUser", "anon");//校验用户是否存在
        filterChainDefinitionMap.put("/sys/user/register", "anon");//用户注册
        filterChainDefinitionMap.put("/sys/user/querySysUser", "anon");//根据手机号获取用户信息
        filterChainDefinitionMap.put("/sys/user/phoneVerification", "anon");//用户忘记密码验证手机号
        filterChainDefinitionMap.put("/sys/user/passwordChange", "anon");//用户更改密码
        filterChainDefinitionMap.put("/auth/2step-code", "anon");//登录验证码
        filterChainDefinitionMap.put("/sys/common/static/**", "anon");//图片预览 &下载文件不限制token
        //filterChainDefinitionMap.put("/sys/common/view/**", "anon");//图片预览不限制token
        //filterChainDefinitionMap.put("/sys/common/download/**", "anon");//文件下载不限制token
        filterChainDefinitionMap.put("/sys/common/pdf/**", "anon");//pdf预览
        filterChainDefinitionMap.put("/generic/**", "anon");//pdf预览需要文件
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/doc.html", "anon");
        filterChainDefinitionMap.put("/**/*.js", "anon");
        filterChainDefinitionMap.put("/**/*.css", "anon");
        filterChainDefinitionMap.put("/**/*.html", "anon");
        filterChainDefinitionMap.put("/**/*.svg", "anon");
        filterChainDefinitionMap.put("/**/*.pdf", "anon");
        filterChainDefinitionMap.put("/**/*.jpg", "anon");
        filterChainDefinitionMap.put("/**/*.png", "anon");
        filterChainDefinitionMap.put("/**/*.ico", "anon");

        // update-begin--Author:sunjianlei Date:20190813 for：排除字体格式的后缀
        filterChainDefinitionMap.put("/**/*.ttf", "anon");
        filterChainDefinitionMap.put("/**/*.woff", "anon");
        filterChainDefinitionMap.put("/**/*.woff2", "anon");
        // update-begin--Author:sunjianlei Date:20190813 for：排除字体格式的后缀

        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger**/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");

        //性能监控
        filterChainDefinitionMap.put("/actuator/metrics/**", "anon");
        filterChainDefinitionMap.put("/actuator/httptrace/**", "anon");
        filterChainDefinitionMap.put("/actuator/redis/**", "anon");

        //测试示例
        filterChainDefinitionMap.put("/test/jeecgDemo/html", "anon"); //模板页面
        filterChainDefinitionMap.put("/test/jeecgDemo/redis/**", "anon"); //redis测试

        //排除Online请求
        filterChainDefinitionMap.put("/auto/cgform/**", "anon");

        //websocket排除
        filterChainDefinitionMap.put("/websocket/**", "anon");

        //大屏设计器排除
        filterChainDefinitionMap.put("/big/screen/**", "anon");

        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<String, Filter>(1);
        filterMap.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        // <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
        filterChainDefinitionMap.put("/**", "jwt");


        // 未授权界面返回JSON
        shiroFilterFactoryBean.setUnauthorizedUrl("/sys/common/403");
        shiroFilterFactoryBean.setLoginUrl("/sys/common/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(ShiroRealm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm);

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-
         * StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        //自定义缓存实现,使用redis
        securityManager.setCacheManager(redisCacheManager());
        return securityManager;
    }

    /**
     * 下面的代码是添加注解支持
     *
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager redisCacheManager() {
        log.info("===============(1)创建缓存管理器RedisCacheManager");
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //redis中针对不同用户缓存(此处的id需要对应user实体中的id字段,用于唯一标识)
        redisCacheManager.setPrincipalIdFieldName("id");
        //用户权限信息缓存时间
        redisCacheManager.setExpire(200000);
        return redisCacheManager;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        log.info("===============(2)创建RedisManager,连接Redis..URL= " + host + ":" + port);
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(oConvertUtils.getInt(port));
        redisManager.setTimeout(0);
        if (!StringUtils.isEmpty(redisPassword)) {
            redisManager.setPassword(redisPassword);
        }
        return redisManager;
    }

}
