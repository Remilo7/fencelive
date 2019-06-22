package com.startopole.dao;

import com.startopole.model.entity.UserInfo;

import java.util.List;

public interface UserInfoDAO {

    public void add(UserInfo user);
    public void edit(UserInfo user);
    public void delete(String username);
    public UserInfo findUserInfo(String userName);
    public List getAllUserInfo();
}