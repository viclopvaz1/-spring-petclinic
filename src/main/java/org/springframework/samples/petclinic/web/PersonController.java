package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adiestrador;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.AdiestradorService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.samples.petclinic.service.DonacionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PersonService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/monedero")
public class PersonController {
	
	private UserService			userService;
	
	private PersonService personService;

	private CausaService		causaService;

	private DonacionService		donacionService;

	private VetService			vetService;

	private OwnerService		ownerService;

	private AdiestradorService	adiestradorService;

	private AuthoritiesService	authoritiesService;


	@Autowired
	public PersonController(final CausaService causaService, final VetService vetService, final AdiestradorService adiestradorService, final DonacionService donacionService, final OwnerService ownerService, final UserService userService,
		final AuthoritiesService authoritiesService, final PersonService personService) {
		this.donacionService = donacionService;
		this.adiestradorService = adiestradorService;
		this.authoritiesService = authoritiesService;
		this.causaService = causaService;
		this.vetService = vetService;
		this.userService = userService;
		this.ownerService = ownerService;
		this.personService = personService;
	}
	
	@GetMapping(value = "/edit")
	public String initUpdateMonederoForm(final Model model) {
		Person person = new Person();
		person.setFirstName("Alberto");
		person.setLastName("Cordon");
		model.addAttribute(SecurityContextHolder.getContext().getAuthentication().getName(), "username");
		model.addAttribute(person);
		return "users/editUser";
	}

	@PostMapping(value = "/edit")
	public String processUpdateMonederoForm(@Valid final Person person, final BindingResult result, final Model model) {
		if (result.hasErrors()) {
			return "users/editUser";
		} else {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			
			Collection<Authorities> collection = this.authoritiesService.findAll();
			String a = collection.stream().filter(x -> x.getUsername().equals(username)).map(x -> x.getAuthority()).findFirst().orElse(null);
			if(person.getMonedero() >=0) {
				if (a.equals("veterinarian")) {
					Vet vet = this.vetService.findVetByUser(username);
					vet.setMonedero(person.getMonedero() + vet.getMonedero());
					this.vetService.saveVet(vet);
				} else if (a.equals("owner")) {
					Owner owner = this.ownerService.findOwnerByUser(username);
					owner.setMonedero(person.getMonedero() + owner.getMonedero());
					this.ownerService.saveOwner(owner);
				} else if (a.equals("adiestrador")) {
					Adiestrador adiestrador = this.adiestradorService.findAdiestradorByUser(username);
					adiestrador.setMonedero(person.getMonedero() + adiestrador.getMonedero());
					this.adiestradorService.saveAdiestrador(adiestrador);
				}
				
				return "welcome";
			}
			


			return "exception";
		}
	}
	

}
