package com.will.homestay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RounterController {
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }
}
