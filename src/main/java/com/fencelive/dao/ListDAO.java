package com.fencelive.dao;

import com.fencelive.model.entity.List;

public interface ListDAO {

    public void add(List list);
    public void edit(List list);
    public void delete(int id);
    public List getList(int id);
    public java.util.List getAllLists();
}
