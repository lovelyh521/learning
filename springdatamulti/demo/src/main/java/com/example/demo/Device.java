package com.example.demo;

import javax.persistence.*;

/**
 * 类描述：
 * JOINED 方式：
 *      父类存放子类和父类的公共字段，
        父类和子类中存在一一对应的关系,子类的id关联父类的id
 TABLE_PER_CLASS:
        这种策略下的每个Entity类都对应一个独立的数据库表
 single_table：
        一个类继承结构一个表的策略，最终只生成一个表，这是继承映射的默认策略。
        如果实体类Teacher继承实体类Person，实体类Student也继承自实体Person，
        那么只会映射成一个表，这个表中包括了实体类Person、Teacher、Student中所有的字段
 * @author
 */
@Entity
@Table(name = "device")
@Inheritance(strategy = InheritanceType.JOINED)
public class Device extends Base
{
    private String deviceName;

    public Device()
    {
    }

    public String getDeviceName()
    {
        return deviceName;
    }

    public void setDeviceName(String deviceName)
    {
        this.deviceName = deviceName;
    }
}
