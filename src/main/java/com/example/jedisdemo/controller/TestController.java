package com.example.jedisdemo.controller;

import com.example.jedisdemo.model.DemoModel;
import com.example.jedisdemo.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TestController {
    private final RedisService redisService;

    @GetMapping("/test-model")
    public ResponseEntity<?> testModelGet(@RequestParam String key, @RequestParam String id) {

        var response = redisService.getValue(key, id, DemoModel.class);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/test-model")
    public ResponseEntity<?> testModelPost(@RequestParam String key, @RequestParam String id, @RequestBody DemoModel value) {

        redisService.save(key, id, value);
        return ResponseEntity.ok("saved");

    }
}
