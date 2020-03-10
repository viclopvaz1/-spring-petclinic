
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/causas")
public class CausaController {

	@Autowired
	private CausaService causaService;


	@GetMapping
	public String listadoCausas(final ModelMap modelMap) {
		String vista = "causas/listadoCausas";
		Iterable<Causa> causas = this.causaService.findAll();
		modelMap.addAttribute("causas", causas);
		return vista;
	}
	@GetMapping(value = "/causas/{username}")
	public String initUpdateOwnerForm(final ModelMap modelMap, @PathVariable("username") final String username) {
		Iterable<Causa> causas = this.causaService.causesWhereIDonated(username);
		String vista = "causas/listadoCausas";
		modelMap.addAttribute("causas", causas);
		return vista;
	}
}
