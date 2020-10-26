package com.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

//自定义realm
public class CustomerRealm extends AuthorizingRealm {
    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 从token中获取用户名
        String principal = (String)token.getPrincipal();
        System.out.println("用户名 ---" + principal);
        if ("小明".equals(principal)){
            //password
            String password = "b22d844bd6497948d8cb605d1195bf3a";
            // salt
            ByteSource salt = ByteSource.Util.bytes("Sat Oct 03 14:40:18 CST 2020");

            //数据库中的用户名，数据库中的加密后密码，数据库中salt ，realm名
            return new SimpleAuthenticationInfo(principal,password,salt,this.getName());
        }
        return null;
    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
