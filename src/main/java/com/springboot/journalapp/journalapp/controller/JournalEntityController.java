package com.springboot.journalapp.journalapp.controller;


import com.springboot.journalapp.journalapp.entity.JournalEntity;
import com.springboot.journalapp.journalapp.entity.UserEntity;
import com.springboot.journalapp.journalapp.service.JournalEntityService;
import com.springboot.journalapp.journalapp.service.UserEntityService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntityController {

    @Autowired
    private JournalEntityService journalEntityService;

    @Autowired
    private UserEntityService userEntityService;


    @GetMapping("{username}")
    public ResponseEntity<List<JournalEntity>> getAllJournalEntriesOfUser(@PathVariable String username){
        UserEntity userFromDb = userEntityService.getByUserName(username);
        List<JournalEntity> all = userFromDb.getJournalEntries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("{username}")
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity myEntry, @PathVariable String username){
        try {
            journalEntityService.saveEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntity> getById(@PathVariable ObjectId myId){
        Optional<JournalEntity> journalEntry = journalEntityService.getById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/id/{myId}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId myId,
                                         @RequestBody JournalEntity newEntry,
                                         @PathVariable String username) {

            JournalEntity old = journalEntityService.getById(myId).orElse(null);
            if(old!=null){
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent((newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent()));
                journalEntityService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{username}/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId, @PathVariable String username){
        journalEntityService.deleteById(myId, username);
        return new ResponseEntity<>(HttpStatus.GONE);
    }
}
