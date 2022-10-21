package dao;

import model.Role;
import model.User;

import java.util.List;

public interface RoleDAO {
    void createRolesTable();

    void createUsersRolesTable();

    void dropRolesTable();

    void saveRole(Role role);

    Role findRoleById(long id);

    void removeRoleById(long id);

    List<Role> getAllRoles();

    void cleanRolesTable();
}
