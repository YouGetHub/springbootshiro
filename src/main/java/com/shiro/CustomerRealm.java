package com.shiro;

import com.entity.Permission;
import com.entity.Role;
import com.entity.User;
import com.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 自定义realm
 */
public class CustomerRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //根据身份信息
        String principal = (String) token.getPrincipal();
        System.out.println("AuthenticationInfo ---------" + principal);
        // 根据用户名查询到用户数据
        User user = userService.findByUserName(principal);
        if (!ObjectUtils.isEmpty(user)){
            return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(), ByteSource.Util.bytes(user.getSalt()),this.getName());
        }
        return null;
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户信息
        String principal = (String)principalCollection.getPrimaryPrincipal();
        //根据用户名获取 角色权限
        User user = userService.findByRolesUserName(principal);
        // 获取权限
        List<Role> roles = user.getRoles();
        if (!CollectionUtils.isEmpty(roles)){
            for (Role role : roles){
                SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
                // 授权角色
                simpleAuthorizationInfo.addRole(role.getName());
                // 授权权限
                Role permissionRoleId = userService.findByPermissionRoleId(role.getId());
                List<Permission> permissions = permissionRoleId.getPermissions();
                if (!CollectionUtils.isEmpty(permissions)){
                    for (Permission permission : permissions){
                        simpleAuthorizationInfo.addStringPermission(permission.getName());
                    }
                }
                return simpleAuthorizationInfo;
            }
        }
        return null;
    }
}