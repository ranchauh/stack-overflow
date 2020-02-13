package com.overflow.stack.service.service;

import com.overflow.stack.service.model.User;
import com.overflow.stack.service.repository.MongoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MongoUserRepository mongoUserRepository;

    @Override
    public Optional<User> findById(String userById) {
        return mongoUserRepository.findById(userById);
    }

    @Override
    public User save(User user) {
        return mongoUserRepository.insert(user);
    }

    @Override
    public User update(User user) {
        return mongoUserRepository.save(user);
    }

    @Override
    public void delete(String userId) {
        mongoUserRepository.deleteById(userId);
    }
}
