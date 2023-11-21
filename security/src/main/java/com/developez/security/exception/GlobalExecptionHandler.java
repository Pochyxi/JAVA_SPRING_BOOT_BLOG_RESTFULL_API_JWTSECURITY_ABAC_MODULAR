package com.developez.security.exception;

import com.developez.security.DTO.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExecptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ ResourceNotFoundException.class, BlogAPIException.class, Exception.class })
    public ResponseEntity<ErrorDetails> handleCustomExceptions( Exception ex, WebRequest webRequest ) {

        if( ex instanceof ResourceNotFoundException resourceNotFoundEx ) {
            ErrorDetails errorDetails = new ErrorDetails( new Date(), resourceNotFoundEx.getMessage(),
                    webRequest.getDescription( false ) );
            return new ResponseEntity<>( errorDetails, HttpStatus.NOT_FOUND );
        } else if( ex instanceof BlogAPIException blogApiEx ) {
            ErrorDetails errorDetails = new ErrorDetails( new Date(), blogApiEx.getMessage(),
                    webRequest.getDescription( false ) );
            return new ResponseEntity<>( errorDetails, blogApiEx.getStatus() );
        } else {
            ErrorDetails errorDetails = new ErrorDetails( new Date(), ex.getMessage(),
                    webRequest.getDescription( false ) );
            return new ResponseEntity<>( errorDetails, HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    // Questo metodo viene chiamato quando @Valid fallisce
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatusCode status,
                                                                   WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach( ( error ) -> {
            String fieldName = (( FieldError ) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put( fieldName, errorMessage );
        } );

        return new ResponseEntity<>( errors, HttpStatus.BAD_REQUEST );
    }

}
