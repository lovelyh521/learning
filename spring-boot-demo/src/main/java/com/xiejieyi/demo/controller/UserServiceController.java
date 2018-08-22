package com.xiejieyi.demo.controller;


import com.xiejieyi.demo.util.DemoConstant;
import com.xiejieyi.demo.bean.ResultBean;
import com.chinamobile.cmss.vrms.common.exception.VrmsException;
import com.xiejieyi.demo.bean.UserBean;
import com.xiejieyi.demo.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类描述：
 *
 * @author
 */

@RestController
public class UserServiceController<T> {
    @Autowired
    UserService userService;

    @ApiOperation(value="查看用户信息")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", dataType = "String", name = "username", value =
            "用户名称", required = true) })
    @GetMapping("/user/{username}")
    public ResultBean sayHello(@PathVariable String username) {
        if(StringUtils.isEmpty(username)){
            return new ResultBean(DemoConstant.RET_CODE_INPUT_ILLEGAL,"username is empty");
        }

        UserBean result = new UserBean();
        result.setPhone("188");
        result.setUsername("hello world");
        return new ResultBean(DemoConstant.SUCCESS,"success",result);
    }

    /**
     * 类描述： 设置helloBean的值, 新增用户
     *
     * 使用@Validated 对Bean进行基础校验
     * 涉及到业务校验的，需要逐一对参数进行校验，对输入保持不信任的态度
     *
     * @author xiejieyi
     */
    @ApiOperation(value="新增用户信息")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "body", dataType = "UserBean", name = "user", value =
            "用户数据", required = true) })
    @PostMapping("/user")
    public ResultBean setHello(@Validated @RequestBody UserBean userBean) throws VrmsException
    {
        // 获取用户信息

        //业务判断，用户名是否已存在
        String username = userBean.getUsername();
        userService.checkUsernameExisted(username);

        // TODO 插入数据库
        return new ResultBean(DemoConstant.SUCCESS,"success",null);
    }
}
