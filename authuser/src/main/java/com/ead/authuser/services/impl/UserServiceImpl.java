package com.ead.authuser.services.impl;

import com.ead.authuser.models.UserModel; // Importa UserModel
import com.ead.authuser.repositories.UserRepository; // Importa o Repositório
import com.ead.authuser.services.UserService; // Importa a interface UserService


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service; // Anotação Spring
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service // <-- Garante que o Spring gerencie esta classe como um serviço
public class UserServiceImpl implements UserService { // Implementa a interface


    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository /*, Utils utils, ... */) {
        this.userRepository = userRepository;

    }

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

    @Override
    public Page<UserModel> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}