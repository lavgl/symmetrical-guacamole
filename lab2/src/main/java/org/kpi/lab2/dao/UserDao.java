package org.kpi.lab2.dao;

import org.kpi.lab2.model.User;

import java.util.*;

public class UserDao {
    static int counter = 0;
    private List<User> users = new ArrayList<>();

    public List<User> getAll() {
        return users;
    }

    public void add(User user) {
        user.setId(counter++);
        users.add(user);
    }

    public void remove(int index) {
        users.remove(index);
    }
}
