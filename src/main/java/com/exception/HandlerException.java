package com.exception;

import com.enump.ErrorEnum;
import com.tools.CorrectDate;
import com.tools.GetResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {
    @Autowired
    MessageSource messageSource;


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        Map<String, String> params = new HashMap<>();
        params.put("code", ErrorEnum.General.toString());
        params.put("timestamp", CorrectDate.dateTimeZone(new Date()));
        params.put("message", ex.getLocalizedMessage());
        return new ResponseEntity(params, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AppException.class)
    public final ResponseEntity<Object> handleAppException(AppException ex, WebRequest request) {
        Map<String, String> params = new HashMap<>();
        params.put("code", ErrorEnum.ValidBusiness.toString());
        params.put("timestamp", new Date().toString());
        params.put("message", ex.getMessage());
        return new ResponseEntity(params, ex.getHttpStatus());
    }

    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> params = new HashMap<>();
        params.put("code", ErrorEnum.ValidArg.toString());
        params.put("timestamp", CorrectDate.dateTimeZone(new Date()));
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            params.put("message", error.getDefaultMessage());
            params.put("field", error.getField());
        });
        return new ResponseEntity(params, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<Object> conflict(DataIntegrityViolationException e) {
        Map<String, String> params = new HashMap<>();
        params.put("code", ErrorEnum.General.toString());
        params.put("timestamp", new Date().toString());

        try {
            String error = e.getRootCause().getMessage();
            if (error.contains("unique constraint")) {
                params.put("message", "unique");
            } else if (error.contains("cannot be null")) {
                params.put("message", "null value");
            } else if (error.contains("Duplicate entry")) {
                params.put("message", "Duplicate entry");
            }
            return new ResponseEntity(params, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
        }

        e.printStackTrace();
        return new ResponseEntity(params, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ToManyRequestException.class)
    public final ResponseEntity<Object> handleToManyRequestException(ToManyRequestException ex) {
        Map<String, String> params = new HashMap<>();
        params.put("code", ErrorEnum.ValidateSystem.toString());
        params.put("timestamp", CorrectDate.dateTimeZone(new Date()));
        try {
            String temp = GetResourceBundle.getMessage_fa.getString("error.ToManyRequest."+ex.getMessage());
            params.put("message", new String(temp.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        } catch (NoClassDefFoundError | Exception e) {
            params.put("message", "To Many Request");
        }

        return new ResponseEntity(params, HttpStatus.TOO_MANY_REQUESTS);
    }

    public final ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, String> params = new HashMap<>();
        params.put("code", ErrorEnum.ValidateSystem.toString());
        params.put("timestamp", CorrectDate.dateTimeZone(new Date()));
        try {
            String temp = GetResourceBundle.getMessage_fa.getString("error.AccessDenied");
            params.put("message", new String(temp.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        } catch (NoClassDefFoundError | Exception e) {
            params.put("message", "Access Denied");
        }
        return new ResponseEntity(params, HttpStatus.FORBIDDEN);
    }
}
