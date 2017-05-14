package com.kpi.lab1.h2;

import com.kpi.lab1.dao.DaoFactory;
import com.kpi.lab1.dao.GenericDao;
import com.kpi.lab1.dao.PersistException;
import com.kpi.lab1.dto.GroupDto;
import com.kpi.lab1.dto.StudentDto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class H2DaoFactory implements DaoFactory<Connection> {

    private String user = "root";
    private String password = "";
    private String url = "jdbc:h2:~/test";
    private String driver = "org.h2.Driver";
    private Map<Class, DaoCreator> creators;

    public Connection getContext() throws PersistException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return connection;
    }

    public GenericDao getDao(Connection connection, Class dtoClass) throws PersistException {
        DaoCreator creator = creators.get(dtoClass);
        if (creator == null) {
            throw new PersistException("Dao object for " + dtoClass + " not found");
        }
        return creator.create(connection);
    }

    public H2DaoFactory() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        creators = new HashMap<Class, DaoCreator>();
        creators.put(StudentDto.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new H2StudentDao(H2DaoFactory.this, connection);
            }
        });
        creators.put(GroupDto.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new H2GroupDao(H2DaoFactory.this, connection);
            }
        });
    }
}
