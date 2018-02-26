package com.xiejieyi.mysqldemo.daobean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String username;

    private String salt;

    private String encrypt;

    /**
     * 必须要有，否则查询时会报错：No default constructor for entity
     */
    public User()
    {
    }

    public User(String username, String salt, String encrypt)
    {
        this.username = username;
        this.salt = salt;
        this.encrypt = encrypt;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getSalt()
    {
        return salt;
    }

    public void setSalt(String salt)
    {
        this.salt = salt;
    }

    public String getEncrypt()
    {
        return encrypt;
    }

    public void setEncrypt(String encrypt)
    {
        this.encrypt = encrypt;
    }
}
