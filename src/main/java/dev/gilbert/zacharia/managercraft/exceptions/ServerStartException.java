package dev.gilbert.zacharia.managercraft.exceptions;

/**
 * Custom exception class for starting a server
 */
public class ServerStartException extends RuntimeException {

    /**
     * Constructs a new ServerStartException with the specified error message.
     *
     * @param message the error message
     */
    public ServerStartException(String message) {
        super(message);
    }

    /**
     * Constructs a new ServerStartException with the specified error message and cause.
     *
     * @param message the error message
     * @param cause   the cause of this exception
     */
    public ServerStartException(String message, Throwable cause) {
        super(message, cause);
    }
}

