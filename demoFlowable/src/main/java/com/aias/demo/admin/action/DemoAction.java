package com.aias.demo.admin.action;

import com.aias.demo.admin.service.IUserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoAction {

    @Autowired
    private IUserService userService;

    @RequestMapping("/")
    public String hello() {
        return "hello world";
    }

    @RequestMapping("/list")
    public String userList() {
        return JSON.toJSONString(userService.listAllUser());
    }
}
