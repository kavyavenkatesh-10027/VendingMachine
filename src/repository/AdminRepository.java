package repository;

import model.Admin;
import util.VendingMachineException;

public class AdminRepository extends BaseRepository<Admin> {

    private static AdminRepository instance;

    private AdminRepository() {}

    public static AdminRepository getInstance() {
        if (instance == null) instance = new AdminRepository();
        return instance;
    }

    @Override
    protected String getId(Admin admin) {
        return admin.getAdminId();
    }
}
//import model.Admin;
//import util.VendingMachineException;
//
//import java.util.*;
//
//public class AdminRepository {
//
//    private static AdminRepository instance;
//    private final Map<String, Admin> admins = new HashMap<>();
//
//    private AdminRepository() {}
//
//    public static AdminRepository getInstance() {
//        if (instance == null) {
//            instance = new AdminRepository();
//        }
//        return instance;
//    }
//
//    public void add(Admin admin) {
//        if (admin == null) {
//            throw new VendingMachineException("Admin cannot be null.");
//        }
//        admins.put(admin.getAdminId(), admin);
//    }
//
//    public Admin findById(String adminId) {
//        if (adminId == null) {
//            throw new VendingMachineException("Admin ID cannot be null");
//        }
//        return admins.get(adminId);
//    }
//
//    public List<Admin> findAll() {
//        return Collections.unmodifiableList(new ArrayList<>(admins.values()));
//    }
//
//    public boolean removeById(String adminId) {
//        return admins.remove(adminId) != null;
//    }
//}