package com.springboot.journalapp.journalapp.repository;

import com.springboot.journalapp.journalapp.entity.JournalEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalEntityRepo extends MongoRepository<JournalEntity, ObjectId> {

}
