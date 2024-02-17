package com.zy.rpc.consumer.controller;

import com.zy.rpc.api.pojo.User;
import com.zy.rpc.api.service.UserService;
import com.zy.rpc.client.annotation.RpcReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xy
 * @version 1.0
 */
@RestController
public class UserController {

    @RpcReference
    private UserService userService;


    @RequestMapping("/user/getUser")
    public User getUser() {

        return userService.queryUser();
    }

    @RequestMapping("/user/getAllUser")
    public List<User> getAllUser() {

        return userService.getAllUsers();
    }

}
