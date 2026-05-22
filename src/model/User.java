package model;

import util.Gender;

import java.time.LocalDate;

public abstract class User {
    private final String name;
    private final LocalDate dob;
    private final Gender gender;

    public User(String name, LocalDate dob, Gender gender){
        if (name==null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (dob == null || dob.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Date of Birth must be on or before the current date");
        }

        if (gender == null){
            throw new IllegalArgumentException("Gender field mustn't be left empty");
        }

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
