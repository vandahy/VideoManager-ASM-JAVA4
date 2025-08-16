package com.java04.dao;

import com.java04.entity.Comment;
import java.util.List;


public interface CommentDAO {
    
    /**
     * Tìm tất cả comments của một video theo videoId
     * 
     * @param videoId ID của video cần tìm comments
     * @return Danh sách comments của video đó, trả về empty list nếu không có
     */
    List<Comment> findByVideoId(String videoId);
    
    /**
     * Tạo comment mới và lưu vào storage
     * 
     * @param comment Comment object cần tạo (commentId sẽ được tự động generate)
     * @return Comment đã được tạo với ID mới, null nếu lỗi
     */
    Comment create(Comment comment);
    
    /**
     * Lấy tất cả comments từ storage
     * 
     * @return Danh sách tất cả comments, trả về empty list nếu không có
     */
    List<Comment> findAll();
    
    /**
     * Tạo ID mới cho comment (tự động tăng)
     * 
     * @return ID mới (max ID hiện tại + 1), trả về 1 nếu chưa có comment nào
     */
    int generateNewId();
}
