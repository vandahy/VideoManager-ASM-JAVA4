package com.java04.dto;

import java.util.Date;

public class VideoSearchInfo {
    private String title;
    private Long totalLikes;
    private Date lastLikedDate;

    public VideoSearchInfo(String title, Long totalLikes, Date lastLikedDate) {
        this.title = title;
        this.totalLikes = totalLikes;
        this.lastLikedDate = lastLikedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(Long totalLikes) {
        this.totalLikes = totalLikes;
    }

    public Date getLastLikedDate() {
        return lastLikedDate;
    }

    public void setLastLikedDate(Date lastLikedDate) {
        this.lastLikedDate = lastLikedDate;
    }
}
