package com.alperenaktug.handler;

import com.alperenaktug.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiError<?>> handleBaseException(BaseException ex , WebRequest request) throws UnknownHostException {
      return  ResponseEntity.badRequest().body(createApiError(ex.getMessage() , request));

    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError<Map<String , List<String>>>> HandleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) throws UnknownHostException {

        Map<String, List<String>> map = new HashMap<>();
        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            String fieldName =  ((FieldError)objectError).getField();

            if (map.containsKey(fieldName)) {
                map.put(fieldName, addValue(map.get(fieldName), objectError.getDefaultMessage()));

            }else {
                map.put(fieldName , addValue(new ArrayList<String>() , ((FieldError) objectError).getDefaultMessage()));

            }
        }

        return ResponseEntity.badRequest().body(createApiError(map , request));
    }

    private List<String> addValue(List<String> list, String newValue){
        list.add(newValue);
        return list;
    }


    private String getHostName() throws UnknownHostException {
      return   Inet4Address.getLocalHost().getHostName();
    }

    public <E> ApiError createApiError(E message , WebRequest request) throws UnknownHostException {
        ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        Exception<E> exception = new Exception<>();
        exception.setPath(request.getDescription(false).substring(4));
        exception.setCreateTime(new Date());
        exception.setMessage(message);
        exception.setHostName(getHostName());

        apiError.setException(exception);




        return apiError;
    }

}
