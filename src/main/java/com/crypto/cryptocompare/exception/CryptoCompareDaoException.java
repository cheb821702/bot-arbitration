package com.crypto.cryptocompare.exception;

public class CryptoCompareDaoException extends RuntimeException {
    public CryptoCompareDaoException() {
        super();
    }

    public CryptoCompareDaoException(String message) {
        super(message);
    }

    public CryptoCompareDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptoCompareDaoException(Throwable cause) {
        super(cause);
    }

    protected CryptoCompareDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
