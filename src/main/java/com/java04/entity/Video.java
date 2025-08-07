package com.java04.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Video")

public class Video {
    @Id
    @Column(name = "Id")
    private String id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Poster")
    private String poster;

    @Column(name = "Views")
    private Integer views;

    @Column(name = "Description")
    private String description;

    @Column(name = "Active")
    private Boolean active;

    @Column(name = "Links")
    private String links;

    @OneToMany(mappedBy = "video")
    private List<Favorite> favorite;

    @OneToMany(mappedBy = "video")
    private List<Share> shares;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public List<Favorite> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<Favorite> favorite) {
        this.favorite = favorite;
    }

    public List<Share> getShares() {
        return shares;
    }

    public void setShares(List<Share> shares) {
        this.shares = shares;
    }


}
