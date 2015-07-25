package org.zpb.springmvc4.hanlders;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldController {
	
	/**
	 * 1、使用@RequestMapping来映射请求url
	 * 2、返回值会通过试图解析器解析为实际的物理视图，对于InternalResourceViewResolver试图解析器而言，会做如何解析：
	 *    通过prefix+returnVal+suffix这样的方式得到实际的物理视图，然后会做转发操作
	 *    /WEB-INF/views/success.jsp
	 * @return
	 */
	@RequestMapping("/hello")
	public String hello() {
		System.out.println("hello world springmvc4");
		return "success";
	}
	
}
