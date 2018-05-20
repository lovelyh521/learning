package com.xiejieyi.poidemo.config;

import com.xiejieyi.poidemo.common.Constant;
import com.xiejieyi.poidemo.bean.ResultBean;
import com.xiejieyi.poidemo.common.SelfException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 类描述：
 *
 * @author
 */
@ControllerAdvice
public class ExceptionAdvice
{
    protected Log logger = LogFactory.getLog(getClass());

    //拦截@Validated校验抛出的异常
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultBean errorHandler(MethodArgumentNotValidException ex) {
        logger.error("validate basic input error ="  + ex.getBindingResult().getFieldError().getDefaultMessage());
        return new ResultBean(Constant.RET_CODE_INPUT_ILLEGAL,null);
    }

    /**
     * 拦截捕捉自定义异常 HGException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = SelfException.class)
    public ResultBean myErrorHandler(SelfException ex) {
        Integer retCode = ex.getRetCode();
        logger.error("validate error, reason=" + ex.getMessage());
        return new ResultBean(retCode,ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResultBean errorHandler(Exception ex) {
        logger.error("validate error " ,ex);
        return new ResultBean(Constant.RETCODE_SYSTEM_ERROR,ex.getMessage());
    }
}
