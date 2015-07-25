package org.zpb.springmvc4.hanlders;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="用户名或密码错误")
public class UserNameAndPasswordNotMatchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
