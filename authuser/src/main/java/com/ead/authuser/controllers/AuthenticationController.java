package com.ead.authuser.controllers;

import com.ead.authuser.dto.UserDTO; // Ainda precisa importar para usar no método registerUser
import com.ead.authuser.dto.UserView;
import com.ead.authuser.enums.UserStatus; // Ainda precisa importar para usar no método
import com.ead.authuser.enums.UserType; // Ainda precisa importar para usar no método
import com.ead.authuser.models.UserModel; // Ainda precisa importar para usar no método
import com.ead.authuser.services.UserService; // Esta é a dependência correta para injetar

import org.springframework.beans.BeanUtils;
// Remova import org.springframework.beans.factory.annotation.Autowired; // Não precisa mais no construtor se ele for único
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    // A dependência correta para injetar no controller é o UserService
    private final UserService userService;


    public AuthenticationController(UserService userService /* Não injete DTO, Model, Enum aqui */) {
        this.userService = userService;
        // Remova atribuições para userDTO e userModel
        // this.userDTO = userDTO;
        // this.userModel = userModel;
    }


    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated(UserView.RegistrationPost.class)
                                                   UserDTO userDTO) {

        if (userService.existsByUserName(userDTO.getUserName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is Already Taken!");
        }
        if (userService.existsByEmail(userDTO.getEmail())) {
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
