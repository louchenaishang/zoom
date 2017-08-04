package com.github.louchen.zoom.api.role.exception;

/**
 * 初始化角色异常
 */
public class InitRoleErrorException extends RuntimeException {

    /**
     * Creates a new InitRoleErrorException.
     */
    public InitRoleErrorException() {
        super();
    }

    /**
     * Constructs a new InitRoleErrorException.
     *
     * @param message the reason for the exception
     */
    public InitRoleErrorException(String message) {
        super(message);
    }

    /**
     * Constructs a new InitRoleErrorException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public InitRoleErrorException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new InitRoleErrorException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public InitRoleErrorException(String message, Throwable cause) {
        super(message, cause);
    }

}
