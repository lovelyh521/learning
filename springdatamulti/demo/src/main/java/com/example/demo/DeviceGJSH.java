package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 类描述：通过三个MappedSuperclass，和一个InheritanceType.JOINED
 *  实现 BASE->Device->DEVICEConnect->DeviceGJ->DevieceGJSH的层级结构
 *  Device是一个独立的表
 *  DeviceGJSH是一个独立的表，通过主键进行关联，可以有其他省份的扩展表
 *
 * @author
 */
@Entity
@Table(name = "device_gj_sh")
public class DeviceGJSH extends DeviceGJ
{
    private String gjSHName;

    public String getGjSHName()
    {
        return gjSHName;
    }

    public void setGjSHName(String gjSHName)
    {
        this.gjSHName = gjSHName;
    }

    public DeviceGJSH()
    {
    }
}
