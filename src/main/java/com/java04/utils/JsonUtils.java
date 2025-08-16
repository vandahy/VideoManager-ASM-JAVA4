package com.java04.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Utility class để xử lý JSON operations
 * Hỗ trợ đọc/ghi JSON từ file và chuyển đổi Java objects
 * 
 * Chức năng chính:
 * - Đọc/ghi JSON từ file
 * - Chuyển đổi Java objects ↔ JSON
 * - Xử lý file có cấu trúc root object chứa array
 * - Tạo file JSON mới
 * 
 * Sử dụng thư viện Gson để xử lý JSON
 */
public class JsonUtils {
    
    // Gson instance với cấu hình pretty printing và date format
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()  // Format JSON đẹp
            .setDateFormat("yyyy-MM-dd HH:mm:ss")  // Format date
            .create();
    
    /**
     * Đọc JSON từ file và chuyển thành Java object
     * 
     * @param filePath đường dẫn file JSON
     * @param classType class type của object cần chuyển đổi
     * @return Java object được chuyển đổi từ JSON, null nếu file không tồn tại
     * @throws IOException nếu có lỗi khi đọc file
     */
    public static <T> T readFromFile(String filePath, Class<T> classType) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, classType);
        } catch (Exception e) {
            throw new IOException("Error reading JSON file: " + filePath, e);
        }
    }
    
    /**
     * Đọc JSON từ file và chuyển thành List của Java objects
     * 
     * @param filePath đường dẫn file JSON
     * @param listType TypeToken của List
     * @return List của Java objects, null nếu file không tồn tại
     * @throws IOException nếu có lỗi khi đọc file
     */
    public static <T> List<T> readListFromFile(String filePath, TypeToken<List<T>> listType) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, listType.getType());
        } catch (Exception e) {
            throw new IOException("Error reading JSON file: " + filePath, e);
        }
    }
    
    /**
     * Đọc JSON từ file có cấu trúc root object chứa array
     * Ví dụ: {"comments": [{"id": 1, "content": "test"}]}
     * 
     * @param filePath đường dẫn file JSON
     * @param rootKey key của array trong root object (ví dụ: "comments")
     * @param listType TypeToken của List
     * @return List của Java objects, null nếu file không tồn tại hoặc không có key
     * @throws IOException nếu có lỗi khi đọc file
     */
    public static <T> List<T> readListFromRootFile(String filePath, String rootKey, TypeToken<List<T>> listType) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            // Đọc root object
            JsonObject root = gson.fromJson(reader, JsonObject.class);
            if (root != null && root.has(rootKey)) {
                // Chuyển đổi array trong root object thành List
                return gson.fromJson(root.get(rootKey), listType.getType());
            }
            return null;
        } catch (Exception e) {
            throw new IOException("Error reading JSON file: " + filePath, e);
        }
    }
    
    /**
     * Ghi Java object thành JSON file
     * 
     * @param filePath đường dẫn file để ghi
     * @param object object cần ghi
     * @throws IOException nếu có lỗi khi ghi file
     */
    public static <T> void writeToFile(String filePath, T object) throws IOException {
        File file = new File(filePath);
        // Tạo thư mục nếu chưa tồn tại
        file.getParentFile().mkdirs();
        
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            gson.toJson(object, writer);
        } catch (Exception e) {
            throw new IOException("Error writing JSON file: " + filePath, e);
        }
    }
    
    /**
     * Ghi List thành JSON file có cấu trúc root object
     * Ví dụ: {"comments": [{"id": 1, "content": "test"}]}
     * 
     * @param filePath đường dẫn file để ghi
     * @param rootKey key của array trong root object (ví dụ: "comments")
     * @param list List cần ghi
     * @throws IOException nếu có lỗi khi ghi file
     */
    public static <T> void writeListToRootFile(String filePath, String rootKey, List<T> list) throws IOException {
        File file = new File(filePath);
        // Tạo thư mục nếu chưa tồn tại
        file.getParentFile().mkdirs();
        
        // Tạo root object
        JsonObject root = new JsonObject();
        // Thêm array vào root object
        root.add(rootKey, gson.toJsonTree(list));
        
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            gson.toJson(root, writer);
        } catch (Exception e) {
            throw new IOException("Error writing JSON file: " + filePath, e);
        }
    }
    
    /**
     * Chuyển Java object thành JSON string
     * 
     * @param object object cần chuyển đổi
     * @return JSON string
     */
    public static <T> String toJson(T object) {
        return gson.toJson(object);
    }
    
    /**
     * Chuyển JSON string thành Java object
     * 
     * @param json JSON string
     * @param classType class type của object
     * @return Java object
     */
    public static <T> T fromJson(String json, Class<T> classType) {
        return gson.fromJson(json, classType);
    }
    
    /**
     * Chuyển JSON string thành List của Java objects
     * 
     * @param json JSON string
     * @param listType TypeToken của List
     * @return List của Java objects
     */
    public static <T> List<T> fromJsonList(String json, TypeToken<List<T>> listType) {
        return gson.fromJson(json, listType.getType());
    }
    
    /**
     * Tạo file JSON mới với cấu trúc root object chứa array rỗng
     * Ví dụ: {"comments": []}
     * 
     * @param filePath đường dẫn file
     * @param rootKey key của array
     * @throws IOException nếu có lỗi khi tạo file
     */
    public static void createEmptyRootFile(String filePath, String rootKey) throws IOException {
        // Tạo root object với array rỗng
        JsonObject root = new JsonObject();
        root.add(rootKey, gson.toJsonTree(new Object[0]));
        writeToFile(filePath, root);
    }
    
    /**
     * Kiểm tra file có tồn tại không
     * 
     * @param filePath đường dẫn file
     * @return true nếu file tồn tại
     */
    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }
}
