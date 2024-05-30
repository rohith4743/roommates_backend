package com.rohithkankipati.roommates.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import com.rohithkankipati.roommates.util.MessageUtil;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RoomMateException.class)
    public ResponseEntity<ErrorResponse> handleRoomMateException(RoomMateException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
            ex.getStatus(), 
            MessageUtil.getMessage(ex.getErrorCode())
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
}
