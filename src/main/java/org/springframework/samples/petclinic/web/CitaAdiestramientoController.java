package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CitaAdiestramientoService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/citasAdiestramiento")
public class CitaAdiestramientoController {

	private static final String VIEWS_CITAADIESTRAMIENTO_CREATE_FORM = "users/createCitaAdiestramientoForm";

	@Autowired
	private CitaAdiestramientoService ciService;
	
	
	@Autowired
    public CitaAdiestramientoController(final CitaAdiestramientoService citaService, final UserService userService, final AuthoritiesService authoritiesService) {
        this.ciService = citaService;
    }
	

	@GetMapping(value = "/all")
	public String listadoCitasAdiestramiento(ModelMap modelMap) {

		String vista = "citasAdiestramiento/listadoCitasAdiestramiento";
		Iterable<CitaAdiestramiento> citasAdiestramiento = ciService.findAll();
		modelMap.addAttribute("citasAdiestramiento", citasAdiestramiento);
		return vista;
	}

	@GetMapping(value = "/new")
	public String initCreationForm(Map<String, Object> model) {
		CitaAdiestramiento citaAdiestramiento = new CitaAdiestramiento();
		model.put("citaAdiestramiento", citaAdiestramiento);
		return VIEWS_CITAADIESTRAMIENTO_CREATE_FORM;
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid CitaAdiestramiento citaAdiestramiento, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_CITAADIESTRAMIENTO_CREATE_FORM;
		} else {
			this.ciService.saveCitaAdiestramiento(citaAdiestramiento);
			return "redirect:/citasAdiestramiento/";
		}
	}

	@GetMapping(value = "/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("citaAdiestramiento", new CitaAdiestramiento());
		return "/citasAdiestramiento/findCitasAdiestramiento";
	}

	@GetMapping
	public String processFindForm(CitaAdiestramiento citaAdiestramiento, BindingResult result,
			Map<String, Object> model) {

		// allow parameterless GET request for /owners to return all records
		if (citaAdiestramiento.getPet().getType().getName() == null) {
			citaAdiestramiento.getPet().getType().setName(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		String tipo = citaAdiestramiento.getPet().getType().getName();
		
		Collection<CitaAdiestramiento> results = this.ciService.findCitaAdiestramientoByPet(tipo);
		if (results.isEmpty()) {
			// no owners found
			result.rejectValue("pet.type.name", "notFound", "not found");
			return "citasAdiestramiento/findCitasAdiestramiento";
			} else {
			// multiple owners found
			model.put("citasAdiestramiento", results);
			return "citasAdiestramiento/listadoCitasAdiestramientoFiltrado";
		} 
	}

}
