package model;

import util.Generator;

import java.time.LocalDate;

public class Admin extends User{
    private final String adminId;

    public Admin(String name, LocalDate dob, String gender, String password) {
        super(name, dob, gender, password);
        adminId = Generator.generateAdminId();
    }

    public String getAdminId() {
        return adminId;
    }
}
