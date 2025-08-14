package com.java04.dto;

import java.util.Date;

public class FavoriteReportDTO {
    private String title;
    private Long favoriteCount;
    private Date latestDate;
    private Date oldestDate;

    public FavoriteReportDTO(String title, Long favoriteCount, Date latestDate, Date oldestDate) {
        this.title = title;
        this.favoriteCount = favoriteCount;
        this.latestDate = latestDate;
        this.oldestDate = oldestDate;
    }

    public String getTitle() {
        return title;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public Date getLatestDate() {
        return latestDate;
    }

    public Date getOldestDate() {
        return oldestDate;
    }
}


