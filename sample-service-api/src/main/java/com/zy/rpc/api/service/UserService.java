package com.zy.rpc.api.service;


import com.zy.rpc.api.pojo.User;

import java.util.List;

/**
 * @author zy
 * @version 1.0
 */
public interface UserService {

    User queryUser();

    List<User> getAllUsers();

}
