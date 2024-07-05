package com.SWP.BadmintonCourtBooking.Exception;


import com.SWP.BadmintonCourtBooking.Dto.Response.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice //annotation define exception
public class GlobalExceptionHandler {

    /*
    @ExceptionHandler(value = Exception.class) //Catch 1 exception cần 1 para
    ResponseEntity<APIResponse> handlingRuntimeException(RuntimeException e) {
        APIResponse apiRespone = new APIResponse();
        apiRespone.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiRespone);
    }*/

    @ExceptionHandler(value = AppException.class) //Catch 1 exception cần 1 para
    ResponseEntity<APIResponse> handlingAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        APIResponse apiRespone = new APIResponse();
        apiRespone.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiRespone);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<APIResponse> handlingMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String emunKey = e.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try{
            errorCode = ErrorCode.valueOf(emunKey);
        }catch (IllegalArgumentException ex){
            //tạm thời chưa xử lý đến
        }
        APIResponse apiRespone = new APIResponse();
        apiRespone.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiRespone);
    }
}
