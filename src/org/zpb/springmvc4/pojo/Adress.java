package org.zpb.springmvc4.pojo;

import java.io.Serializable;

public class Adress implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3808738539572825614L;

	private String province;
	private String city;

	public Adress() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Adress [province=" + province + ", city=" + city + "]";
	}

}
