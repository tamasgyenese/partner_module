package com.partner.exception;

public class IllegalResponsePath  extends Exception{

    public IllegalResponsePath() {
    }

    public IllegalResponsePath(String message) {
        super(message);
    }

    public IllegalResponsePath(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalResponsePath(Throwable cause) {
        super(cause);
    }

    public IllegalResponsePath(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
