package com.xiejieyi.mysqldemo.dao;


import com.xiejieyi.mysqldemo.daobean.User;
import org.springframework.data.repository.CrudRepository;

/**
 * 类描述：
 *
 * @author
 */
public interface UserRepository extends CrudRepository<User, Long>
{
    /**
     *  根据用户名找用户信息
     */
    User findByUsername(String var1);
}
