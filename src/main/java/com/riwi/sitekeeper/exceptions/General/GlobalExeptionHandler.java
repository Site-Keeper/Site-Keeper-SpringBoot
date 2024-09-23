package com.riwi.sitekeeper.exceptions.General;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExeptionHandler {

   @ExceptionHandler(NotFoundException.class)
   public ResponseEntity<ErrorObject> handleReportNotFoundException(NotFoundException e, WebRequest request){
       ErrorObject errorObject = new ErrorObject();

       errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
       errorObject.setMessage(e.getMessage());
       errorObject.setTimestamp(new Date());

       return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
   }
   @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<ErrorObject> handleInvalidSpaceException(InvalidFileException e, WebRequest request){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(e.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<ErrorObject> handleUnauthorizedActionException(UnauthorizedActionException e, WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.FORBIDDEN.value());
        errorObject.setMessage(e.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.FORBIDDEN);
    }
}
