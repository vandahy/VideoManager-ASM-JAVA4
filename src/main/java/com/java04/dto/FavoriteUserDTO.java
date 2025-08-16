package com.java04.dto;

import java.util.Date;

public class FavoriteUserDTO {
    private String videoTitle;
    private String username;
    private String fullname;
    private String email;
    private Date favoriteDate;

    public FavoriteUserDTO(String videoTitle, String username, String fullname, String email, Date favoriteDate) {
        this.videoTitle = videoTitle;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.favoriteDate = favoriteDate;
    }

    public String getVideoTitle() { return videoTitle; }
    public String getUsername() { return username; }
    public String getFullname() { return fullname; }
    public String getEmail() { return email; }
    public Date getFavoriteDate() { return favoriteDate; }
}








