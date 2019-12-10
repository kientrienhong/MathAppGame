package com.hkt.fcamp1;

import java.util.Comparator;

public class SortByScore implements Comparator<Student> {
    @Override
    public int compare(Student student, Student t1) {
        return t1.getScore() - student.getScore();
    }
}
