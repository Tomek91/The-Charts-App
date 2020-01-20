package com.app.listaprzebojow.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyException extends RuntimeException {
    private String msg;
    private LocalDateTime localDateTime;

    public MyException(String msg){
        this.msg = msg;
        localDateTime = LocalDateTime.now();
    }
}
