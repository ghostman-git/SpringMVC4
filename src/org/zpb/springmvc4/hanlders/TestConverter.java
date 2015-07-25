package org.zpb.springmvc4.hanlders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zpb.springmvc4.crud.dao.EmployeeDao;
import org.zpb.springmvc4.crud.pojo.Employee;

@Controller
public class TestConverter {

	@Autowired
	private EmployeeDao empDao;
	
	@RequestMapping("/converter")
	public String testConversionServiceConverer(@RequestParam("emp") Employee emp) {
		System.out.println("TestConverter.testConverter(),"+emp);
		this.empDao.save(emp);
		return "redirect:/emp/list";
	}
	
}
