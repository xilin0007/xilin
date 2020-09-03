package com.fxl.exception;

import com.fxl.frame.common.ReturnMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description controller全局异常处理类
 * @author fangxilin
 * @date 2020-09-02
 * @Copyright: 深圳市宁远科技股份有限公司版权所有(C)2020
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 处理父类ResponseEntityExceptionHandler中未处理的异常类型
     */
    @ExceptionHandler(Exception.class)
    ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
        String uri = request.getRequestURI();
        log.error("调用" + uri + "接口异常", ex);
        HttpStatus status = getStatus(request);
        String message = ExceptionUtils.getMessage(ex);
        return new ResponseEntity<>(new ReturnMsg(status.value(), message), status);
    }


    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        //return super.handleExceptionInternal(ex, body, headers, status, request);
        String message = ExceptionUtils.getMessage(ex);
        ReturnMsg ret = new ReturnMsg(status.value(), message);
        log.error("调用业务接口异常", ex);
        return new ResponseEntity<>(ret, headers, status);
    }
}
