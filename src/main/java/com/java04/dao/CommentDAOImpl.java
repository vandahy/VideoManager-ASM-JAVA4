package com.java04.dao;

import com.google.gson.reflect.TypeToken;
import com.java04.entity.Comment;
import com.java04.utils.JsonUtils;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation của CommentDAO sử dụng JSON file để lưu trữ
 * 
 * Chức năng:
 * - Lưu trữ comments trong file JSON (comments.json)
 * - Đọc/ghi comments từ file JSON
 * - Tự động tạo file nếu chưa tồn tại
 * - Tạo ID tự động tăng cho comments
 */
public class CommentDAOImpl implements CommentDAO {
    
    // Tên file JSON lưu trữ comments
    private static final String COMMENTS_FILE = "comments.json";
    
    // ServletContext để lấy đường dẫn file
    private ServletContext servletContext;
    
    /**
     * Constructor với ServletContext
     * @param servletContext ServletContext để lấy đường dẫn file
     */
    public CommentDAOImpl(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    
    /**
     * Tìm tất cả comments của một video theo videoId
     * 
     * @param videoId ID của video cần tìm comments
     * @return Danh sách comments của video đó
     */
    @Override
    public List<Comment> findByVideoId(String videoId) {
        try {
            // Lấy tất cả comments từ file
            List<Comment> allComments = findAll();
            if (allComments == null) {
                return new ArrayList<>();
            }
            
            // Lọc comments theo videoId sử dụng Stream API
            return allComments.stream()
                    .filter(comment -> videoId.equals(comment.getVideoId()))
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Tạo comment mới và lưu vào file JSON
     * 
     * @param comment Comment object cần tạo
     * @return Comment đã được tạo với ID mới
     */
    @Override
    public Comment create(Comment comment) {
        try {
            // Lấy tất cả comments hiện tại
            List<Comment> comments = findAll();
            if (comments == null) {
                comments = new ArrayList<>();
            }
            
            // Tạo ID mới cho comment
            int newId = generateNewId();
            comment.setCommentId(newId);
            
            // Thêm comment vào danh sách
            comments.add(comment);
            
            // Lưu lại tất cả comments vào file
            saveAll(comments);
            
            return comment;
            
        } catch (Exception e) {
            System.err.println("Error in CommentDAOImpl.create: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Lấy tất cả comments từ file JSON
     * 
     * @return Danh sách tất cả comments
     */
    @Override
    public List<Comment> findAll() {
        try {
            // Lấy đường dẫn file JSON
            String filePath = getProjectFilePath();
            
            // Kiểm tra file có tồn tại không
            if (!JsonUtils.fileExists(filePath)) {
                // Tạo file mới nếu chưa tồn tại
                JsonUtils.createEmptyRootFile(filePath, "comments");
                return new ArrayList<>();
            }
            
            // Đọc comments từ file JSON
            TypeToken<List<Comment>> listType = new TypeToken<List<Comment>>(){};
            List<Comment> comments = JsonUtils.readListFromRootFile(filePath, "comments", listType);
            
            return comments != null ? comments : new ArrayList<>();
            
        } catch (Exception e) {
            System.err.println("Error in CommentDAOImpl.findAll: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Tạo ID mới cho comment (tự động tăng)
     * 
     * @return ID mới (max ID hiện tại + 1)
     */
    @Override
    public int generateNewId() {
        try {
            // Lấy tất cả comments hiện tại
            List<Comment> comments = findAll();
            int maxId = 0;
            
            // Tìm ID lớn nhất
            if (comments != null) {
                for (Comment comment : comments) {
                    if (comment.getCommentId() > maxId) {
                        maxId = comment.getCommentId();
                    }
                }
            }
            
            // Trả về ID mới (max + 1)
            return maxId + 1;
            
        } catch (Exception e) {
            e.printStackTrace();
            return 1; // Trả về 1 nếu có lỗi
        }
    }
    
    /**
     * Lấy đường dẫn file trong thư mục project
     * 
     * @return Đường dẫn tuyệt đối đến file comments.json
     */
    private String getProjectFilePath() {
        // Sử dụng đường dẫn tuyệt đối đến thư mục project
        String projectPath = "F:/ASM2_JAVA4/VideoManager-ASM-JAVA4/src/main/webapp/WEB-INF/";
        return projectPath + COMMENTS_FILE;
    }
    
    /**
     * Lưu tất cả comments vào file JSON
     * 
     * @param comments Danh sách comments cần lưu
     * @throws IOException Nếu có lỗi khi ghi file
     */
    private void saveAll(List<Comment> comments) throws IOException {
        String filePath = getProjectFilePath();
        
        try {
            // Ghi comments vào file JSON
            JsonUtils.writeListToRootFile(filePath, "comments", comments);
        } catch (Exception e) {
            System.err.println("Error saving file: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
