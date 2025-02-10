package br.com.TriagemCheck.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.NoPermissionException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorRecordResponse> handleNotFoundException(NotFoundException ex) {
        var errorRecordResponse = new ErrorRecordResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
        logger.error("NotFoundException message: {} ", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorRecordResponse);
    }

    @ExceptionHandler(NoValidException.class)
    public ResponseEntity<ErrorRecordResponse> handleNoValidException(NoValidException ex) {
        var errorRecordResponse = new ErrorRecordResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                null
        );
        logger.error("NoValidException message: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorRecordResponse);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRecordResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                }
        );
        var errorRecordResponse = new ErrorRecordResponse(HttpStatus.BAD_REQUEST.value(), "Error: Validation failed", errors);
        logger.error("MethodArgumentNotValidException message: {} ", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRecordResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorRecordResponse> handleInvalidFormatException(HttpMessageNotReadableException ex) {
        Map<String, String> errors = new HashMap<>();
        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException ifx = (InvalidFormatException) ex.getCause();
            if (ifx.getTargetType()!=null && ifx.getTargetType().isEnum()) {
                String fieldName = ifx.getPath().get(ifx.getPath().size()-1).getFieldName();
                String errorMessage = ex.getMessage();
                errors.put(fieldName, errorMessage);
            }
        }
        var errorRecordResponse = new ErrorRecordResponse(HttpStatus.BAD_REQUEST.value(), "Error: Invalid enum value", errors);
        logger.error("HttpMessageNotReadableException message: {} ", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRecordResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorRecordResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        for (ConstraintViolation<?> violation : violations) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        }

        var errorRecordResponse = new ErrorRecordResponse(HttpStatus.BAD_REQUEST.value(), "Validation error", errors);
        logger.error("ConstraintViolationException message: {} ", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRecordResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorRecordResponse> handleAccessDeniedException(AccessDeniedException ex) {
        var errorRecordResponse = new ErrorRecordResponse(
                HttpStatus.FORBIDDEN.value(),
                "Erro de acesso negado: " + ex.getMessage(),
                null
        );
        logger.error("AccessDeniedException message: {} ", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorRecordResponse);
    }

    @ExceptionHandler(EmptySortException.class)
    public ResponseEntity<ErrorRecordResponse> handleEmptySortException(EmptySortException ex) {
        var errorRecordResponse = new ErrorRecordResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null
        );
        logger.error("EmptySortException message: {} ", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRecordResponse);
    }



}
