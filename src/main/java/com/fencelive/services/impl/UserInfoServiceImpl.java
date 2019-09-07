package com.fencelive.services.impl;

import com.fencelive.dao.UserInfoDAO;
import com.fencelive.model.entity.UserInfo;
import com.fencelive.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDAO userInfoDAO;

    @Transactional
    public void add(UserInfo user) {
        userInfoDAO.add(user);
    }

    @Transactional
    public void edit(UserInfo user) {
        userInfoDAO.edit(user);
    }

    @Transactional
    public void delete(String username) {
        userInfoDAO.delete(username);
    }

    @Transactional
    public UserInfo findUserInfo(String userName) {
        return userInfoDAO.findUserInfo(userName);
    }

    @Transactional
    public List getAllUserInfo() {
        return userInfoDAO.getAllUserInfo();
    }
}
