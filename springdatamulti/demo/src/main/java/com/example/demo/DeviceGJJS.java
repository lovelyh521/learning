package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 类描述：江苏光交扩展表
 *
 * @author
 */
@Entity
@Table(name = "device_gj_js")
public class DeviceGJJS extends DeviceGJ
{
    private String gjJSName;

    public String getGjJSName()
    {
        return gjJSName;
    }

    public void setGjJSName(String gjJSName)
    {
        this.gjJSName = gjJSName;
    }

    public DeviceGJJS()
    {
    }
}
