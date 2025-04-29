package com.ead.authuser.services.impl;

import com.ead.authuser.models.UserModel; // Importa UserModel
import com.ead.authuser.repositories.UserRepository; // Importa o Repositório
import com.ead.authuser.services.UserService; // Importa a interface UserService


import org.springframework.stereotype.Service; // Anotação Spring
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service // <-- Garante que o Spring gerencie esta classe como um serviço
public class UserServiceImpl implements UserService { // Implementa a interface

    // Dependência do Repositório (gerenciada pelo Spring Data JPA)
    private final UserRepository userRepository;

    // Se você usa Utils ou outras dependências no serviço, injete-as aqui também
    // private final Utils utils;

    // Construtor para Injeção de Dependência do Repositório (e outras dependências)
    // Spring injetará a instância de UserRepository aqui
    public UserServiceImpl(UserRepository userRepository /*, Utils utils, ... */) {
        this.userRepository = userRepository;
        // this.utils = utils; // Atribuir outras dependências
    }

    // Implementação dos métodos definidos na interface UserService

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll(); // Delega para o método do Repositório JPA
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return userRepository.findById(userId); // Delega para o método do Repositório JPA
    }

    @Override
    public void delete(UserModel userModel) {
        userRepository.delete(userModel); // Delega para o método do Repositório JPA
    }

    @Override
    public void save(UserModel usermodel) {
        // Dentro deste método é onde ocorreu o NullPointerException anteriormente
        // AGORA, o 'userRepository' deve estar injetado e não será nulo
        // Qualquer lógica de negócio (como gerar ID com Utils, validar, etc.) iria aqui ANTES de salvar
        // Exemplo: if (usermodel.getUserId() == null) { String generatedId = utils.generateUserId(); ... }

        userRepository.save(usermodel); // <-- A chamada que usa o repositório injetado
    }

    @Override
    public boolean existsByUserName(String userName) {
        // Delega para o método de Query Method do Repositório (que Spring Data implementa)
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        // Delega para o método de Query Method do Repositório (que Spring Data implementa)
        // Este é o método que precisou ser renomeado de 'existByEmail' para 'existsByEmail'
        return userRepository.existsByEmail(email);
    }

    // ... Implementações de quaisquer outros métodos da interface UserService, se houver ...
}