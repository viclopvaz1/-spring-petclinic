package org.springframework.samples.petclinic.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.service.CitaAdiestramientoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CitaAdiestramientoController {
	
	@Autowired
	private CitaAdiestramientoService citaAdiestramientoService;


	@Autowired
	public CitaAdiestramientoController(final CitaAdiestramientoService citaAdiestramientoService) {
		this.citaAdiestramientoService = citaAdiestramientoService;
	}
	
	@GetMapping(value = "/citasAdiestramiento/{ownerId}")
	public String listadoCitasAdiestramientoPorOwnerId(final ModelMap modelMap, @PathVariable("ownerId") final int ownerId) {
		Collection<CitaAdiestramiento> citasAdiestramiento = this.citaAdiestramientoService.findCitaAdiestramientoByOwnerId(ownerId);
		String vista = "citasAdiestramiento/listadoCitasAdiestramientoOwnersId";
		modelMap.addAttribute("citasAdiestramiento", citasAdiestramiento);
		return vista;
	}
	
//	@GetMapping("/citasAdiestramiento/{ownerId}")
// 	public ModelAndView showCitasAdiestramientoOwnersId(@PathVariable("ownerId") int ownerId) {
// 		ModelAndView mav = new ModelAndView("citasAdiestramiento/listadoCitasAdiestramientoOwnersId");
// 		mav.addObject(this.citaAdiestramientoService.findCitaAdiestramientoByOwnerId(ownerId));
// 		return mav;
// 	}

}
