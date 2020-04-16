package com.yl.crm.service.impl;

import com.yl.crm.data.model.User;
import com.yl.crm.data.repository.UserRepository;
import com.yl.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Cao Yuliang on 2019-08-23.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User getById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public User getByCodeAndPassword(String code, String password) {
        return this.userRepository.findByCodeAndPassword(code, password);
    }

    @Override
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }
}
