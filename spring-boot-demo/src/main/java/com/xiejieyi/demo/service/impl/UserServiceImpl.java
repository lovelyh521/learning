package com.xiejieyi.demo.service.impl;

import com.xiejieyi.demo.util.DemoConstant;
import com.chinamobile.cmss.vrms.common.exception.VrmsException;
import com.xiejieyi.demo.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 类描述：
 *
 * @author
 */
@Service
public class UserServiceImpl implements UserService
{
    @Override
    public void checkUsernameExisted(String username) throws VrmsException
    {
        if(StringUtils.isEmpty(username))
        {
            throw new VrmsException(DemoConstant.RET_CODE_INPUT_ILLEGAL,"username is empty");
        }
        if(username.equals("exception")){
            throw new VrmsException(DemoConstant.RET_CODE_INPUT_ILLEGAL,"exception for test");
        }
        return;
    }
}
