/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import java.beans.EventSetDescriptor;
import java.util.Map;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class UserController {

	private static final String VIEWS_OWNER_CREATE_FORM = "users/createOwnerForm";
	
	private static final String VIEWS_USER_CREATE_FORM = "users/updateUser";
	
	public Boolean error(BindingResult result) {
		return error(result);
	}
	
	//private static final String VIEWS_MONEDERO_CREATE_FORM = "users/updateOrDeleteMonederoForm";

	
	private final OwnerService ownerService;
	
	private final UserService userService;

	@Autowired
	public UserController(OwnerService clinicService, UserService userService) {
		this.ownerService = clinicService;
		this.userService = userService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/users/new")
	public String initCreationForm(Map<String, Object> model) {
		Owner owner = new Owner();
		model.put("owner", owner);
		return VIEWS_OWNER_CREATE_FORM;
	}

	@PostMapping(value = "/users/new")
	public String processCreationForm(@Valid Owner owner, BindingResult result) {
		if (error(result)) {
			return VIEWS_OWNER_CREATE_FORM;
		}
		else {
			//creating owner, user, and authority
			this.ownerService.saveOwner(owner);
			return "redirect:/";
		}
	}
	//------------------------------
	
//	@GetMapping(value = "/users/{userId}/edit")
//	public String initUpdateUserForm(@PathVariable("userId") int userId, Model model) {
//		User user = this.userService.findUserById(userId);
//		model.addAttribute(user);
//		return VIEWS_USER_CREATE_FORM;
//	}
//
//	@PostMapping(value = "/users/{userId}/edit")
//	public String processUpdateUserForm(@Valid User user, BindingResult result,
//			@PathVariable("userId") int userId) {
//		if (result.hasErrors()) {
//			return VIEWS_USER_CREATE_FORM;
//		}
//		else {
//			user.setId(userId);
//			this.userService.saveUser(user);
//			return "redirect:/users/{userId}";
//		}
//	}


	
//	@GetMapping(path="/new")
//	public String crearUsuario(ModelMap modelMap) {
//		String vista = "users/editUser";
//		modelMap.addAttribute("user", new User());
//		return vista;
//	}
//	
//	@PostMapping(path="/save")
//	public String salvarUsuario(@Valid User user, BindingResult result,ModelMap modelMap) {
//		String vista ="users/listUsers";
//		if(result.hasErrors()) {
//			modelMap.addAttribute("user", user);
//			return "users/editUser";
//		}else {
//			userService.saveUser(user);
//			modelMap.addAttribute("message","User successfully saved!");
//		}
//		return vista;
//			
//	}
//	
	//----------------------------------------------
	
	
	
	
//	@GetMapping(value = "/users/addmoney")
//	public String initAddMoney(Map<String, Object> model) {
//		User user = new User();
//		model.put("user", user);
//		return VIEWS_MONEDERO_CREATE_FORM;
//	}
//
//	@PostMapping(value = "/users/addmoney")
//	public String processAddMoney(@Valid User user, BindingResult result) {
//		if (result.hasErrors()) {
//			return VIEWS_MONEDERO_CREATE_FORM;
//		}
//		else {
//			this.userService.saveUser(user);
//			return "redirect:/";
//		}
//	}

}
