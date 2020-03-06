package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.service.CitaOperacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/citasOperaciones")
public class CitaOperacionController {
	
	@Autowired
	private CitaOperacionService citaOperacionService;
	
	@GetMapping
	public String listadoCitasOperaciones(ModelMap modelMap) {
		String vista = "citasOperaciones/listadoCitasOperaciones";
		Iterable<CitaOperacion> citasOperaciones = citaOperacionService.findAll();
		modelMap.addAttribute("citasOperaciones", citasOperaciones);
		return vista;
	}

}
