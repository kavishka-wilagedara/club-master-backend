package com.uokclubmanagement.controller;

import com.uokclubmanagement.request.LoginRequest;
import com.uokclubmanagement.dto.UserDTO;
import com.uokclubmanagement.service.impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/login")
@CrossOrigin(origins = "http://localhost:5174")
public class LoginController {

    @Autowired
    private LoginServiceImpl loginService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            UserDTO userDTO = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(userDTO);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
