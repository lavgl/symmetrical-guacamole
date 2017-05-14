package com.kpi.lab1.dao;

import com.kpi.lab1.dto.StudentDto;
import com.kpi.lab1.h2.H2DaoFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLType;
import java.util.List;

public class H2StudentDaoTest {

    private Connection connection;

    private GenericDao dao;

    private static final DaoFactory<Connection> factory = new H2DaoFactory();

    public StudentDto getMock() {
        return StudentDto
                .builder()
                .firstName("first name")
                .lastName("last name")
                .age(42)
                .build();
    }

    public void insertMock() {
        StudentDto mock = getMock();

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
        dao = factory.getDao(connection, StudentDto.class);

        insertMock();
    }

    @After
    public void tearDown() throws SQLException {
        connection.rollback();
        connection.close();
    }

    @Test
    public void testGetAll() throws Exception {
        List<StudentDto> list = dao.getAll();

        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void testGetById() throws Exception {
        Identified student = dao.getById(IdGenerator.INSTANCE.getCurrent());

        Assert.assertNotNull(student);
    }

    @Test
    public void testCreate() throws Exception {
        String firstName = "test";
        String lastName = "test";
        int age = 42;

        StudentDto studentDto = StudentDto
                .builder()
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .build();

        StudentDto created = (StudentDto) dao.create(studentDto);

        Assert.assertNotNull(created);
        Assert.assertEquals(firstName, created.getFirstName());
        Assert.assertEquals(lastName, created.getLastName());
    }

    @Test
    public void testUpdate() throws Exception {
        String newFirstName = "test";

        StudentDto student = getMock();
        student.setId(IdGenerator.INSTANCE.getCurrent());
        student.setFirstName(newFirstName);

        dao.update(student);

        Assert.assertNotNull(student);
        Assert.assertEquals(newFirstName, student.getFirstName());
        Assert.assertEquals(getMock().getLastName(), student.getLastName());
    }

    @Test
    public void testDelete() throws Exception {
        StudentDto student = getMock();
        student.setId(IdGenerator.INSTANCE.getCurrent());
        dao.delete(student);

        List<StudentDto> students = dao.getAll();

        System.out.println(students);

        Assert.assertTrue(students.size() == 0);
    }

}
