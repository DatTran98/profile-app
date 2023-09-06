package com.dattb.info.profile.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/welcome")
@Slf4j
public class WelcomeController {

    @GetMapping("/ping")
    ResponseEntity<String> welcome() {
        log.info("Welcome to the REST API");
        return ResponseEntity.ok("Pong!!! Welcome to the REST API");
    }
}
