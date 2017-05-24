/**
 *
 */
package com.wevioo.mules.activiti.error;

import org.json.JSONObject;
import org.mule.api.MessagingException;
import org.mule.api.MuleException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
public class ActivitiException extends MuleException  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String title;
	private String description;
	
	
	
	
	public ActivitiException(String code, String title, String description) {
	
		this.code = code;
		this.title = title;
		this.description = description;
	}
	
	
	
	@Override
	public String toString() {
		JSONObject object = new JSONObject();
		object.put("title", title);
		object.put("code", code);
		object.put("description", description);
		return object.toString();
	}
		



	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
