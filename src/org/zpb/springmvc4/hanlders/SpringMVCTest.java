package org.zpb.springmvc4.hanlders;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.zpb.springmvc4.crud.dao.EmployeeDao;
import org.zpb.springmvc4.crud.pojo.Employee;
import org.zpb.springmvc4.pojo.User;

@Controller
@RequestMapping("/springmvc")
@SessionAttributes(value={"user"},types=String.class)
public class SpringMVCTest {

	private final String SUCCESS = "success";
	@Resource
	private EmployeeDao empDao;
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="测试404")
	@RequestMapping("/responsestatus")
	public String testResponseStatusExceptionResolver(@RequestParam("i") int i) {
		if(i==0) {
			throw new UserNameAndPasswordNotMatchException();
		}
		System.out.println("正常执行");
		return SUCCESS;
	}
	
	/**
	 * 1. 在 @ExceptionHandler 方法的入参中可以加入 Exception 类型的参数, 该参数即对应发生的异常对象
	 * 2. @ExceptionHandler 方法的入参中不能传入 Map. 若希望把异常信息传导页面上, 需要使用 ModelAndView 作为返回值
	 * 3. @ExceptionHandler 方法标记的异常有优先级的问题. 
	 * 4. @ControllerAdvice: 如果在当前 Handler 中找不到 @ExceptionHandler 方法来出来当前方法出现的异常, 
	 * 则将去 @ControllerAdvice 标记的类中查找 @ExceptionHandler 标记的方法来处理异常. 
	 */
