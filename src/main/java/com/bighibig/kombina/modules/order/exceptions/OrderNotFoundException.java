package com.bighibig.kombina.modules.order.exceptions;

import com.bighibig.kombina.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends BaseException {
    public OrderNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
