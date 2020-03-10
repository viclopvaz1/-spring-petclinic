
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/causa")
public class CausaController {

	@Autowired
	private CausaService causaService;


	@Autowired
	public CausaController(final CausaService causaService, final UserService userService, final AuthoritiesService authoritiesService) {
		this.causaService = causaService;
	}

	@GetMapping
	public String listadoCausas(final ModelMap modelMap) {
		String vista = "causas/listadoCausas";
		Iterable<Causa> causas = this.causaService.findAll();
		modelMap.addAttribute("causas", causas);
		return vista;
	}
	@GetMapping(value = "/{ong}")
	public String listadoCausasPorDonacion(final ModelMap modelMap, @PathVariable("ong") final String ong) {
		Iterable<Causa> causas = this.causaService.findCausaByOng(ong);
		String vista = "causas/listadoCausas";
		modelMap.addAttribute("causas", causas);
		return vista;
	}
}
