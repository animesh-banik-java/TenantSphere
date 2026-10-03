package com.banik.user_service.basepackage.exception;

import com.banik.user_service.basepackage.controller.BaseController;
import com.banik.user_service.basepackage.response.Response;
import com.banik.user_service.basepackage.response.ResponseCode;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    protected ResponseEntity<Response> internalServerError(Throwable ex, WebRequest request) {
        log.error(String.valueOf(ex.getLocalizedMessage()));
        log.error(Arrays.toString(ex.getStackTrace()));
        return error(Collections.singletonList(/*"Getting error while processing this request"*/ ex.getLocalizedMessage()));
    }

  /*  @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> experienceException(ApplicationException ex) {
        return error(ex.getErrorCode(), ex.getMessage());
    }*/

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Response> handleApplicationException(ApplicationException ex) {

        log.error("ApplicationException: code={}, message={}", ex.getErrorCode(), ex.getMessage());

//        HttpStatus status = getHttpStatus(ex.getResponseCode());

        return error(ex.getErrorCode(), ex.getMessage());

    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return error(ResponseCode.FILE_SIZE_EXCEED, "File size too large");
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> dataFormatException(BindException ex) {
        List<String> errorMessages = new ArrayList<>();
        BindingResult result = ex.getBindingResult();
        List<ObjectError> errors = result.getAllErrors();

        if (!CollectionUtils.isEmpty(errors)) {
            for (ObjectError error : errors) {
                String[] err = error.getCodes();
                assert err != null;
                for (String errMessage : err) {
                    switch (errMessage) {
                        case "typeMismatch.org.springframework.web.multipart.MultipartFile": {
                            String message = "Please provide images";
                            errorMessages.add(message);
                            return error(errorMessages);
                        }
                        case "typeMismatch": {
                            String field = ((FieldError) error).getField();
                            String message = "Invalid request parameter " + field.substring(field.lastIndexOf(".") + 1)
                                    + " - " + ((FieldError) error).getRejectedValue();
                            errorMessages.add(message);
                            return error(errorMessages);
                        }
                    }
                }
                errorMessages.add(error.getDefaultMessage());
            }
        } else {
            errorMessages.add(ex.getMessage());
        }
        return error(errorMessages);
    }

}
