package io.kimmking.rpcfx.Exception;

public class BaseException extends Exception{
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(String message) {
        super(message);
    }
}
