package com.xiejieyi.demo.service;

import com.chinamobile.cmss.vrms.common.exception.VrmsException;

public interface UserService
{
    /**
     * 方法描述： 判断用户是否存在
     *
     *  如果存在，抛出异常
     * @author xiejieyi
     * @date 8/22/2018
     */
    void checkUsernameExisted(String username) throws VrmsException;
}
