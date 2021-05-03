package com.partner.exception;

public class NoResponseDataException extends Exception {

    private long errorCode;

    public NoResponseDataException(String message) {
        super(message);
    }

    public NoResponseDataException(long errorCode) {
        this.errorCode = errorCode;
    }

    public NoResponseDataException(String message, long errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public NoResponseDataException(String message, Throwable cause, long errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public NoResponseDataException(Throwable cause, long errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public NoResponseDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }
}
