package com.springboot.journalapp.journalapp.service;

import com.springboot.journalapp.journalapp.entity.JournalEntity;
import com.springboot.journalapp.journalapp.entity.UserEntity;
import com.springboot.journalapp.journalapp.repository.JournalEntityRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntityService {

    @Autowired
    private JournalEntityRepo journalEntityRepo;

    @Autowired
    private UserEntityService userEntityService;

    @Transactional
    public void saveEntry(JournalEntity journalEntity, String username){
        try {
            UserEntity userFromDb = userEntityService.getByUserName(username);
            journalEntity.setDate(LocalDateTime.now());
            JournalEntity userSaved = journalEntityRepo.save(journalEntity);
            userFromDb.getJournalEntries().add(userSaved);
            userEntityService.saveUser(userFromDb);
        }catch (Exception e){
            log.error("Exception", e);
        }

    }
    public void saveEntry(JournalEntity journalEntity){
            journalEntityRepo.save(journalEntity);

    }
    public List<JournalEntity> getAll(){
        return journalEntityRepo.findAll();
    }

    public Optional<JournalEntity> getById(ObjectId myId) {
        return journalEntityRepo.findById(myId);
    }

    public void deleteById(ObjectId myId, String username) {
        UserEntity userFromDb = userEntityService.getByUserName(username);
        userFromDb.getJournalEntries().removeIf(x -> x.getId().equals(myId));
        userEntityService.saveUser(userFromDb);
        journalEntityRepo.deleteById(myId);
    }
}
