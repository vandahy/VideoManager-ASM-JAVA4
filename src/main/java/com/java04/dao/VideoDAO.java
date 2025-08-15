package com.java04.dao;

import com.java04.entity.Video;

import java.util.List;

public interface VideoDAO extends GenericDAO<Video, String> {
    List<Video> findByTitleContaining(String keyword);

    List<Video> findTop10FavoriteVideos();

    List<Video> findVideosNotFavorited();

    List<Video> findAllByViewsDesc();

    int count();

    List<Video> findByPage(int page, int size);

    int countLikeByViews(String videoId);
}
