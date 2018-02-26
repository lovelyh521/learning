package com.xiejieyi.mysqldemo.rest;

import com.xiejieyi.mysqldemo.Constant;
import com.xiejieyi.mysqldemo.bean.ResultBean;
import com.xiejieyi.mysqldemo.dao.UserRepository;
import com.xiejieyi.mysqldemo.daobean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 类描述：用户登录鉴权操作
 *
 * @author
 */
@RestController
public class LoginRest
{
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path="/authenticate",method = RequestMethod.POST)
    public @ResponseBody
    ResultBean authenticate(@RequestBody Map<String, String> payload){
        // 获取参数
        String name = payload.get("username");
        String pwd = payload.get("password");
        System.out.println("name="+ name+ "\npwd="+pwd);

        User user = userRepository.findByUsername(name);
        System.out.println(user);

        return new ResultBean(Constant.SUCCESS, user);
    }
}
