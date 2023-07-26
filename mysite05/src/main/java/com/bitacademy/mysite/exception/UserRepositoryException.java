package com.bitacademy.mysite.exception;

public class UserRepositoryException extends RuntimeException {
	public UserRepositoryException(String message) {
		super(message);
	}
}