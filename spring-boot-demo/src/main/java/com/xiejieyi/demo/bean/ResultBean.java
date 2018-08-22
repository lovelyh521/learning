package com.xiejieyi.demo.bean;

/**
 * 类描述： 统一定义返回值
 *
 * @author
 */
public class ResultBean<T>
{

    private final int code;
    private final String msg;
    private T data = null;

    public ResultBean()
    {
        this.code = 0;
        this.msg = "";
        this.data = null;
    }

    public ResultBean(final int retcode, final String msg)
    {
        this.code = retcode;
        this.msg = msg;
    }

    public ResultBean(final int retcode,final  String msg,final  T data)
    {
        this.code = retcode;
        this.msg = msg;
        this.data = data;
    }

    public int getCode()
    {
        return code;
    }

    public T getData()
    {
        return data;
    }

    public String getMsg()
    {
        return msg;
    }
}
