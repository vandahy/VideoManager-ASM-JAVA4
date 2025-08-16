package com.java04.entity;


public class Comment {
    // ID tự động tăng của comment
    private int commentId;
    
    // ID của video được comment
    private String videoId;
    
    // ID của user đã comment
    private String userId;
    
    // Tên đầy đủ của user
    private String fullName;
    
    // Thời gian comment (format: yyyy-MM-dd HH:mm:ss)
    private String commentTime;
    
    // Nội dung comment
    private String content;
    
    // Constructors
    /**
     * Constructor mặc định
     */
    public Comment() {}
    
    /**
     * Constructor với đầy đủ tham số
     * @param commentId ID của comment
     * @param videoId ID của video
     * @param userId ID của user
     * @param fullName Tên đầy đủ của user
     * @param commentTime Thời gian comment
     * @param content Nội dung comment
     */
    public Comment(int commentId, String videoId, String userId, String fullName, String commentTime, String content) {
        this.commentId = commentId;
        this.videoId = videoId;
        this.userId = userId;
        this.fullName = fullName;
        this.commentTime = commentTime;
        this.content = content;
    }
    
    // Getters and Setters
    /**
     * Lấy ID của comment
     * @return ID comment
     */
    public int getCommentId() {
        return commentId;
    }
    
    /**
     * Thiết lập ID cho comment
     * @param commentId ID comment
     */
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
    
    /**
     * Lấy ID của video
     * @return ID video
     */
    public String getVideoId() {
        return videoId;
    }
    
    /**
     * Thiết lập ID video
     * @param videoId ID video
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
    
    /**
     * Lấy ID của user
     * @return ID user
     */
    public String getUserId() {
        return userId;
    }
    
    /**
     * Thiết lập ID user
     * @param userId ID user
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    /**
     * Lấy tên đầy đủ của user
     * @return Tên đầy đủ
     */
    public String getFullName() {
        return fullName;
    }
    
    /**
     * Thiết lập tên đầy đủ
     * @param fullName Tên đầy đủ
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    /**
     * Lấy thời gian comment
     * @return Thời gian comment
     */
    public String getCommentTime() {
        return commentTime;
    }
    
    /**
     * Thiết lập thời gian comment
     * @param commentTime Thời gian comment
     */
    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
    
    /**
     * Lấy nội dung comment
     * @return Nội dung comment
     */
    public String getContent() {
        return content;
    }
    
    /**
     * Thiết lập nội dung comment
     * @param content Nội dung comment
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     * Chuyển đổi comment thành chuỗi để debug
     * @return Chuỗi biểu diễn comment
     */
    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", videoId='" + videoId + '\'' +
                ", userId='" + userId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", commentTime='" + commentTime + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
