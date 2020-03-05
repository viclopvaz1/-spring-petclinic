package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/causas")
public class CausaController {

	
	@Autowired
	private CausaService causaService;
	
	@GetMapping
	public String listadoCausas(ModelMap modelMap) {
		String vista = "causas/listadoCausas";
		Iterable<Causa> causas = causaService.findAll();
		modelMap.addAttribute("causas", causas);
		return vista;
	}
}
