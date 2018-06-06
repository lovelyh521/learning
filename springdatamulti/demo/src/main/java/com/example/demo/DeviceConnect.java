package com.example.demo;

import javax.persistence.*;

/**
 * 类描述：
 *
 * @author
 */
@MappedSuperclass
public class DeviceConnect extends Device
{
    private String connect;

    public String getConnect()
    {
        return connect;
    }

    public void setConnect(String connect)
    {
        this.connect = connect;
    }

    public DeviceConnect()
    {
    }
}
