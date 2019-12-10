package com.hkt.fcamp1;

import java.util.ArrayList;

public class StudentManagement {
    private ArrayList<Student> list;

    public StudentManagement() {
        this.list = new ArrayList<>();
    }

    public ArrayList<Student> getList() {
        return list;
    }

    public void setList(ArrayList<Student> list) {
        this.list = list;
    }
}