//	@ExceptionHandler({ArithmeticException.class})
//	public ModelAndView handlerArithmeticException(Exception e/*, ModelMap map*/) {
//		ModelAndView modelAndView = new ModelAndView("error");
//		modelAndView.addObject("ex", e);
//		System.out.println("发生了异常："+e);
//		//map.put("ex", e);
//		///return "error";
//		return modelAndView;
//	}
	
	@RequestMapping("/exception")
	public String testExceptionHandlerExceptionResolver(@RequestParam("i") int i) {
		System.out.println("result: "+(10/i));
		return SUCCESS;
	}
	
	@RequestMapping("fileupload")
	public String testFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
		System.out.println("ContentType: "+file.getContentType());
		System.out.println("OriginalFilename: "+file.getOriginalFilename());
		System.out.println("InputStream: "+file.getInputStream());
		System.out.println("Size: "+file.getSize());
		return "success";
	}
	
	@RequestMapping("/responseentity")
	public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
		ServletContext context = session.getServletContext();
		InputStream inputStream = context.getResourceAsStream("/js/jquery-1.11.1.min.js");
		byte[] body = new byte[inputStream.available()];
		inputStream.read(body);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=jquery.js");
		
		HttpStatus statusCode = HttpStatus.OK;
		
		ResponseEntity<byte[]> resp = new ResponseEntity<byte[]>(body, headers, statusCode);
		return resp;
	}
	
	@ResponseBody
	@RequestMapping("/byties")
	public byte[] testHttpMessageConverter() throws IOException {
		// ClassPathResource生成的classes文件路劲
		org.springframework.core.io.Resource resource = new ClassPathResource("springMVC.xml");
		byte[] fileData = FileCopyUtils.copyToByteArray(resource.getInputStream());
		return fileData;
	}
	
	@ResponseBody
	@RequestMapping("/converter")
	public String testHttpMessageConverter(@RequestBody String body) {
		System.out.println("[body]"+body);
		return "converter! "+new Date();
	}
	
	@ResponseBody
	@RequestMapping("/json")
	public Collection<Employee> testJson(){
		return this.empDao.getAll();
	}
	
	@RequestMapping("/redirect")
	public String testRedirect(){
		System.out.println("testRedirect");
		return "redirect:/index.jsp";
	}
	
	@RequestMapping("/view")
	public String testView(){
		System.out.println("testView");
		return "helloView";
	}
	
	@RequestMapping("/viewandviewresolver")
	public String testViewAndViewResolver(){
		System.out.println("testViewAndViewResolver");
		return SUCCESS;
	}
	
	/**
	 * 1. 有 @ModelAttribute 标记的方法, 会在每个目标方法执行之前被 SpringMVC 调用! 
	 * 2. @ModelAttribute 注解也可以来修饰目标方法 POJO 类型的入参, 其 value 属性值有如下的作用:
	 * 1). SpringMVC 会使用 value 属性值在 implicitModel 中查找对应的对象, 若存在则会直接传入到目标方法的入参中.
	 * 2). SpringMVC 会一 value 为 key, POJO 类型的对象为 value, 存入到 request 中. 
	 */
	@ModelAttribute
	public void getUser(/*@RequestParam(value="id") Integer id,*/ ModelMap modelMap) {
		//if(id!=null) {
			modelMap.put("modelUser", new User(1, "Tom", "123", "zpb@163.com", 14));
		//}
	}
	/**
	 * 运行流程:
	 * 1. 执行 @ModelAttribute 注解修饰的方法: 从数据库中取出对象, 把对象放入到了 Map 中. 键为: user
	 * 2. SpringMVC 从 Map 中取出 User 对象, 并把表单的请求参数赋给该 User 对象的对应属性.
	 * 3. SpringMVC 把上述对象传入目标方法的参数. 
	 * 
	 * 注意: 在 @ModelAttribute 修饰的方法中, 放入到 Map 时的键需要和目标方法入参类型的第一个字母小写的字符串一致!
	 * 
	 * SpringMVC 确定目标方法 POJO 类型入参的过程
	 * 1. 确定一个 key:
	 * 1). 若目标方法的 POJO 类型的参数木有使用 @ModelAttribute 作为修饰, 则 key 为 POJO 类名第一个字母的小写
	 * 2). 若使用了  @ModelAttribute 来修饰, 则 key 为 @ModelAttribute 注解的 value 属性值. 
	 * 2. 在 implicitModel 中查找 key 对应的对象, 若存在, 则作为入参传入
	 * 1). 若在 @ModelAttribute 标记的方法中在 Map 中保存过, 且 key 和 1 确定的 key 一致, 则会获取到. 
	 * 3. 若 implicitModel 中不存在 key 对应的对象, 则检查当前的 Handler 是否使用 @SessionAttributes 注解修饰, 
	 * 若使用了该注解, 且 @SessionAttributes 注解的 value 属性值中包含了 key, 则会从 HttpSession 中来获取 key 所
	 * 对应的 value 值, 若存在则直接传入到目标方法的入参中. 若不存在则将抛出异常. 
	 * 4. 若 Handler 没有标识 @SessionAttributes 注解或 @SessionAttributes 注解的 value 值中不包含 key, 则
	 * 会通过反射来创建 POJO 类型的参数, 传入为目标方法的参数
	 * 5. SpringMVC 会把 key 和 POJO 类型的对象保存到 implicitModel 中, 进而会保存到 request 中. 
	 * 
	 * 源代码分析的流程
	 * 1. 调用 @ModelAttribute 注解修饰的方法. 实际上把 @ModelAttribute 方法中 Map 中的数据放在了 implicitModel 中.
	 * 2. 解析请求处理器的目标参数, 实际上该目标参数来自于 WebDataBinder 对象的 target 属性
	 * 1). 创建 WebDataBinder 对象:
	 * ①. 确定 objectName 属性: 若传入的 attrName 属性值为 "", 则 objectName 为类名第一个字母小写. 
	 * *注意: attrName. 若目标方法的 POJO 属性使用了 @ModelAttribute 来修饰, 则 attrName 值即为 @ModelAttribute 
	 * 的 value 属性值 
	 * 
	 * ②. 确定 target 属性:
	 * 	> 在 implicitModel 中查找 attrName 对应的属性值. 若存在, ok
	 * 	> *若不存在: 则验证当前 Handler 是否使用了 @SessionAttributes 进行修饰, 若使用了, 则尝试从 Session 中
	 * 获取 attrName 所对应的属性值. 若 session 中没有对应的属性值, 则抛出了异常. 
	 * 	> 若 Handler 没有使用 @SessionAttributes 进行修饰, 或 @SessionAttributes 中没有使用 value 值指定的 key
	 * 和 attrName 相匹配, 则通过反射创建了 POJO 对象
	 * 
	 * 2). SpringMVC 把表单的请求参数赋给了 WebDataBinder 的 target 对应的属性. 
	 * 3). *SpringMVC 会把 WebDataBinder 的 attrName 和 target 给到 implicitModel. 
	 * 近而传到 request 域对象中. 
	 * 4). 把 WebDataBinder 的 target 作为参数传递给目标方法的入参. 
	 */
	@RequestMapping("/modelattribute")
	public String testModelAttribute(@ModelAttribute("modelUser") User user) {
		System.out.println("SpringMVCTest.testModelAttribute(),user:"+user);
		return SUCCESS;
	}
	
	/**
	 * @SessionAttributes 除了可以通过属性名指定需要放到会话中的属性外(实际上使用的是 value 属性值),
	 * 还可以通过模型属性的对象类型指定哪些模型属性需要放到会话中(实际上使用的是 types 属性值)
	 * 
	 * 注意: 该注解只能放在类的上面. 而不能修饰放方法. 
	 */
	@RequestMapping("/sessionattributes")
	public String testSessionAttributes(Map<String, Object> map){
		User user = new User("小周", "123", "380348@qq.com", 123);
		map.put("user",user);
		map.put("school", "菏泽学院");
		return SUCCESS;
	}
	
	/**
	 * 目标方法可以添加 Map 类型(实际上也可以是 Model 类型或 ModelMap 类型)的参数. 
	 * @param map
	 * @return
	 */
	@RequestMapping("/map")
	public String testMap(Map<String, Object> map){
		System.out.println(map.getClass().getName()); 
		map.put("names", Arrays.asList("Tom", "Jerry", "Mike"));
		return SUCCESS;
	}
	
	/**
	 * 目标方法的返回值可以是 ModelAndView 类型。 
	 * 其中可以包含视图和模型信息
	 * SpringMVC 会把 ModelAndView 的 model 中数据放入到 request 域对象中. 
	 * @return
	 */
	@RequestMapping("modelandview")
	public ModelAndView testModelAndView() {
		String viewName = SUCCESS;
		ModelAndView modelAndView = new ModelAndView(viewName);
		//添加模型数据到 ModelAndView 中.
		modelAndView.addObject("time", new Date());
		return modelAndView;
	}
	
	/**
	 * 可以使用 Serlvet 原生的 API 作为目标方法的参数 具体支持以下类型
	 * 
	 * HttpServletRequest 
	 * HttpServletResponse 
	 * HttpSession
	 * java.security.Principal 
	 * Locale InputStream 
	 * OutputStream 
	 * Reader 
	 * Writer
	 */
	@RequestMapping("servletapi")
	public String testServletPAI(HttpServletRequest request, HttpServletResponse response, Writer out) throws IOException {
		System.out.println("SpringMVCTest.testServletPAI(),request:"+request+",response:"+response);
		out.write("hello springmvc");
		return null;
		//return SUCCESS;
	}
	
	/**
	 * Spring MVC 会按请求参数名和 POJO 属性名进行自动匹配， 自动为该对象填充属性值。支持级联属性。
	 * 如：dept.deptId、dept.address.tel 等
	 */
	@RequestMapping("/pojo")
	public String testPojo(User user) {
		System.out.println("SpringMVCTest.testPojo(),[user]:"+user);
		return SUCCESS;
	}
	
	/**
	 * 了解:
	 * 
	 * @CookieValue: 映射一个 Cookie 值. 属性同 @RequestParam
	 */
	@RequestMapping("/cookievalue")
	public String testCookieValue(@CookieValue("JSESSIONID") String cookie) {
		System.out.println("SpringMVCTest.testCookieValue(),cookie:"+cookie);
		return SUCCESS;
	}
	
	/**
	 * 了解: 映射请求头信息 用法同 @RequestParam
	 */
	@RequestMapping("/requestheader")
	public String testRequestHeader(@RequestHeader("Accept-Language") String language) {
		System.out.println("SpringMVCTest.testRequestHeader(),language:"+language);
		return SUCCESS;
	}
	
	/**
	 * @RequestParam 来映射请求参数
	 * value 值即请求参数的参数名 
	 * required 该参数是否必须. 默认为 true
	 * defaultValue 请求参数的默认值
	 */
	@RequestMapping("/requestparam")
	public String testReqestParam(@RequestParam(value="username", required=true) String name, 
			@RequestParam(value="age", required=false, defaultValue="null") Integer age) {
		System.out.println("SpringMVCTest.testReqestParam(),name:"+name+",age:"+age);
		return SUCCESS;
	}
	
	/**
	 * Rest 风格的 URL. 以 CRUD 为例: 新增: /order POST 修改: /order/1 PUT update?id=1 获取:
	 * /order/1 GET get?id=1 删除: /order/1 DELETE delete?id=1
	 * 
	 * 如何发送 PUT 请求和 DELETE 请求呢 ? 1. 需要配置 HiddenHttpMethodFilter 2. 需要发送 POST 请求
	 * 3. 需要在发送 POST 请求时携带一个 name="_method" 的隐藏域, 值为 DELETE 或 PUT
	 * 
	 * 在 SpringMVC 的目标方法中如何得到 id 呢? 使用 @PathVariable 注解
	 * 
	 */
	@RequestMapping(value="/rest", method=RequestMethod.DELETE)
	public String testRESTDELETE() {
		System.out.println("SpringMVCTest.testRESTDELETE()");
		return SUCCESS;
	}
	@RequestMapping(value="/rest", method=RequestMethod.PUT)
	public String testRESTPUT() {
		System.out.println("SpringMVCTest.testRESTPUT()");
		return SUCCESS;
	}
	@RequestMapping(value="/rest", method=RequestMethod.POST)
	public String testRESTPOST() {
		System.out.println("SpringMVCTest.testRESTPOST()");
		return SUCCESS;
	}
	@RequestMapping(value="/rest/{id}", method=RequestMethod.GET)
	public String testRESTGET(@PathVariable("id") Integer id) {
		System.out.println("SpringMVCTest.testRESTGET()"+id);
		return SUCCESS;
	}
	
	/**
	 * @PathVariable 可以来映射 URL 中的占位符到目标方法的参数中.
	 * @param id
	 * @return
	 */
	@RequestMapping("/pathvariable/{id}")
	public String testPathVariable(@PathVariable("id") Integer id) {
		System.out.println("SpringMVCTest.testPathVariable()"+id);
		return SUCCESS;
	}
	
	@RequestMapping("/antpath/*/abc")
	public String testAntPath() {
		System.out.println("SpringMVCTest.testAntPath()");
		return SUCCESS;
	}
	
	/**
	 * 了解: 可以使用 params 和 headers 来更加精确的映射请求. params 和 headers 支持简单的表达式.
	 * @return
	 */
	@RequestMapping(value="/paramsandheaders",params={"name", "age!=10"},
			headers={"Accept-Language=zh-CN,zh;q=0.8"})
	public String testParamsAndHeaders() {
		System.out.println("SpringMVCTest.testParamsAndHeaders()");
		return SUCCESS;
	}
	
	/**
	 * 常用: 使用 method 属性来指定请求方式
	 */
	@RequestMapping(value="method",method=RequestMethod.POST)
	public String testMethod() {
		System.out.println("SpringMVCTest.testMethod()");
		return SUCCESS;
	}
	
	/**
	 * 1) @RequestMapping 除了修饰方法, 还可来修饰类 
	 * 2) 类定义处: 提供初步的请求映射信息。相对于 WEB 应用的根目录
	 * 3) 方法处: 提供进一步的细分映射信息。 相对于类定义处的 URL。
	 *    若类定义处未标注 @RequestMapping，则方法处标记的 URL
	 *    相对于 WEB 应用的根目录
	 */
	@RequestMapping("/mapping")
	public String testMapping() {
		System.out.println("SpringMVCTest.testMapping()");
		return SUCCESS;
	}

}
