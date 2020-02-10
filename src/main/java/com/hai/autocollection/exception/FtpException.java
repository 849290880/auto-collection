package com.hai.autocollection.exception;

/**
 * @author created by hai on 2020/1/7
 */
public class FtpException extends RuntimeException{

    public FtpException() {
        super();
    }

    public FtpException(String message) {
        super(message);
    }

    public FtpException(String message, Throwable cause) {
        super(message, cause);
    }

    public FtpException(Throwable cause) {
        super(cause);
    }

    protected FtpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public FtpException(String message, String... append) {
        super(String.format(message, append));
    }
}
