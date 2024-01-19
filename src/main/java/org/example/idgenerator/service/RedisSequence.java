package org.example.idgenerator.service;

import org.example.idgenerator.exception.SeqException;

public interface RedisSequence {
    String nextNo() throws SeqException;
}
