package com.devsuperior.dscommerce.dto;

public class FieldMessage {

	private String fieldName;
	private String messsage;
	
	
	public FieldMessage(String fieldName, String messsage) {
		super();
		this.fieldName = fieldName;
		this.messsage = messsage;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public String getMesssage() {
		return messsage;
	}
	
}
