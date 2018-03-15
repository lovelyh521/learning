package com.xiejieyi.rbac.repository;

import com.xiejieyi.rbac.bean.User;
import org.springframework.data.repository.CrudRepository;

/**
 * 类描述：
 *
 * @author
 */
public interface UserRepository extends CrudRepository<User,Long>
{
     User findByUsername(String username);
}
