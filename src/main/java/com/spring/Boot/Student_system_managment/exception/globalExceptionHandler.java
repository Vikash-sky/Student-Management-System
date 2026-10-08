package com.spring.Boot.Student_system_managment.exception;

import com.spring.Boot.Student_system_managment.payLoad.ApiResponse;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class globalExceptionHandler {


    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleStudentNotFoundException(StudentNotFoundException ex, HttpServletRequest request){
       log.info("student not found: {}",ex.getMessage());
        ApiResponse<Object> Response = new ApiResponse<>(
                false,
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null
        );
        return  new ResponseEntity<>(Response,HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateEmailException(DuplicateEmailException ex,HttpServletRequest request) {
        ApiResponse<Object> Response = new ApiResponse<>(
                false,
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(Response,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex,HttpServletRequest request){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors().
                forEach(fieldError -> {
                    errors.put(
                            fieldError.getField(),
                            fieldError.getDefaultMessage()
                    );
                });
        ApiResponse<Object> Response = new ApiResponse<>(
                false,
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "validation failed",
                errors
        );

        return new ResponseEntity<>(Response,HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ApiResponse<Object>> handleOptimiseLockException(OptimisticLockException ex,HttpServletRequest request){
        ApiResponse<Object> response = new ApiResponse<>(
                false,
                LocalDateTime.now(),
                HttpStatus.CONTINUE.value(),
                "Student was allready updated by another user",
                null
        );
        return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }

}
