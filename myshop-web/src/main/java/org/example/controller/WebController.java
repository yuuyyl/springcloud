package org.example.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.example.client.UserController;
import org.example.domain.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 购票controller
 */
@RequestMapping(value = "/web",method = RequestMethod.GET)
@RestController
@Api(description = "购票web核心api")
public class WebController {

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient client;

    @Resource
    private UserController userController;

    @Resource
    private RestTemplate restTemplate;

    /**
     * 新增微服务方法的日志功能
     */
    private static final Log log = LogFactory.getLog(WebController.class);
    /**
     * 购票方法
     * @return
     */
    @ApiOperation(value = "微服务间远程互相调用方法：根据id查询用户")
    @RequestMapping(value = "/order",method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "fallback")
    public String order(){
        log.info("开始调用WebApplication的order方法");
        //模拟当前用户
        Integer id = 0;
        Integer userId = 1;
        //User user = restTemplate.getForObject("http://myshop-user/user/" + id, User.class);
        User user = userController.findById(userId);
        System.out.println(user.getUsername()+"====正在购票");
        return user.getUsername()+"====购票成功";
    }

    /**
     * 熔断回滚方法
     * @return
     */
    public String fallback(){
        return "服务发生熔断，请稍后再试。";
    }
}