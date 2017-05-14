package com.kpi.lab1.h2;

import com.kpi.lab1.dao.DaoFactory;
import com.kpi.lab1.dao.IdGenerator;
import com.kpi.lab1.dto.StudentDto;
import com.kpi.lab1.dao.PersistException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class H2StudentDao extends AbstractH2Dao<StudentDto, Integer> {

    public H2StudentDao(DaoFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
    }

    @Override
    public String getSelectQuery() {
        return "select id, firstName, lastName, age from students";
    }

    @Override
    public String getCreateQuery() {
        return "insert into students values (?,?,?,?);";
    }

    @Override
    public String getUpdateQuery() {
        return "update students set firstname = ?, lastname = ?, age = ? where id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "delete from students where id = ?;";
    }

    protected List<StudentDto> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<StudentDto> result = new LinkedList<>();
        try {
            while (rs.next()) {
                StudentDto student = StudentDto
                        .builder()
                        .id(rs.getInt("id"))
                        .firstName(rs.getString("firstName"))
                        .lastName(rs.getString("lastName"))
                        .age(rs.getInt("age"))
                        .build();
                result.add(student);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    protected void prepareStatementForInsert(PreparedStatement statement, StudentDto object) throws PersistException {
        try {
            // getNext is already called in create method
            statement.setInt(1, IdGenerator.INSTANCE.getCurrent());
            statement.setString(2, object.getFirstName());
            statement.setString(3, object.getLastName());
            statement.setInt(4, object.getAge());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    protected void prepareStatementForUpdate(PreparedStatement statement, StudentDto object) throws PersistException {
        try {
            statement.setString(1, object.getFirstName());
            statement.setString(2, object.getLastName());
            statement.setInt(3, object.getAge());
            statement.setInt(4, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}
