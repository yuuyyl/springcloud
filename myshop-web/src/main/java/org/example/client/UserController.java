package org.example.client;

import org.example.client.Impl.UserControllerImpl;
import org.example.domain.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "myshop-user",fallback = UserControllerImpl.class)
public interface UserController{
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    User findById(@PathVariable(value = "id") Integer id);
}
