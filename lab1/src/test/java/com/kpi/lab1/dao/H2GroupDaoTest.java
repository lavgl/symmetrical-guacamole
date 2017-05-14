package com.kpi.lab1.dao;

import com.kpi.lab1.dto.GroupDto;
import com.kpi.lab1.h2.H2DaoFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class H2GroupDaoTest {
    private Connection connection;

    private GenericDao dao;

    private static final DaoFactory<Connection> factory = new H2DaoFactory();

    public GroupDto getMock() {
        return GroupDto
                .builder()
                .name("test group")
                .build();
    }

    public void insertMock() {
        GroupDto mock = getMock();

        try {
            dao.create(mock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() throws PersistException, SQLException {
        connection = factory.getContext();
        connection.setAutoCommit(false);
        dao = factory.getDao(connection, GroupDto.class);

        insertMock();
    }

    @After
    public void tearDown() throws SQLException {
        connection.rollback();
        connection.close();
    }

    @Test
    public void testGetAll() throws Exception {
        List<GroupDto> list = dao.getAll();

        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void testGetById() throws Exception {
        Identified group = dao.getById(IdGenerator.INSTANCE.getCurrent());

        Assert.assertNotNull(group);
    }

    @Test
    public void testCreate() throws Exception {
        String name = "test group";

        GroupDto groupDto = GroupDto
                .builder()
                .name(name)
                .build();

        GroupDto created = (GroupDto) dao.create(groupDto);

        Assert.assertNotNull(created);
        Assert.assertEquals(name, created.getName());
    }

    @Test
    public void testUpdate() throws Exception {
        GroupDto group = getMock();
        String newName = "new name";
        group.setId(IdGenerator.INSTANCE.getCurrent());
        group.setName(newName);

        dao.update(group);

        Assert.assertNotNull(group);
        Assert.assertEquals(newName, group.getName());
    }

    @Test
    public void testDelete() throws Exception {
        GroupDto group = getMock();
        group.setId(IdGenerator.INSTANCE.getCurrent());
        dao.delete(group);

        List<GroupDto> groups = dao.getAll();

        Assert.assertTrue(groups.size() == 0);
    }

}
