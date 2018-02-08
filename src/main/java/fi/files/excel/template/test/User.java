package fi.files.excel.template.test;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
	
	private Long id;
	
	private String name;
	
	private String address;
	
	private Boolean isOk;
	
	private Date date = new Date();

	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getIsOk() {
		return isOk;
	}

	public void setIsOk(Boolean isOk) {
		this.isOk = isOk;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User(Long id, String name, String address, Boolean isOk) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.isOk = isOk;
	}

	
	
}
