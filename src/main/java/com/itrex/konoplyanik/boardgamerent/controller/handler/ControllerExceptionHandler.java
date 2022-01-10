package com.itrex.konoplyanik.boardgamerent.controller.handler;

import com.itrex.konoplyanik.boardgamerent.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(
            WebRequest request,
            MethodArgumentTypeMismatchException ex
    ) {
        log.error("handleMethodArgumentTypeMismatchException {}\n", request.getDescription(false), ex);
        return handleExceptionInternal(ex,"Method argument type mismatch", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<?> handleAccessDeniedException(
            WebRequest request,
            AccessDeniedException ex
    ) {
        log.error("handleAccessDeniedException {}\n", request.getDescription(false), ex);
        return handleExceptionInternal(ex, "Access denied!", new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleUnauthorizedException(
            WebRequest request,
            BadCredentialsException ex
    ) {
        log.error("handleUnauthorizedException {}\n", request.getDescription(false), ex);
        return handleExceptionInternal(ex, "Incorrect username or password!", new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler({Exception.class, ServiceException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleExceptionServerError(
            WebRequest request,
            Exception ex
    ) {
        log.error("handleServiceExceptionServerError {}\n", request.getDescription(false), ex);
        return handleExceptionInternal(ex, "Parameters is not valid", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
