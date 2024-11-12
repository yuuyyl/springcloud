package org.example.client.Impl;

import org.example.client.UserController;
import org.example.domain.pojo.User;
import org.springframework.stereotype.Component;

@Component
public class UserControllerImpl implements UserController {
    @Override
    public User findById(Integer id) {
        System.out.println("执行了熔断操作");
        return null;
    }
}
