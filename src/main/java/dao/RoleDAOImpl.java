package dao;

import model.Role;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.Util;

import java.util.ArrayList;
import java.util.List;

public class RoleDAOImpl implements RoleDAO{
    private final SessionFactory sessionFactory = Util.getConnection();

    @Override
    public void createRolesTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS security.roles" +
                    " (id int not null auto_increment, role VARCHAR(200) UNIQUE, " +
                    "PRIMARY KEY (id))").executeUpdate();
            transaction.commit();
            System.out.println("Таблица roles создана");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void createUsersRolesTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS security.users_roles" +
                    " (users_id int not null, roles_id int not null, " +
                    " FOREIGN KEY (users_id) REFERENCES users(id), " +
                    " FOREIGN KEY (roles_id) REFERENCES roles(id)," +
                    " UNIQUE (users_id, roles_id))").executeUpdate();
            transaction.commit();
            System.out.println("Таблица users_roles создана");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropRolesTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("Drop table if exists security.roles").executeUpdate();
            transaction.commit();
            System.out.println("Таблица roles удалена");
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
    }

    @Override
    public void saveRole(Role role) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(new Role(role.getRole()));
            transaction.commit();
            System.out.println("Role с именем – " + role.getRole() + " добавлен в базу данных");
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
    }

    @Override
    public Role findRoleById(long id) {
        Session session = sessionFactory.openSession();
        Role role = null;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            role = session.get(Role.class, id);
            transaction.commit();
            System.out.println("Role найден");
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
        return role;
    }

    @Override
    public void removeRoleById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(session.get(Role.class, id));
            transaction.commit();
            System.out.println("Role удален");
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> list = new ArrayList<Role>();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            list = session.createCriteria(Role.class).list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
        return list;
    }

    @Override
    public void cleanRolesTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            final List<Role> instances = session.createCriteria(Role.class).list();

            for (Object o : instances) {
                session.delete(o);
            }

            session.getTransaction().commit();
            System.out.println("Таблица roles очищена");
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
    }
}
