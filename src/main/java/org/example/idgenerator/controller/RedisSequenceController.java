package org.example.idgenerator.controller;

import org.example.idgenerator.exception.SeqException;
import org.example.idgenerator.service.RedisSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redisSequence")
public class RedisSequenceController {
    @Autowired
    private RedisSequence redisSequence;

    @GetMapping()
    public ResponseEntity<String> getSequenceRedis() {
        try {
            String nextNo = redisSequence.nextNo();
            return new ResponseEntity<>(nextNo, HttpStatus.OK);
        }catch(SeqException e){
            return new ResponseEntity<>("获取序列号失败：" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
