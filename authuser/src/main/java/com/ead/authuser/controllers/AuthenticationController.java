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

    // Remova estes campos, pois eles não devem ser injetados
    // private final UserDTO userDTO;
    // private final UserModel userModel;


    // Construtor correto: apenas com as dependências gerenciadas pelo Spring
    // Spring injetará a instância de UserService aqui
    public AuthenticationController(UserService userService /* Não injete DTO, Model, Enum aqui */) {
        this.userService = userService;
        // Remova atribuições para userDTO e userModel
        // this.userDTO = userDTO;
        // this.userModel = userModel;
    }

    //Methods
    // O método registerUser recebe o UserDTO do corpo da requisição (@RequestBody)
    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated(UserView.RegistrationPost.class)
                                                   UserDTO userDTO) { // <-- UserDTO vem daqui, NÃO do construtor

        if (userService.existsByUserName(userDTO.getUserName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is Already Taken!");
        }
        if (userService.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is Already Taken!");
        }

        // UserModel é criado DENTRO do método
        var usermodel = new UserModel();
        BeanUtils.copyProperties(userDTO, usermodel);

        // UserStatus e UserType são ENUMs usados diretamente
        usermodel.setUserStatus(UserStatus.ACTIVE);
        usermodel.setUserType(UserType.STUDENT);

        usermodel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        usermodel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        // Chame o serviço para salvar
        userService.save(usermodel);

        // IMPORTANTE: Considerar retornar um DTO de resposta seguro, sem senha
        return ResponseEntity.status(HttpStatus.CREATED).body(usermodel);
    }


}
