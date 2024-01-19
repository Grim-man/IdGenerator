package org.example.idgenerator.controller;

import org.example.idgenerator.exception.SeqException;
import org.example.idgenerator.service.DbSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dbSequence")
public class DbSequenceController {
    @Autowired
    private DbSequence dbSequence;

    @PostMapping()
    public ResponseEntity<String> getSequence() {
        try {
            String nextNo = dbSequence.nextNo();
            return new ResponseEntity<>(nextNo, HttpStatus.OK);
        }catch(SeqException e){
            return new ResponseEntity<>("获取序列号失败：" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
