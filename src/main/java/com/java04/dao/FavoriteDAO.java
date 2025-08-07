package com.java04.dao;

import com.java04.dto.VideoSearchInfo;
import com.java04.entity.Favorite;

import java.util.List;

public interface FavoriteDAO extends GenericDAO<Favorite, Long> {
    List<VideoSearchInfo> searchVideosByKeyword(String keyword);
    List<Favorite> findByUserId(String userId);
}
