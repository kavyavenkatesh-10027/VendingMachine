package model;

import java.time.LocalDate;

public abstract class User {
    private final String name;
    private final LocalDate dob;
    private final String gender;
    private final String password;

    public User(String name, LocalDate dob, String gender, String password){
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.password = password;
    }

    public String getName() {
        return gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Name : " + name + '\n' + "Date of Birth : " + dob  + " Gender : " + gender;
    }
}
