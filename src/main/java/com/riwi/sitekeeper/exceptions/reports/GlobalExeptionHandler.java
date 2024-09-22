package com.riwi.sitekeeper.exceptions.reports;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExeptionHandler {

   @ExceptionHandler(ReportNotFoundException.class)
   public ResponseEntity<ErrorObject> handleReportNotFoundException(ReportNotFoundException e, WebRequest request){
       ErrorObject errorObject = new ErrorObject();

       errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
       errorObject.setMessage(e.getMessage());
       errorObject.setTimestamp(new Date());

       return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
   }
}
