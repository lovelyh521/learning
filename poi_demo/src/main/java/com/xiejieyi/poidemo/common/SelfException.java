package com.xiejieyi.poidemo.common;

/**
 * 类描述：
 *
 * @author
 */
public class SelfException extends  Exception
{
    private Integer retCode;

    public SelfException(Integer retCode, String message){
        super(message);
        this.retCode = retCode;
    }

    @Override
    public String toString()
    {
        return super.toString() + " retCode = " + String.valueOf(retCode);
    }

    public Integer getRetCode()
    {
        return retCode;
    }
}
