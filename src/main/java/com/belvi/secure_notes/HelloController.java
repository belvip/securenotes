package com.belvi.secure_notes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String SayHello(){
        return "Hello World";
    }

    @GetMapping("/contact")
    public String SayContact(){
        return "Contact";
    }

    @GetMapping("/hi")
    public String SayHi(){
        return "Hi";
    }
}
