import dao.RoleDAOImpl;
import dao.UserDAOImpl;
import model.Role;
import model.User;
import util.Util;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final UserDAOImpl userDAO = new UserDAOImpl();
    private static final RoleDAOImpl roleDAO = new RoleDAOImpl();

    private static List<Role> roles = new ArrayList<Role>();
    private static final User user1 = new User("Mitroy", (byte) 60, "ddfhj@mail.ru", roles);
    private static final User user2 = new User("Jija", (byte) 15, "sdf@mail.ru", roles);
    private static final User user3 = new User("Butj", (byte) 15, "sdiuyf@mail.ru", roles);
    private static final User user4 = new User("Kolyr", (byte) 15, "yusdf@mail.ru", roles);

    public static void main(String[] args) {
        userDAO.createUsersTable();
        roleDAO.createRolesTable();
        roleDAO.createUsersRolesTable();

        roleDAO.saveRole(new Role("ROLE_ADMIN"));
        roleDAO.saveRole(new Role("ROLE_USER"));

        roles.add(roleDAO.findRoleById(1L));

        userDAO.saveUser(user1);

        userDAO.saveUser(user2);

        userDAO.saveUser(user3);

        userDAO.saveUser(user4);

        List<User> strings = userDAO.getAllUsers();
        for (User s : strings)
            System.out.println(s);


        //userDAO.removeUserById(3);

        //userDAO.cleanUsersTable();

        //userDAO.dropUsersTable();

        Util.closeConnection();
    }
}
