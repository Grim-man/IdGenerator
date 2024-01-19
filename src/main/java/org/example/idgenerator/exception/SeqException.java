package org.example.idgenerator.exception;
/**
 * 自定义全局异常
 */
public class SeqException extends Exception{
    public SeqException(String message){
        super(message);
    }

    public SeqException(String message, Throwable cause){
        super(message, cause);
    }
}
