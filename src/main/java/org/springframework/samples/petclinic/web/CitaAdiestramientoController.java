package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.service.CitaAdiestramientoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/citasAdiestramiento")
public class CitaAdiestramientoController {


@Autowired
	private CitaAdiestramientoService citaAdiestramientoService;


	@Autowired
	public CitaAdiestramientoController(final CitaAdiestramientoService citaAdiestramientoService) {
		this.citaAdiestramientoService = citaAdiestramientoService;
	}
	
	@GetMapping(value = "/{ownerId}")
	public String listadoCitasAdiestramientoPorOwnerId(final ModelMap modelMap, @PathVariable("ownerId") final int ownerId) {
		Collection<CitaAdiestramiento> citasAdiestramiento = this.citaAdiestramientoService.findCitaAdiestramientoByOwnerId(ownerId);
		String vista = "citasAdiestramiento/listadoCitasAdiestramientoOwnersId";
		modelMap.addAttribute("citasAdiestramiento", citasAdiestramiento);
		return vista;
	}
	

	@GetMapping(value = "/all")
	public String listadoCitasAdiestramiento(ModelMap modelMap) {

		String vista = "citasAdiestramiento/listadoCitasAdiestramiento";
		Iterable<CitaAdiestramiento> citasAdiestramiento = citaAdiestramientoService.findAll();
		modelMap.addAttribute("citasAdiestramiento", citasAdiestramiento);
		return vista;
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
		
		Collection<CitaAdiestramiento> results = this.citaAdiestramientoService.findCitaAdiestramientoByPet(tipo);
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
