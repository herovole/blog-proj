package org.herovole.blogproj.controller;

import org.herovole.blogproj.entrypoint.property.LocalProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/hello")
@EnableConfigurationProperties(LocalProperty.class)
public class HelloController {

    //@GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("message", "This is your fucking message!");
        return "hello ";
    }

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    //@GetMapping("/error")
    public String error(Model model) {
        return "error";
    }
}
