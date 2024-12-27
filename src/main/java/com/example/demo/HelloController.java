package com.example.demo;

//import jakarta.servlet.annotation.WebServlet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
//@WebServlet("/hello")
public class HelloController {
    @GetMapping("/hello")
    @ResponseBody
    public String showHelloPage(){
        return "hello";
    }
}

