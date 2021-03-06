package com.overflow.stack.user.service.service;

import com.overflow.stack.user.service.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findById(String userById);

    User save(User user);

    User update(User user);

    void delete(String userId);

}
