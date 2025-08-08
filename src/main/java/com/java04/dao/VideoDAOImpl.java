package com.java04.dao;

import com.java04.entity.Video;
import org.hibernate.boot.model.relational.Database;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class VideoDAOImpl implements VideoDAO {
    private EntityManager em;

    public VideoDAOImpl(){
        em = Persistence.createEntityManagerFactory("myPersistenceUnit").createEntityManager();
    }

    @Override
    public List<Video> findAll() {
        return em.createQuery("select v from Video v", Video.class).getResultList();
    }

    @Override
    public Video findById(String id) {
        return em.find(Video.class, id);
    }

    @Override
    public void create(Video video) {
        em.getTransaction().begin();
        em.persist(video);
        em.getTransaction().commit();
    }

    @Override
    public void update(Video video) {
        em.getTransaction().begin();
        em.merge(video);
        em.getTransaction().commit();
    }

    @Override
    public void deleteById(String id) {
        em.getTransaction().begin();
        Video video = em.find(Video.class, id);
        if (video != null) {
            em.remove(video);
        }
        em.getTransaction().commit();
    }

    @Override
    public List<Video> findByTitleContaining(String keyword) {
        return em.createQuery(
                        "SELECT v FROM Video v WHERE LOWER(v.title) LIKE :keyword", Video.class)
                .setParameter("keyword", "%" + keyword.toLowerCase() + "%")
                .getResultList();
    }

    @Override
    public List<Video> findTop10FavoriteVideos() {
        return em.createQuery(
                        "SELECT f.video FROM Favorite f GROUP BY f.video ORDER BY COUNT(f) DESC", Video.class)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public List<Video> findVideosNotFavorited() {
        return em.createQuery(
                        "SELECT v FROM Video v WHERE v.favorite IS EMPTY", Video.class)
                .getResultList();
    }

    @Override
    public List<Video> findAllByViewsDesc() {
        return em.createQuery("SELECT v FROM Video v ORDER BY v.views DESC", Video.class)
                .getResultList();  // ❌ không có setMaxResults
    }
    @Override
    public int count() {
        Long count = em.createQuery("SELECT COUNT(v) FROM Video v", Long.class)
                .getSingleResult();
        return count.intValue();
    }

    @Override
    public List<Video> findByPage(int page, int size) {
        return em.createQuery("SELECT v FROM Video v ORDER BY v.id DESC", Video.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }
}


