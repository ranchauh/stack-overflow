package com.overflow.stack.user.service.controller;

import com.overflow.stack.user.service.entity.User;
import com.overflow.stack.user.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RequestMapping(value = "api/v1/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User saveUser(@RequestBody User user){
        return userService.save(user);
    }

    @PutMapping(value = "/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public User update(@RequestBody User user){
        return userService.update(user);
    }

    @GetMapping(value = "/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable String userId){
        return userService.findById(userId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{userId}",produces = MediaType.TEXT_PLAIN_VALUE)
    public String deleteUser(@PathVariable String userId){
        userService.delete(userId);
        return String.format("User %s deleted successfully.", userId);
    }

}
