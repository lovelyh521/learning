package com.xiejieyi.rbac;

import com.xiejieyi.rbac.bean.Permission;
import com.xiejieyi.rbac.bean.Role;
import com.xiejieyi.rbac.bean.User;
import com.xiejieyi.rbac.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 *
 * @author
 */
public class CustomUserService implements UserDetailsService
{

    @Autowired
    UserRepository userRepository;

    public UserDetails loadUserByUsername(String username)
    {
        User user = userRepository.findByUsername(username);
        System.out.println(user);
        if (user == null)
        {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
        List<Role> roles = user.getRoles();
        if (roles == null || roles.size() == 0)
        {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : roles)
        {
            List<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions)
            {
                if (permission != null && permission.getName() != null)
                {
                    System.out.println("permission name=" + permission.getName());
                    GrantedAuthority grantedAuthority = new MyGrantedAuthority(permission.getUrl(),permission.getMethod());
                    //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                    grantedAuthorities.add(grantedAuthority);
                }
            }
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);


        // try
        // {
        //     System.out.println(username);
        //     User user = userRepository.findByUsername(username);
        //     if (user == null)
        //     {
        //         System.out.println("error");
        //         throw new UsernameNotFoundException("用户名不存在");
        //     }
        //     System.out.println(user);
        //     List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //     //用于添加用户的权限。只要把用户权限添加到authorities 就万事大吉。
        //     for (Role role : user.getRoles())
        //     {
        //         authorities.add(new SimpleGrantedAuthority(role.getName()));
        //         System.out.println(role.getName());
        //     }
        //     return new org.springframework.security.core.userdetails.User(user.getUsername(),
        //             user.getPassword(), authorities);
        // } catch (Exception ex)
        // {
        //     System.out.println(ex);
        // }
        // return null;


    }
}