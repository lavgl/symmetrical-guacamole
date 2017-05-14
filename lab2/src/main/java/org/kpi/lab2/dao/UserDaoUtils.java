package org.kpi.lab2.dao;

import org.kpi.lab2.model.User;

public class UserDaoUtils {
    public static void populateDao(UserDao dao) {
        dao.add(new User("user 1"));
        dao.add(new User("user 2"));
        dao.add(new User("user 3"));
    }
}
