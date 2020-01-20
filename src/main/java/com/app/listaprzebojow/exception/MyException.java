package com.app.listaprzebojow.exception;

import java.time.LocalDateTime;

public class MyException extends RuntimeException {
    private String msg;
    private LocalDateTime localDateTime;

    public MyException(String msg){
        this.msg = msg;
        localDateTime = LocalDateTime.now();
    }
}
