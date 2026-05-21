package model;

import util.Gender;
import util.Generator;

import java.time.LocalDate;

public class Admin extends User{
    private final String adminId;

    public Admin(String name, LocalDate dob, Gender gender) {
        super(name, dob, gender);
        adminId = Generator.generateAdminId();
    }

    public String getAdminId() {
        return adminId;
    }
}
