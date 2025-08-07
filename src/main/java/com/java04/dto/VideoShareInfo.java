package com.java04.dto;


import java.util.Date;

public class VideoShareInfo {
    private String title;
    private Long totalShares;
    private Date firstShareDate;
    private Date lastShareDate;

    public VideoShareInfo(String title, Long totalShares, Date firstShareDate, Date lastShareDate) {
        this.title = title;
        this.totalShares = totalShares;
        this.firstShareDate = firstShareDate;
        this.lastShareDate = lastShareDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTotalShares() {
        return totalShares;
    }

    public void setTotalShares(Long totalShares) {
        this.totalShares = totalShares;
    }

    public Date getFirstShareDate() {
        return firstShareDate;
    }

    public void setFirstShareDate(Date firstShareDate) {
        this.firstShareDate = firstShareDate;
    }

    public Date getLastShareDate() {
        return lastShareDate;
    }

    public void setLastShareDate(Date lastShareDate) {
        this.lastShareDate = lastShareDate;
    }
}
