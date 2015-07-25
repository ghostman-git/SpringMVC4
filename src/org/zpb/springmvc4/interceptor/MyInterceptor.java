package org.zpb.springmvc4.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor implements HandlerInterceptor {

	/**
	 * 该方法在目标方法之前被调用. 
	 * 若返回值为 true, 则继续调用后续的拦截器和目标方法. 
	 * 若返回值为 false,则不会再调用后续的拦截器和目标方法.
	 * 
	 * 可以考虑做权限. 日志, 事务等.
	 */
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("action之前执行！！！");
		return true; // 继续执行action
	}

	/**
	 * 调用目标方法之后, 但渲染视图之前. 可以对请求域中的属性或视图做出修改.
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Action执行之后，生成视图之前执行！！");
	}

	/**
	 * 渲染视图之后被调用. 释放资源
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("最后执行！！！一般用于释放资源！！");
	}

}
