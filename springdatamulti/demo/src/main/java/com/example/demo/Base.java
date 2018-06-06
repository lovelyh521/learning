package com.example.demo;

import javax.persistence.*;

/**
 * 类描述：端口数据模型
 *
 * @author
 */

//MappedSuperclass 只继承，不建表
@MappedSuperclass
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// @Table(name = "base")
public class Base
{
    //资管资源对象ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Base()
    {
    }

}
