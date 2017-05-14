package com.kpi.lab1.h2;

import com.kpi.lab1.dao.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public abstract class AbstractH2Dao<T extends Identified<PK>, PK extends Integer> implements GenericDao<T, PK> {

    public abstract String getSelectQuery();
    public abstract String getCreateQuery();
    public abstract String getUpdateQuery();
    public abstract String getDeleteQuery();

    protected abstract List<T> parseResultSet(ResultSet rs) throws PersistException;

    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws PersistException;
    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws PersistException;

    private DaoFactory<Connection> parentFactory;
    private Connection connection;

    public AbstractH2Dao(DaoFactory<Connection> parentFactory, Connection connection) {
        this.parentFactory = parentFactory;
        this.connection = connection;
    }

    @Override
    public T getById(Integer id) throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        sql += " where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new PersistException("More then one record");
        }
        return list.iterator().next();
    }

    @Override
    public List<T> getAll() throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }

    @Override
    public T create(T object) throws PersistException {
        if (object.getId() != null) {
            throw new PersistException("Object already exists");
        }

        T persistInstance;
        Integer id = IdGenerator.INSTANCE.getNext();

        String sql = getCreateQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForInsert(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("persist");
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }

        sql = getSelectQuery() + " where id = " + id + ";";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            List<T> list = parseResultSet(rs);
            if ((list == null) || (list.size() != 1)) {
                throw new PersistException("lol");
            }

            persistInstance = list.iterator().next();
        } catch (Exception e) {
            throw new PersistException(e);
        }

        return persistInstance;

    }

    @Override
    public void update (T object) throws PersistException {
        String sql = getUpdateQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForUpdate(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On update");
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void delete (T object) throws PersistException {
        String sql = getDeleteQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try {
                statement.setObject(1, object.getId());
            } catch (Exception e) {
                throw new PersistException(e);
            }
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("delete");
            }
            statement.close();
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}
