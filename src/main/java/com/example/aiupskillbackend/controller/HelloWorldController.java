package com.example.aiupskillbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Tag(name = "Hello World", description = "Simple greeting API")
public class HelloWorldController {

    @GetMapping("/helloworld")
    @Operation(summary = "Get hello world message", description = "Returns a magical greeting message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved greeting message")
    })
    public ResponseEntity<Map<String, String>> getHelloWorld() {
        Map<String, String> response = Map.of("message", "Hello Wizard - Welcome to our Magical World");
        return ResponseEntity.ok(response);
    }
}
