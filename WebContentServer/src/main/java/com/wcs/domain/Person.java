/**
 * 
 */
package com.wcs.domain;

import java.util.Map;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Prashanth
 * @date Apr 1, 2015
 * 
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class Person {

	private long id;
	
	@Size(min=2, max=30)
	private String firstName;
	
	@Size(min=2, max=30)
	private String lastName;
	private String uri;
	
	//optimization: _links can be extended as a base class to all classes used for mapping.
	private Map<Object, Map<Object, Object>> _links;
	
	public Person(){
		
	}
	
	/**
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param _links
	 */
	public Person(Object firstName, Object lastName,
			Map<Object, Map<Object, Object>> _links) {
		this.firstName = (String)firstName;
		this.lastName = (String)lastName;
		set_links(_links);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Map<Object, Map<Object, Object>> get_links() {
		return _links;
	}

	public void set_links(Map<Object, Map<Object, Object>> _links) {
		Map<Object, Object> self = null;
		if(_links!=null) self = _links.get("self");
		if(self!=null) setUri((String) self.get("href"));
		this._links = _links;
	}
}
