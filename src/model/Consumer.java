package model;

import util.Gender;

import java.time.LocalDate;

public class Consumer extends User{
    public Consumer(String name, LocalDate dob, Gender gender) {
        super(name, dob, gender);
    }
}
//For future use-case, if necessary