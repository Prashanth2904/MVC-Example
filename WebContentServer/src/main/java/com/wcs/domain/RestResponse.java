/**
 * 
 */
package com.wcs.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Prashanth
 * @date Apr 1, 2015
 * 
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class RestResponse {
	
	@JsonProperty("_links")
	private Object _links;
	
	@JsonProperty("_embedded")
	private Object _embedded;
	
	@JsonProperty("page")
	private Object page;
	
	public Object get_links() {
		return _links;
	}
	public void set_links(Object _links) {
		this._links = _links;
	}
	public Object get_embedded() {
		return _embedded;
	}
	public void set_embedded(Object _embedded) {
		this._embedded = _embedded;
	}
	public Object getPage() {
		return page;
	}
	public void setPage(Object page) {
		this.page = page;
	}
	
	class Embedded{
		
		@JsonProperty("people")
		private List<Person> people;

		public List<Person> getPersons() {
			return people;
		}

		public void setPersons(List<Person> persons) {
			this.people = persons;
		}
		
	}
}
