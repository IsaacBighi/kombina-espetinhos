package com.bighibig.kombina.modules.order.exceptions;

import com.bighibig.kombina.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidOrderException extends BaseException {
    public InvalidOrderException(String message){
        super(message, HttpStatus.BAD_REQUEST);
    }
}
