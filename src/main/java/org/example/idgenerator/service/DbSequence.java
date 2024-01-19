package org.example.idgenerator.service;

import org.example.idgenerator.exception.SeqException;

public interface DbSequence {
    /**
     * 下一个生成序号
     *
     * @return
     * @throws SeqException
     */
    String nextNo() throws SeqException;
}
