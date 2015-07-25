package org.zpb.springmvc4.crud.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.zpb.springmvc4.crud.pojo.Department;
import org.zpb.springmvc4.crud.pojo.Employee;

@Component
public class EmpConverter implements Converter<String, Employee> {

	@Override
	public Employee convert(String source) {
		// TODO Auto-generated method stub
		if(source!=null) {
			String[] values = source.split("-");
			if(values!=null && values.length==4) {
				String lastName = values[0];
				String email = values[1];
				Integer gender = Integer.parseInt(values[2]);
				Department depart = new Department(Integer.parseInt(values[3]));
				
				Employee emp = new Employee();
				emp.setLastName(lastName);
				emp.setEmail(email);
				emp.setGender(gender);
				emp.setDepartment(depart);
				
				System.out.println("EmpConverter.convert(),"+emp);
				return emp;
			}
		}
		return null;
	}

}
