package com.fencelive.dao.impl;

import java.util.List;

import com.fencelive.dao.UserInfoDAO;
import com.fencelive.model.entity.UserInfo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoDAOImpl implements UserInfoDAO {

    @Autowired
    private SessionFactory session;

    public void add(UserInfo user) {
        session.getCurrentSession().save(user);
    }

    public void edit(UserInfo user) {
        session.getCurrentSession().update(user);
    }

    public void delete(String username) {
        session.getCurrentSession().delete(findUserInfo(username));
    }

    public UserInfo findUserInfo(String userName) {
        return session.getCurrentSession().get(UserInfo.class, userName);
    }

    public List getAllUserInfo(){
        return session.getCurrentSession().createQuery("FROM UserInfo").list();
    }

}