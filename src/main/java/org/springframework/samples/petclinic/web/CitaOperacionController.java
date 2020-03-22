package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CitaOperacionService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
//@RequestMapping("/citasOperaciones")
public class CitaOperacionController {

	@Autowired
	private CitaOperacionService citaOperacionService;


	@Autowired
	public CitaOperacionController(final CitaOperacionService citaOperacionService, final UserService userService, final AuthoritiesService authoritiesService) {
		this.citaOperacionService = citaOperacionService;
	}
	
	@GetMapping(value = "/citasOperaciones/{tipoOperacion}")
	public String listadoCitasOperacionesPorTipoDonacion(final ModelMap modelMap, @PathVariable("tipoOperacion") final String tipoOperacion) {
//		tipoOperacion.substring(33, tipoOperacion.length());
//		tipoOperacion.
		Iterable<CitaOperacion> citasOperaciones = this.citaOperacionService.findCitaOperacionByTipoOperacion(tipoOperacion);
		String vista = "citasOperaciones/listadoCitasOperacionesFiltrado";
		modelMap.addAttribute("citasOperaciones", citasOperaciones);
		return vista;
	}
	
	@GetMapping(value = "/citasOperaciones/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("citaOperacion", new CitaOperacion());
		return "/citasOperaciones/findCitasOperaciones";
	}
	
	@GetMapping(value = "/citasOperaciones")
	public String processFindForm(CitaOperacion citaOperacion, BindingResult result, Map<String, Object> model) {

		String a = citaOperacion.getTipoOperacion().getName();
		if(a.contains("+")) {
			a.replace("+", " ");
		}
		citaOperacion.getTipoOperacion().setName(a);
		// allow parameterless GET request for /owners to return all records
		if (citaOperacion.getTipoOperacion().getName() == null) {
			citaOperacion.getTipoOperacion().setName(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		Collection<CitaOperacion> results = this.citaOperacionService.findCitaOperacionByTipoOperacion(citaOperacion.getTipoOperacion().getName());
		if (results.isEmpty()) {
			// no owners found
			result.rejectValue("tipoOperacion", "notFound", "not found");
			return "citasOperaciones/findCitasOperaciones";
		}
		else {
			// multiple owners found
			model.put("citasOperaciones", results);
			return "citasOperaciones/listadoCitasOperacionesFiltrado";
		}
	}
	
}
