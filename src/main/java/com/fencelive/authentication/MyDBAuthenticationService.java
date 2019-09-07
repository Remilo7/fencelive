package com.fencelive.authentication;

import java.util.ArrayList;
import java.util.List;

import com.fencelive.model.entity.UserInfo;
import com.fencelive.model.entity.UserRole;
import com.fencelive.services.UserInfoService;
import com.fencelive.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyDBAuthenticationService implements UserDetailsService {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserInfoService userInfoService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo userInfo = userInfoService.findUserInfo(username);
        System.out.println("UserInfo= " + userInfo);

        if (userInfo == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        // [USER,ADMIN,..]
        List<UserRole> roles= userRoleService.getAllUserRoles(username);

        List<GrantedAuthority> grantList= new ArrayList<GrantedAuthority>();
        if(roles!= null)  {
            for(UserRole role: roles)  {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getUser_role());
                grantList.add(authority);
            }
        }

        UserDetails userDetails = (UserDetails) new User(userInfo.getUserName(), //
                userInfo.getPassword(),grantList);

        return userDetails;
    }

}