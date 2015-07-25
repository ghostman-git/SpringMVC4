package org.zpb.springmvc4.crud.handler;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.zpb.springmvc4.crud.dao.DepartmentDao;
import org.zpb.springmvc4.crud.dao.EmployeeDao;
import org.zpb.springmvc4.crud.pojo.Employee;

@Controller
@RequestMapping("/emp")
public class EmloyeeHandler {
	
	@Resource
	private EmployeeDao empDao;
	@Resource
	private DepartmentDao departDao;
	
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		binder.setDisallowedFields("lastName");
//	}
	
	@ModelAttribute
	public void getEmp(@RequestParam(value="id",required=false) Integer id,ModelMap map) {
		if(id!=null) {
			map.put("emp", this.empDao.get(id));
		}
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.PUT)
	public String update(@ModelAttribute("emp") @Valid Employee emp, BindingResult result) {
		System.out.println("EmloyeeHandler.update(),"+emp);
		if(result.getErrorCount()>0) {
			List<FieldError> fieldErrors = result.getFieldErrors();
			for (FieldError field : fieldErrors) {
				System.out.println(field.getField()+","+field.getDefaultMessage());
			}
		}
		
		this.empDao.save(emp);
		return "redirect:/emp/list";
	}
	
	@RequestMapping(value="/input/{id}",method=RequestMethod.GET)
	public String input1(@PathVariable() Integer id,ModelMap map) {
		map.put("emp", this.empDao.get(id)); // modelAttribute
		map.put("departLst", this.departDao.getDepartments());
		return "emp/input";
	}
	
	@RequestMapping(value="/edit/{id}",method=RequestMethod.DELETE)
	public String save(@PathVariable("id") Integer id) {
		this.empDao.delete(id);
		return "redirect:/emp/list";
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public String save(@ModelAttribute("emp") @Valid Employee emp, BindingResult result, ModelMap map) {
		if(result.getErrorCount()>0) {
			//for(FieldError error:result.getFieldErrors()){
				//System.out.println(error.getField() + ":" + error.getDefaultMessage());
			//}
			
			// 回显数据
			map.put("departLst", this.departDao.getDepartments());
			return "emp/input";
		}
		this.empDao.save(emp);
		return "redirect:/emp/list";
	}
	
	@RequestMapping(value={"/input"},method=RequestMethod.GET)
	public String input(ModelMap map, @ModelAttribute("emp") Employee emp) {
		map.put("emp", emp); // modelAttribute
		map.put("departLst", this.departDao.getDepartments());
		return "emp/input";
	}
	
	@RequestMapping("/list")
	public String list(ModelMap map) {
		map.put("empLst", this.empDao.getAll());
		return "emp/list";
	}
	
}
