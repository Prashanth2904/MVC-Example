/**
 * 
 */
package com.wcs.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wcs.domain.Person;
import com.wcs.service.DataServerRestClient;

/**
 * @author Prashanth
 * @date Apr 1, 2015
 */

@Controller
public class GreetingController {

	private DataServerRestClient restClient;

	/**
	 * This is the very landing page!
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/greeting")
	public String greeting(Model model) {
		model.addAttribute("persons", getPeople());
		return "greeting";
	}

	/**
	 * This will get the corresponding uri of the user selection as input and
	 * opens a new edit page;
	 * 
	 * Optimization: Can be done if this edit call has been avoided; this means
	 * the data on landing page should be edit-enabled on clicking edit button
	 * on a row. Thereafter on clicking submit it calls the update method below.
	 * 
	 * @param uri
	 * @param person
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String showForm(@RequestParam("uri") String uri, Person person,
			Model model) {
		model.addAttribute("uri", uri);
		return "edit";
	}

	/**
	 * After you enter the fields, this method will update the backend using
	 * restTemplate.
	 * 
	 * @param person
	 * @param model
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String checkPersonInfo(@Valid Person person, Model model,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "form";
		}
		restClient.update(person.getUri(), person);
		model.addAttribute("persons", getPeople());
		return "greeting";
	}

	/**
	 * Deletes the element at specified uri.
	 * 
	 * @param uri
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam("uri") String uri, Model model) {
		restClient.delete(uri);
		model.addAttribute("persons", getPeople());
		return "greeting";
	}

	private List<Person> getPeople() {
		return restClient.getPeople();
	}

	@Autowired
	public GreetingController(DataServerRestClient restClient) {
		this.restClient = restClient;
	}

}