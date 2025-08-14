package com.java04.dao;

import com.java04.dto.VideoSearchInfo;
import com.java04.dto.FavoriteReportDTO;
import com.java04.dto.FavoriteUserDTO;
import com.java04.entity.Favorite;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Date;
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

    @Override
    public boolean isLiked(String userId, String videoId) {
        String jpql = "SELECT COUNT(f) FROM Favorite f WHERE f.user.id = :userId AND f.video.id = :videoId";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("userId", userId)
                .setParameter("videoId", videoId)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public void like(String userId, String videoId) {
        em.getTransaction().begin();

        Favorite favorite = new Favorite();
        favorite.setUser(em.find(com.java04.entity.User.class, userId));
        favorite.setVideo(em.find(com.java04.entity.Video.class, videoId));
        favorite.setLikeDate(new Date());

        em.persist(favorite);

        em.getTransaction().commit();
    }

    @Override
    public void delete(String userId, String videoId) {
        em.getTransaction().begin();
        try {
            Favorite favorite = em.createQuery(
                            "SELECT f FROM Favorite f WHERE f.user.id = :uid AND f.video.id = :vid", Favorite.class)
                    .setParameter("uid", userId)
                    .setParameter("vid", videoId)
                    .getSingleResult();

            if (favorite != null) {
                em.remove(favorite);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<FavoriteReportDTO> getFavoriteReport() {
        String jpql = "SELECT new com.java04.dto.FavoriteReportDTO(" +
                "f.video.title, COUNT(f), MAX(f.likeDate), MIN(f.likeDate)) " +
                "FROM Favorite f GROUP BY f.video.title";
        return em.createQuery(jpql, FavoriteReportDTO.class).getResultList();
    }

    @Override
    public List<FavoriteUserDTO> getFavoriteUsersByVideoTitle(String title) {
        String jpql = "SELECT new com.java04.dto.FavoriteUserDTO(" +
                "f.video.title, f.user.id, f.user.fullname, f.user.email, f.likeDate) " +
                "FROM Favorite f WHERE LOWER(f.video.title) LIKE :title ORDER BY f.likeDate DESC";
        return em.createQuery(jpql, FavoriteUserDTO.class)
                .setParameter("title", "%" + title.toLowerCase() + "%")
                .getResultList();
    }
}
