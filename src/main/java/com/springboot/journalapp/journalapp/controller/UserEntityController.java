package com.springboot.journalapp.journalapp.controller;

import com.springboot.journalapp.journalapp.entity.UserEntity;
import com.springboot.journalapp.journalapp.service.UserEntityService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserEntityController {

    @Autowired
    private UserEntityService userEntityService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        List<UserEntity> allUsers =  userEntityService.getAllUser();
        if(allUsers!=null && !allUsers.isEmpty()){
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserEntity myUser){
        try{
           userEntityService.saveUser(myUser);
           return new ResponseEntity<>(myUser, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getUserById(@PathVariable ObjectId myId){
        try{
            userEntityService.findById(myId);
            return new ResponseEntity<>(myId, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/update/username/{username}")
    public ResponseEntity<?> updateUser(@RequestBody UserEntity newUser,@PathVariable String username){
        UserEntity userInDb = userEntityService.getByUserName(username);
        if(userInDb!=null){
            userInDb.setUsername(newUser.getUsername());
            userInDb.setPassword(newUser.getPassword());
            userEntityService.saveUser(userInDb);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
