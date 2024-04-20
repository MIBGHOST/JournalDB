package com.springboot.journalapp.journalapp.service;


import com.springboot.journalapp.journalapp.entity.UserEntity;
import com.springboot.journalapp.journalapp.repository.UserEntityRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserEntityService {

    @Autowired
    private UserEntityRepo userEntityRepo;

    public void saveUser(UserEntity user){
        userEntityRepo.save(user);
    }

    public List<UserEntity> getAllUser(){
        return userEntityRepo.findAll();
    }

    public Optional<UserEntity> findById(ObjectId id){
        return userEntityRepo.findById(id);
    }

    public void deleteById(ObjectId id){
        userEntityRepo.deleteById(id);
    }

    public UserEntity getByUserName(String username){
        return userEntityRepo.getByUsername(username);
    }
}
