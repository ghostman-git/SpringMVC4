package org.zpb.springmvc4.hanlders;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class HandlerException {
	
	@ExceptionHandler({ArithmeticException.class})
	public ModelAndView handlerArithmeticException(Exception e/*, ModelMap map*/) {
		ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("ex", e);
		System.out.println("---> 发生了异常："+e);
		return modelAndView;
	}
}
