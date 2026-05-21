package model;

import util.Gender;

import java.time.LocalDate;

public abstract class User {
    private final String name;
    private final LocalDate dob;
    private final Gender gender;

    public User(String name, LocalDate dob, Gender gender){
        this.name = name;
        this.dob = dob;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Name : " + name + '\n' +
                "Date of Birth : " + dob  + "\n" +
                " Gender : " + gender;
    }
}
