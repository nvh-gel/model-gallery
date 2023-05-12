package com.eden.gallery.aop;

import com.eden.common.utils.ResponseModel;
import com.eden.common.viewmodel.BaseVM;
import com.eden.gallery.utils.PageConverter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * Aspect for converting response entity and response model.
 */
@Aspect
@Component
public class ResponseHandlingAspect {

    private final PageConverter pageConverter = new PageConverter();

    /**
     * Convert response entity.
     *
     * @param joinPoint proceeding object
     * @return response entity to web interface
     * @throws Throwable if proceeding method produce exception
     */
    @SuppressWarnings("unchecked")
    @Around(value = "@annotation(ResponseHandling)")
    public ResponseEntity<ResponseModel> responseHandling(ProceedingJoinPoint joinPoint) throws Throwable {
        ResponseEntity<Object> result = (ResponseEntity<Object>) joinPoint.proceed();
        if (result.hasBody()) {
            if (isUuid(result.getBody())) {
                return ResponseEntity.accepted().body(ResponseModel.accepted(result.getBody()));
            }
            if (result.getBody() instanceof Page) {
                return ResponseEntity.ok(pageConverter.toResponse((Iterable<? extends BaseVM>) result.getBody()));
            }
            return ResponseEntity.ok(ResponseModel.ok(result.getBody()));
        } else {
            return ResponseEntity.status(404).body(ResponseModel.notFound());
        }
    }

    /**
     * Check if an object is UUID or UUID string
     *
     * @param obj object to check
     * @return true if argument is uuid, else false
     */
    private boolean isUuid(Object obj) {
        if (null == obj || !StringUtils.hasText(obj.toString())) {
            return false;
        }
        if (obj instanceof UUID) {
            return true;
        }
        try {
            //noinspection ResultOfMethodCallIgnored
            UUID.fromString(obj.toString());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
