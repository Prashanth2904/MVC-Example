/**
 * 
 */
package com.wcs.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcs.domain.Person;
import com.wcs.domain.RestResponse;

/**
 * @author Prashanth
 * @date Apr 1, 2015
 * 
 */
@Component
public class DataServerRestClient {
	
	static RestTemplate restTemplate;
	
	static{
		restTemplate = getRestTemplate();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Person> getPeople(){
		List<Person> persons = new ArrayList<Person>();
		RestResponse restResponse = restTemplate.getForObject("http://localhost:8081/people", RestResponse.class);
		
		Map<Object, Object> lHashMap1 = (Map)restResponse.get_embedded();
		for(Map<Object, Object> p: (List<Map>)lHashMap1.get("people")){
			persons.add(new Person(p.get("firstName"), p.get("lastName"), (Map)p.get("_links")));
		}
		/*for(Person p: persons){
			System.out.println(p.getFirstName());
			System.out.println(p.getLastName());
			System.out.println(p.getUri());
		}*/
		return persons;
	}
	
	private static RestTemplate getRestTemplate(){
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.registerModule(new Jackson2HalModule());

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
		converter.setObjectMapper(mapper);

		return new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
	}
	
	public void update(String url, Person person){
		restTemplate.put(url, person);
	}

	public void delete(String uri) {
		restTemplate.delete(uri);
	}
}
