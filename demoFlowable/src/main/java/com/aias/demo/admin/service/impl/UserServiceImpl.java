package com.aias.demo.admin.service.impl;

import com.aias.demo.admin.bean.User;
import com.aias.demo.admin.dao.IUserDao;
import com.aias.demo.admin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public List<User> listAllUser() {
        return userDao.listAllUser();
    }
}
