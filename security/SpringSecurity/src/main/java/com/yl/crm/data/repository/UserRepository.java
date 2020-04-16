package com.yl.crm.data.repository;

import com.yl.crm.data.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Cao Yuliang on 2019-08-22.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByCode(String code);

    User findByCodeAndPassword(String code, String password);
}
