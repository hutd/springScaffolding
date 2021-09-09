package com.quick.exception;

import lombok.Getter;

@Getter
public class CodeException extends RuntimeException {

    private int code;

    public CodeException(int code,String message) {
        super(message);
        this.code = code;
    }

    public CodeException(String code,String message) {
        super(message);
        this.code = Integer.parseInt(code);
    }
}
