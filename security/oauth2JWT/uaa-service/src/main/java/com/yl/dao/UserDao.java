package com.yl.dao;


import com.yl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Cao Yuliang on 2020/4/15.
 */

public interface UserDao extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
