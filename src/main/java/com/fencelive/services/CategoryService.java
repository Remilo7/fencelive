package com.fencelive.services;

import com.fencelive.model.entity.Category;

import java.util.List;

public interface CategoryService {

    public void add(Category category);
    public void edit(Category category);
    public void delete(int categoryId);
    public Category getCategory(int categoryId);
    public List getAllCategories();
}
