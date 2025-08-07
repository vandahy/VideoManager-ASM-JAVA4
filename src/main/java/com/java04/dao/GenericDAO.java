package com.java04.dao;

import java.util.List;

public interface GenericDAO <T, ID>{
    List<T> findAll();
    T findById(ID id);
    void create(T entity);
    void update(T entity);
    void deleteById(ID id);
}
