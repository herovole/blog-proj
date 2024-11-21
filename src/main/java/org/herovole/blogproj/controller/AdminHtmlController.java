package org.herovole.blogproj.controller;

import org.herovole.blogproj.LocalProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/b")
@EnableConfigurationProperties(LocalProperty.class)
public class AdminHtmlController {

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    //@GetMapping("/error")
    public String error(Model model) {
        return "error";
    }
}
