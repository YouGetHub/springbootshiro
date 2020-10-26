package com.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

public class TestCustomerRealm {
    public static void main(String[] args) {
        // 设置安全管理器
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        // 自定义realm获取数据
        CustomerRealm customerRealm = new CustomerRealm();

        // 设置md5加密
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(1024);//设置散列次数
        customerRealm.setCredentialsMatcher(credentialsMatcher);

        // 设置realm
        securityManager.setRealm(customerRealm);

        // 全局安全工具类设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);

        //获取用户主体
        Subject subject = SecurityUtils.getSubject();

        // 获取token 令牌
        UsernamePasswordToken token = new UsernamePasswordToken("小明","123");

        try {
            System.out.println("认证状态" + subject.isAuthenticated());
            subject.login(token);
            System.out.println("认证状态" + subject.isAuthenticated());
        }catch(UnknownAccountException e){
            e.printStackTrace();
            System.out.println("认证失败 ： 用户名错误");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("认证失败 ： 密码错误");
        }
    }
}
