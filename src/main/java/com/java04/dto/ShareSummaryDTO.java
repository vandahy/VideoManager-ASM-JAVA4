package com.java04.dto;

import java.util.Date;

public class ShareSummaryDTO {
    private String title;
    private Long totalShares;
    private Date firstShareDate;
    private Date lastShareDate;

    public ShareSummaryDTO(String title, Long totalShares, Date firstShareDate, Date lastShareDate) {
        this.title = title;
        this.totalShares = totalShares;
        this.firstShareDate = firstShareDate;
        this.lastShareDate = lastShareDate;
    }

    // Getters
    public String getTitle() { return title; }
    public Long getTotalShares() { return totalShares; }
    public Date getFirstShareDate() { return firstShareDate; }
    public Date getLastShareDate() { return lastShareDate; }
}
