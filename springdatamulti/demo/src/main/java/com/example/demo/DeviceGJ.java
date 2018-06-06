package com.example.demo;

import javax.persistence.*;

/**
 * 类描述：
 *
 * @author
 */
@MappedSuperclass
public class DeviceGJ extends DeviceConnect
{
    private String gjName;

    public String getGjName()
    {
        return gjName;
    }

    public void setGjName(String gjName)
    {
        this.gjName = gjName;
    }

    public DeviceGJ()
    {
    }
}
