package com.project.jobmatch.exceptions;

public class MatchRequestDuplicateException extends RuntimeException {
    public MatchRequestDuplicateException(String message) {
        super(message);
    }
}
