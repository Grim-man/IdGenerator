package org.example.idgenerator.service;

import org.example.idgenerator.exception.SeqException;
import org.springframework.stereotype.Service;

@Service
public interface SnowflakeSequence {
    String nextNo() throws SeqException;
}
