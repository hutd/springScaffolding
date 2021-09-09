package com.quick.advice;

import com.quick.enums.ExceptionEnum;
import com.quick.exception.CamelliaException;
import com.quick.exception.CodeException;
import com.quick.vo.ExceptionResult;
import com.quick.vo.IResultEntity;
import com.quick.vo.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BasicExceptionHandler {

    @ExceptionHandler(CamelliaException.class)
    public IResultEntity<Void> handleException(CamelliaException e) {
        return ResultEntity.fail(new ExceptionResult(e.getExceptionEnum()));
    }

    @ExceptionHandler(CodeException.class)
    public IResultEntity<Void> handleCodeException(CodeException e) {
        return ResultEntity.fail(e.getCode(), e.getMessage());
    }

    /**
     * BindException 参数错误异常
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    public IResultEntity<Void> handleMethodArgumentNotValidException(Exception e) {
        return ResultEntity.fail(new ExceptionResult(ExceptionEnum.INVALID_PARAM), e);
    }
}
