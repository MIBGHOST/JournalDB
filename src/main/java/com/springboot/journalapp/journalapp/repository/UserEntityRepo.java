package com.springboot.journalapp.journalapp.repository;

import com.springboot.journalapp.journalapp.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepo extends MongoRepository<UserEntity, ObjectId> {
    UserEntity getByUsername(String username);
}
