package com.xiejieyi.rbac;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 类描述：
 *
 * @author
 */
@RestController
public class HomeController {


    @RequestMapping("/")
    public String index(){
        return "home";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping({"/index", "/home"})
    public String root(){
        return "index";
    }

    // @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping({"/resource"})
    public String resource(){
        System.out.println("post resource");
        return "resource";
    }
}
