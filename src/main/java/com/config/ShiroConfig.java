package com.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.shiro.CustomerRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {
    // 创建shirofilter 拦截所有请求
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean (DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        // 配置受限的资源
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("/user/login","anon"); // aone 匿名也可以访问的资源
        linkedHashMap.put("/user/register","anon"); // aone 匿名也可以访问的资源
        linkedHashMap.put("/user/image","anon"); // aone 匿名也可以访问的资源
        linkedHashMap.put("/register","anon"); // aone 匿名也可以访问的资源
        linkedHashMap.put("/**","authc"); // authc 请求的这个资源需要认证


        // 认证的路径
        shiroFilterFactoryBean.setLoginUrl("/login"); // 默认登录页面
        shiroFilterFactoryBean.setFilterChainDefinitionMap(linkedHashMap);

        return shiroFilterFactoryBean;
    }

    // 创建安全管理器
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(CustomerRealm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    // 创建自定义realm
    @Bean
    public CustomerRealm realm(){
        CustomerRealm customerRealm = new CustomerRealm();

        //修改密码校验匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置散列算法
        credentialsMatcher.setHashAlgorithmName("MD5");
        //散列次数
        credentialsMatcher.setHashIterations(2);
        customerRealm.setCredentialsMatcher(credentialsMatcher);

      /*  //shiro中默认EhCache实现缓存
        // 开启缓存管理器
        customerRealm.setCachingEnabled(true); //全局缓存
        customerRealm.setAuthenticationCachingEnabled(true); // 认证缓存
        customerRealm.setAuthenticationCacheName("authenticationCache");
        customerRealm.setAuthorizationCachingEnabled(true); // 授权缓存
        customerRealm.setAuthorizationCacheName("authorizationCache");
        customerRealm.setCacheManager(new EhCacheManager());*/

        return customerRealm;
    }

    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}
