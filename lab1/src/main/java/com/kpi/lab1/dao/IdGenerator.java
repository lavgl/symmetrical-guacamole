package com.kpi.lab1.dao;

public enum  IdGenerator {
    INSTANCE;

    private int current = -1;

    public int getCurrent() {
        return  current;
    }

    public int getNext() {
        return ++current;
    }
}
