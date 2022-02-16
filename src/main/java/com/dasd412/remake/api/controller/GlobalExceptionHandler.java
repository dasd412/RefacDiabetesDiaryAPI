/*
 * @(#)GlobalExceptionHandler.java        1.0.1 2022/1/22
 *
 * Copyright (c) 2022 YoungJun Yang.
 * ComputerScience, ProgrammingLanguage, Java, Pocheon-si, KOREA
 * All rights reserved.
 */

package com.dasd412.remake.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.NoResultException;

/**
 * 전역 예외 핸들러 클래스.
 *
 * @author 양영준
 * @version 1.0.1 2022년 1월 22일
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @param throwable BAD_REQUEST 상태값과 관련된 예외
     * @return 400.mustache 이동
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class,
            TypeMismatchException.class, MissingServletRequestParameterException.class,
            JSONException.class, MethodArgumentNotValidException.class})
    public ModelAndView handle400(Throwable throwable) {
        logger.error("Bad Request: {}", throwable.getMessage());
        return new ModelAndView("400");
    }

    /**
     * 허가되지 않은 접근일때 캐치
     *
     * @return 403.mustache 이동
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handle403() {
        logger.info("not authorized");
        return new ModelAndView("403");
    }

    /**
     * 참고로 NoResultException 은 쿼리 결과가 하나도 없을 때 발생하는 (jpa 가 제공하는) 예외이다.
     *
     * @return 404.mustache 이동
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoHandlerFoundException.class, NoResultException.class})
    public ModelAndView handle404() {
        logger.error("Not found in server");
        return new ModelAndView("404");
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ModelAndView handle405(HttpRequestMethodNotSupportedException throwable) {
        logger.warn("Method not allowed : {}", throwable.getMethod());
        return new ModelAndView("405");
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeException.class)
    public ModelAndView handle415(HttpMediaTypeException throwable) {
        logger.warn("Unsupported Media Type : {}", throwable.getMessage());
        return new ModelAndView("415");
    }

    /**
     * @param throwable 다른 예외 캐치에서 잡히지 못한 예외들
     * @return 500.mustache 이동
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView handle500(Throwable throwable) {
        logger.error("Internal server error : {}", throwable.getMessage());
        return new ModelAndView("500");
    }
}
