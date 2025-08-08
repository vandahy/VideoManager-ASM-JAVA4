package com.java04.dao;

import com.java04.entity.User;

public interface UserDAO extends GenericDAO<User, String> {
    User findByIdOrEmail(String idOrEmail);
    String generateNewUserId();
}
