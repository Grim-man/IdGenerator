package org.example.idgenerator.service.impl;

import org.example.idgenerator.exception.SeqException;
import org.example.idgenerator.service.RedisSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 基于Redis实现方式
 */
@Service
public class RedisSequenceImpl implements RedisSequence {

    private static final String SEQUENCE_KEY = "sequence";
    private static final String SEQUENCE_KEY_PREFIX = "sequence_";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public String nextNo() throws SeqException {
        try {
            // 使用Redis INCR命令递增序列值
            Long nextNo = redisTemplate.opsForValue().increment(SEQUENCE_KEY);
            String nextNoValue = String.format("%07d",nextNo);

            // 将生成的序列号添加到列表中
            String key = SEQUENCE_KEY_PREFIX + nextNoValue;
            redisTemplate.opsForValue().set(key,nextNoValue);

            return nextNoValue;
        } catch (Exception e) {
            throw new SeqException("Redis操作异常！", e);
        }
    }
}
