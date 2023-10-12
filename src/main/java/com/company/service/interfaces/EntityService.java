package com.company.service.interfaces;

import java.util.List;

public interface EntityService<T> {
    List<T> getAll();

    T getById(String id);

    boolean isExists(T entity);

    T save(T entity);

    void update(T entity);

    void deleteById(String id);

    void deleteAll();
}
