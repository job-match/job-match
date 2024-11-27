package com.project.jobmatch.exceptions;

public class MatchRequestDeniedException extends RuntimeException {
  public MatchRequestDeniedException(String message) {
    super(message);
  }
}
