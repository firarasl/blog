package com.example.portfolio.exception;

public class FiteException extends RuntimeException {
        public FiteException(String exMessage, Exception exception) {
            super(exMessage, exception);
        }

        public FiteException(String exMessage) {
            super(exMessage);
        }
}
