package com.yl.crm.service.impl;

import com.yl.crm.data.model.Role;
import com.yl.crm.data.repository.RoleRepository;
import com.yl.crm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Cao Yuliang on 2020-03-25.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Override
    public Role getById(Long id) {
        return this.repository.getById(id);
    }
}
