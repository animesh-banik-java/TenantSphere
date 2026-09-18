package com.banik.user_service.basepackage.exception;

import com.banik.user_service.basepackage.response.ResponseCode;
import lombok.Getter;

@Getter
public class ApplicationException  extends RuntimeException {

    private static final long serialVersionUID = 7365547609387611425L;

    private final ResponseCode errorCode;

    private String[] fields;

    private Throwable exception;

    public ApplicationException(final ResponseCode code, final String message, final String... fields) {
        super(message);
        this.errorCode = code;
        if (fields.length == 0) {
            this.fields = new String[]{message};
        } else {
            this.fields = fields;

        }
    }

    public ApplicationException( final String message, final String... fields) {
        super(message);
        this.errorCode = ResponseCode.BAD_REQUEST;
        if (fields.length == 0) {
            this.fields = new String[]{message};
        } else {
            this.fields = fields;

        }
    }
    public ApplicationException(Throwable exception) {
        super(exception.getLocalizedMessage());
        this.errorCode = ResponseCode.INTERNAL_ERROR;
        this.exception = exception;
    }
}
