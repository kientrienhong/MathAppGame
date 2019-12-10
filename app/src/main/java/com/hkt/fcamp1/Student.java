package com.hkt.fcamp1;

import java.io.Serializable;

public class Student implements Serializable {
    private String phoneNumber;
    private String name;
    private int score;

    public Student(String phoneNumber, String name, int score) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.score = score;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
