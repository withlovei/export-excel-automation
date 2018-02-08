package fi.files.excel.template.domain;

import java.io.Serializable;

public class CellDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String key;
	
	private String value;
	
	/**
	 * for value = DATE, DATETIME, ZONEDATETIME
	 */
	
	private String pattern;
	

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CellDomain {key: " + key + ", value: " + value + "}";
	}

	public CellDomain() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CellDomain(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	

	
}
