package com.bitacademy.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public String handlerException(Model model, Exception ex) {
		//1. 로깅
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		
		logger.error(errors.toString());
		
		//2. 사과
		model.addAttribute("exception", errors.toString());
		return "error/exception";
	}
}