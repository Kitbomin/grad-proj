package com.example.mainserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class testController {
    @GetMapping("/check-ai")
    public String checkAi() {
        WebClient client = WebClient.create("http://localhost:8000");
        return client.get().retrieve().bodyToMono(String.class).block();
    }
}
