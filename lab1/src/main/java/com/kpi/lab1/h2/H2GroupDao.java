package com.kpi.lab1.h2;

import com.kpi.lab1.dao.DaoFactory;
import com.kpi.lab1.dao.IdGenerator;
import com.kpi.lab1.dao.PersistException;
import com.kpi.lab1.dto.GroupDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class H2GroupDao extends AbstractH2Dao<GroupDto, Integer> {
    public H2GroupDao(DaoFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
    }

    @Override
    public String getSelectQuery() {
        return "select id, name from Groups";
    }

    @Override
    public String getCreateQuery() {
        return "insert into Groups values (?,?);";
    }

    @Override
    public String getUpdateQuery() {
        return "update Groups set name = ? where id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "delete from Groups where id = ?;";
    }

    @Override
    protected List<GroupDto> parseResultSet(ResultSet rs) throws PersistException {
        List<GroupDto> result = new LinkedList<>();
        try {
            while (rs.next()) {
                GroupDto group = GroupDto
                        .builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build();
                result.add(group);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, GroupDto object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setInt(2, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, GroupDto object) throws PersistException {
        try {
            statement.setInt(1, IdGenerator.INSTANCE.getCurrent());
            statement.setString(2, object.getName());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}
