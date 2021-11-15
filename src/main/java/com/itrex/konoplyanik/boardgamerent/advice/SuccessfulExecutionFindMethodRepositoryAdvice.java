package com.itrex.konoplyanik.boardgamerent.advice;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SuccessfulExecutionFindMethodRepositoryAdvice {
	private static final String METHOD_RESULT = "\n\nREPOSITORY METHOD: %s%s successfully executed\nresult:%s\n";

    @AfterReturning(pointcut = "execution(* com.itrex.konoplyanik.boardgamerent.repository.*.find*(..)) ", returning = "entity"
    )
    public void methodLoggingFindResult(JoinPoint joinPoint, Object entity) {
        String methodName = joinPoint.getSignature().getName();
        String arg = Arrays.toString(joinPoint.getArgs());
        System.out.println((String.format(METHOD_RESULT, methodName, arg, entity)));
    }
}
