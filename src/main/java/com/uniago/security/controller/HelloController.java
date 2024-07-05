package com.uniago.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ex_guoanjian
 * @version 1.0
 * Create by 2024/07/02 14:00
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    @PreAuthorize("hasAuthority('test')")
    // @PreAuthorize("hasAnyAuthority('test', 'test2')")
    // @PreAuthorize("hasRole('test')")
    // @PreAuthorize("hasAnyRole('test', 'test2')")
    // @PreAuthorize("@custom.hasAuthority('test')")
    public String hello() {
        return "hello";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

}
