package com.itrex.konoplyanik.boardgamerent.advice;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class ExceptionExecutionMethodRepositoryAdvice {
	private static final String MESSAGE = "\n\nRepository method: {}  with arguments {} throws {}: {}";

    @AfterThrowing(pointcut = "execution(* com.itrex.konoplyanik.boardgamerent.repository.*.*(..))", throwing = "ex")
    public void methodLoggingException(JoinPoint joinPoint, Exception ex) {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        String arguments = Arrays.toString(joinPoint.getArgs());
        String exception = ex.getClass().getSimpleName();
        String exceptionMessage = ex.getMessage();

        log.error(MESSAGE, methodName, arguments, exception, exceptionMessage, ex);
    }

}
