package com.peijie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("index")
public class indexController {

    @RequestMapping("/login")
    public String login_view(){
        return "login";
    }
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/upload")
    public String upload(){
        return "uploadFile";
    }
}
