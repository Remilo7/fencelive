package com.startopole.services.impl;

import com.startopole.dao.UserRoleDAO;
import com.startopole.model.entity.UserRole;
import com.startopole.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Transactional
    public void add(UserRole userRole) {
        userRoleDAO.add(userRole);
    }

    @Transactional
    public void edit(UserRole userRole) {
        userRoleDAO.edit(userRole);
    }

    @Transactional
    public void delete(int id) {
        userRoleDAO.delete(id);
    }

    @Transactional
    public UserRole getUserRole(int id) {
        return userRoleDAO.getUserRole(id);
    }

    @Transactional
    public List getAllUserRoles(String username) {
        return userRoleDAO.getAllUserRoles(username);
    }
}
