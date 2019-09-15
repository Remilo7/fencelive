package com.fencelive.services.impl;

import com.fencelive.dao.ListDAO;
import com.fencelive.model.entity.List;
import com.fencelive.services.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListServiceImpl implements ListService {

    @Autowired
    ListDAO listDAO;

    @Override
    @Transactional
    public void add(List list) {
        listDAO.add(list);
    }

    @Override
    @Transactional
    public void edit(List list) {
        listDAO.edit(list);
    }

    @Override
    @Transactional
    public void delete(int id) {
        listDAO.delete(id);
    }

    @Override
    @Transactional
    public List getList(int id) {
        return listDAO.getList(id);
    }

    @Override
    @Transactional
    public java.util.List getAllLists() {
        return listDAO.getAllLists();
    }
}
