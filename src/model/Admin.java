package model;

import util.Gender;
import util.Generator;

import java.time.LocalDate;
import java.util.Objects;

public class Admin extends User{
    private final String adminId;

    public Admin(String name, LocalDate dob, Gender gender) {
        super(name, dob, gender);
        adminId = Generator.generateAdminId();
    }

    public String getAdminId() {
        return adminId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Admin admin = (Admin) o;
        return Objects.equals(getAdminId(), admin.getAdminId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(adminId);
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + "Admin ID : " + adminId;
    }
}
