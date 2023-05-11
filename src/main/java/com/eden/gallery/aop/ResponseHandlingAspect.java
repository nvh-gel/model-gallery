package com.eden.gallery.aop;

import com.eden.common.utils.ResponseModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResponseHandlingAspect {

    @SuppressWarnings("unchecked")
    @Around(value = "@annotation(ResponseHandling)")
    public ResponseEntity<ResponseModel> responseHandling(ProceedingJoinPoint joinPoint) throws Throwable {
        ResponseEntity<Object> result = (ResponseEntity<Object>) joinPoint.proceed();
        if (result.hasBody()) {
            return ResponseEntity.ok(ResponseModel.ok(result.getBody()));
        } else {
            return ResponseEntity.status(404).body(ResponseModel.notFound());
        }
    }
}
