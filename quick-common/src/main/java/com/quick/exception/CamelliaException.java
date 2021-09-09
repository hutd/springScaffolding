package com.quick.exception;


import com.quick.enums.ExceptionEnum;
import lombok.Getter;

@Getter
public class CamelliaException extends RuntimeException {

    private ExceptionEnum exceptionEnum;

    public CamelliaException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }


}
