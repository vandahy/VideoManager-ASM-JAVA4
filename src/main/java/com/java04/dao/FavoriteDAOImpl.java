package com.java04.dao;

import com.java04.dto.VideoSearchInfo;
import com.java04.entity.Favorite;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class FavoriteDAOImpl implements FavoriteDAO{
    private EntityManager em;

    public FavoriteDAOImpl(){
        em = Persistence.createEntityManagerFactory("myPersistenceUnit").createEntityManager();
    }

    @Override
    public List<Favorite> findAll(){
        return em.createQuery("select f from Favorite f", Favorite.class).getResultList();
    }

    @Override
    public Favorite findById(Long id) {
        return em.find(Favorite.class, id);
    }

    @Override
    public void create(Favorite favorite) {
        em.getTransaction().begin();
        em.persist(favorite);
        em.getTransaction().commit();
    }

    @Override
    public void update(Favorite favorite) {
        em.getTransaction().begin();
        em.merge(favorite);
        em.getTransaction().commit();
    }

    @Override
    public void deleteById(Long id) {
        em.getTransaction().begin();
        Favorite favorite = em.find(Favorite.class, id);
        if (favorite != null) {
            em.remove(favorite);
        }
        em.getTransaction().commit();
    }

    @Override
    public List<VideoSearchInfo> searchVideosByKeyword(String keyword) {
        return em.createQuery(
                        "SELECT new com.java04.dto.VideoSearchInfo(" +
                                "f.video.title, COUNT(f), MAX(f.likeDate)) " +
                                "FROM Favorite f WHERE f.video.title LIKE :kw " +
                                "GROUP BY f.video.title", VideoSearchInfo.class)
                .setParameter("kw", "%" + keyword + "%")
                .getResultList();
    }

    @Override
    public List<Favorite> findByUserId(String userId) {
        return em.createQuery(
                        "SELECT f FROM Favorite f WHERE f.user.id = :uid", Favorite.class)
                .setParameter("uid", userId)
                .getResultList();
    }
}
