package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adiestrador;
import org.springframework.samples.petclinic.service.AdiestradorService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adiestradores")
public class AdiestradorController {

	private AdiestradorService adiestradorService;
	
	@Autowired
	public AdiestradorController(final AdiestradorService adiestradorService, final UserService userService, final AuthoritiesService authoritiesService) {
		this.adiestradorService = adiestradorService;
	}
	
	@GetMapping
	public String listadoAdiestradores(ModelMap modelMap) {
		String vista = "adiestradores/listadoAdiestradores";
		Iterable<Adiestrador> adiestradores = adiestradorService.findAll();
		modelMap.addAttribute("adiestradores",adiestradores);
		return vista;
	}
	
	@GetMapping(value = "/{estrellas}")
	public String listadoBestAdiestradores(final ModelMap modelMap, @PathVariable("estrellas") final Integer estrellas){
		String vista = "adiestradores/listadoBestAdiestradores";
		Collection<Adiestrador> bestAdiestradores = adiestradorService.findAdiestradorByEstrellas(estrellas);
		modelMap.addAttribute("adiestradores", bestAdiestradores);
		return vista;
	}
}
