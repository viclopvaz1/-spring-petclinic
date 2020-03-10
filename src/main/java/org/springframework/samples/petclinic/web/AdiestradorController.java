package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adiestrador;
import org.springframework.samples.petclinic.service.AdiestradorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adiestradores")
public class AdiestradorController {

	@Autowired
	private AdiestradorService adiestradorService;
	
	@GetMapping
	public String listadoAdiestradores(ModelMap modelMap) {
		String vista = "adiestradores/listadoAdiestradores";
		Iterable<Adiestrador> adiestradores = adiestradorService.findAll();
		modelMap.addAttribute("adiestradores",adiestradores);
		return vista;
	}
}
