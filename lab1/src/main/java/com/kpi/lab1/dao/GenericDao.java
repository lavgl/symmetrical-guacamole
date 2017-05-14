package com.kpi.lab1.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T extends Identified<PK>, PK extends Serializable> {
    public T create(T object) throws PersistException;
    public T getById(PK key) throws PersistException;
    public void update(T object) throws PersistException;
    public void delete(T object) throws PersistException;
    public List<T> getAll() throws PersistException;
}
