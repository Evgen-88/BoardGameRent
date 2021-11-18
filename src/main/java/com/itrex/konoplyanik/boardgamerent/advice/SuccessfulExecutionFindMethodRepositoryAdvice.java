package com.itrex.konoplyanik.boardgamerent.advice;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class SuccessfulExecutionFindMethodRepositoryAdvice {
	private static final String METHOD_RESULT = "\n\nREPOSITORY METHOD: {}{} successfully executed\nresult:{}\n";

    @AfterReturning(pointcut = "execution(* com.itrex.konoplyanik.boardgamerent.repository.*.find*(..)) ", returning = "entity"
    )
    public void methodLoggingFindResult(JoinPoint joinPoint, Object entity) {
        String methodName = joinPoint.getSignature().getName();
        String arg = Arrays.toString(joinPoint.getArgs());
        log.info(METHOD_RESULT, methodName, arg, entity);
    }
}
