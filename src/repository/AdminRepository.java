package repository;

import model.Admin;

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