package com.stussy.stussyclone20220930gahyeon.aop;

import com.stussy.stussyclone20220930gahyeon.exception.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class ValidationAop {

    @Pointcut("execution(* com.stussy.stussyclone20220930gahyeon..*Api.*(..))")
    private void executionPointCut(){}

    @Around("executionPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        BeanPropertyBindingResult bindingResult = null;

        for(Object arg : args) {
            if (arg.getClass() == BeanPropertyBindingResult.class) {
                bindingResult = (BeanPropertyBindingResult) arg;
                break;
            }
        }
        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<String, String>();

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrors){

                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()); //에러를 Map으로 전달해서 그룹으로 묶어줌
            }

            //return ResponseEntity.badRequest().body(errorMap); //에러 있을 때 badRequest //errormap 그룹 보여줌
            throw new CustomValidationException("Validation Error", errorMap);
        }

        Object result = null;
        result = joinPoint.proceed();

        return result;
    }
}
