package com.xiejieyi.mysqldemo.rest;


import com.xiejieyi.mysqldemo.daobean.User;
import com.xiejieyi.mysqldemo.util.Security;
import com.xiejieyi.mysqldemo.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserRest
{
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/user",method = RequestMethod.POST )
    public boolean addUser(@RequestBody Map<String, String> payload)
    {
        String username = payload.get("username");
        String password = payload.get("password");
        String salt = Security.generateSalt();
        String encryptPWD = Security.encrypt(password, salt);

        if(encryptPWD==null || encryptPWD.isEmpty() || encryptPWD == password){
            return false;
        }
        User user = new User(username, salt, encryptPWD);
        try{
            userRepository.save(user);
        }catch(Exception ex){
            System.out.println("save user failed " + ex);
            return false;
        }
        return true;
    }
}
