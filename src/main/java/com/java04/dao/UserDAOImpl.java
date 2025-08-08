package com.java04.dao;

import com.java04.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager em;

    public UserDAOImpl() {
        em = Persistence.createEntityManagerFactory("myPersistenceUnit").createEntityManager();
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User findById(String id) {
        String jpql = "SELECT u FROM User u LEFT JOIN FETCH u.favorites WHERE u.id = :id";
        return em.createQuery(jpql, User.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void create(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    @Override
    public void update(User user) {
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
    }

    @Override
    public void deleteById(String id) {
        em.getTransaction().begin();
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
        em.getTransaction().commit();
    }

    @Override
    public User findByIdOrEmail(String idOrEmail) {
        try {
            return em.createQuery(
                            "SELECT u FROM User u WHERE u.id = :value OR u.email = :value", User.class)
                    .setParameter("value", idOrEmail)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public String generateNewUserId() {
        String lastId = em.createQuery("SELECT u.id FROM User u ORDER BY u.id DESC", String.class)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse("USER00");

        // Đảm bảo viết hoa ID
        lastId = lastId.toUpperCase();

        // Kiểm tra nếu ID bắt đầu bằng "USER" và phía sau là số
        if (lastId.startsWith("USER") && lastId.length() > 4) {
            try {
                int number = Integer.parseInt(lastId.substring(4)); // Lấy phần số
                number++;
                return String.format("USER%02d", number);
            } catch (NumberFormatException e) {
                // Nếu phần số không hợp lệ thì quay về USER01
                return "USER01";
            }
        }

        // Nếu định dạng sai hoàn toàn thì bắt đầu lại từ USER01
        return "USER01";
    }

}
