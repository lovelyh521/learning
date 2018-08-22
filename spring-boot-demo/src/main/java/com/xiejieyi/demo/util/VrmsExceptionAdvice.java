package com.xiejieyi.demo.util;

import com.chinamobile.cmss.vrms.common.Constant;
import com.chinamobile.cmss.vrms.common.bean.ResultBean;
import com.chinamobile.cmss.vrms.common.exception.VrmsException;
import com.chinamobile.cmss.vrms.common.log.VrmsLog;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 类描述： 统一拦截校验异常，统一拦截VrmsException和未捕获异常Exception
 *
 * @author
 */
@ControllerAdvice
@ResponseBody
public class VrmsExceptionAdvice
{

    /**
     * 拦截@Validated校验抛出的异常
     *
     * @param ex vrms自定义异常
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultBean errorHandler(final MethodArgumentNotValidException ex)
    {
        VrmsLog.error("validate basic input error =" + ex.getBindingResult().getFieldError().getDefaultMessage());
        return new ResultBean(Constant.RET_CODE_INPUT_ILLEGAL, ex.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 拦截捕捉自定义异常 VrmsException.class
     *
     * @param ex vrms自定义异常
     * @return
     */
    @ExceptionHandler(value = VrmsException.class)
    public ResultBean vrmsExceptionHandler(final VrmsException ex)
    {
        final Integer retCode = ex.getCode();
        VrmsLog.error("validate error, reason=" + ex.getMessage());
        return new ResultBean(retCode, ex.getMessage());
    }

    /**
     * 拦截捕捉自定义异常 VrmsException.class
     *
     * @param ex 未捕获的异常
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResultBean errorHandler(final Exception ex)
    {
        VrmsLog.error("system error ", ex);
        return new ResultBean(Constant.RETCODE_SYSTEM_ERROR, ex.getMessage());
    }
}


