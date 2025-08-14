package com.java04.dao;

import com.java04.dto.ShareSummaryDTO;
import com.java04.dto.SharedFriendDTO;
import com.java04.entity.Share;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class ShareDAOImpl implements ShareDAO {
    private EntityManager em;

    public ShareDAOImpl() {
        em = Persistence.createEntityManagerFactory("myPersistenceUnit").createEntityManager();
    }

    @Override
    public List<Share> findAll() {
        return em.createQuery("SELECT s FROM Share s", Share.class).getResultList();
    }

    @Override
    public Share findById(Long id) {
        return em.find(Share.class, id);
    }

    @Override
    public void create(Share share) {
        em.getTransaction().begin();
        em.persist(share);
        em.getTransaction().commit();
    }

    @Override
    public void update(Share share) {
        em.getTransaction().begin();
        em.merge(share);
        em.getTransaction().commit();
    }

    @Override
    public void deleteById(Long id) {
        em.getTransaction().begin();
        Share share = em.find(Share.class, id);
        if (share != null) {
            em.remove(share);
        }
        em.getTransaction().commit();
    }

    @Override
    public List<ShareSummaryDTO> getShareSummary() {
        String jpql = "SELECT new com.java04.dto.ShareSummaryDTO(" +
                "s.video.title, COUNT(s), MIN(s.shareDate), MAX(s.shareDate)) " +
                "FROM Share s GROUP BY s.video.title";
        return em.createQuery(jpql, ShareSummaryDTO.class).getResultList();
    }

    @Override
    public List<SharedFriendDTO> getSharedFriendsByVideoTitle(String title) {
        String jpql = "SELECT new com.java04.dto.SharedFriendDTO(" +
                "s.user.fullname, s.user.email, s.emails, s.shareDate) " +
                "FROM Share s WHERE LOWER(s.video.title) LIKE :title ORDER BY s.shareDate DESC";
        return em.createQuery(jpql, SharedFriendDTO.class)
                .setParameter("title", "%" + title.toLowerCase() + "%")
                .getResultList();
    }
}
