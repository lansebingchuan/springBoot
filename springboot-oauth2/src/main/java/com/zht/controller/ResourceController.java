package com.zht.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/resource")
public class ResourceController {

    @GetMapping("/getResource")
    public ResponseEntity<String> getResource() {
        return new ResponseEntity<>("Resource", HttpStatus.OK);
    }
}
