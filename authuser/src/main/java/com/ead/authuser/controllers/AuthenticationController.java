package com.ead.authuser.controllers;


import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    //Depencies

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService){
        this.userService = userService;
    }

    //Methods
    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody UserDTO userDTO){
        if(userService.existsByUserName(userDTO.getUserName())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is Already Taken!");
        }
        if(userService.existsByEmail(userDTO.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is Already Taken!");
        }

        var usermodel = new UserModel();
        BeanUtils.copyProperties(userDTO, usermodel);
        usermodel.setUserStatus(UserStatus.ACTIVE);
        usermodel.setUserType(UserType.STUDENT);
        usermodel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        usermodel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.save(usermodel);

        return ResponseEntity.status(HttpStatus.CREATED).body(usermodel);



    }


}
