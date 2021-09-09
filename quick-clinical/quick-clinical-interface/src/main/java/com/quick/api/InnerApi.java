package com.quick.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/demo")
public interface InnerApi {
    @GetMapping("/test")
    String test();
}
