package repository;

import model.Admin;
import util.VendingMachineException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdminRepository {

    private static AdminRepository instance;
    private final List<Admin> admins = new ArrayList<>();

    private AdminRepository() {}

    public static AdminRepository getInstance() {
        if (instance == null) {
            instance = new AdminRepository();
        }
        return instance;
    }

    public void add(Admin admin) {
        if (admin == null){
            throw new VendingMachineException("Admin cannot be null.");
        }
        admins.add(admin);
    }
    public Admin findById(String adminId) {
        for(Admin admin: admins){
            if(admin.getAdminId().equals(adminId)){
                return admin;
            }
        }
        return null;
    }

    public List<Admin> findAll() {
        return Collections.unmodifiableList(admins);
    }

    public boolean removeById(String adminId) {
        Admin adminToRemove = findById(adminId);
        if(adminToRemove!=null){
            admins.remove(adminToRemove);
            return true;
        }
        return false;
    }
}
