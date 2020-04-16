package com.yl.crm.data.repository;

import com.yl.crm.data.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Cao Yuliang
 * @date 2020-03-25
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role getById(Long id);
}
