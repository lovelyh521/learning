package com.xiejieyi.demo.bean;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserBean implements Serializable
{

    @Size(max=11, message = "phone is exceed of max length")
    private String phone;

    @Size(max=12, message = "username is exceed of max length")
    @NotBlank(message="username is empty")
    private String username;

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    @Override
    public String toString()
    {
        return "UserBean{" +
                "phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
