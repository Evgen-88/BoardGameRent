package com.itrex.konoplyanik.boardgamerent.advice;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionExecutionMethodRepositoryAdvice {
	private static final String MESSAGE = "\n\nRepository method: %s  with arguments %s throws %s: %s";

    @AfterThrowing(pointcut = "execution(* com.itrex.konoplyanik.boardgamerent.repository.*.*(..))", throwing = "ex")
    public void methodLoggingException(JoinPoint joinPoint, Exception ex) {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        String arguments = Arrays.toString(joinPoint.getArgs());
        String exception = ex.getClass().getSimpleName();
        String exceptionMessage = ex.getMessage();

        System.out.println(String.format(MESSAGE, methodName, arguments, exception, exceptionMessage));
        ex.printStackTrace();
    }

}
