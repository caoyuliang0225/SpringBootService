package com.yl.crm.service;

import com.yl.crm.data.model.User;

/**
 * Created by Cao Yuliang on 2019-08-23.
 */
public interface UserService {

    User save(User user);

    User getById(Long id);

    User getByCodeAndPassword(String code, String password);

    void deleteById(Long code);
}
