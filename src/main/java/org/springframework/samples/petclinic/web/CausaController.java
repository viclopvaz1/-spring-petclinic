
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/causa")
public class CausaController {

	private CausaService		causaService;

	final AuthoritiesService	authoritiesService;


	@Autowired
	public CausaController(final CausaService causaService, final AuthoritiesService authoritiesService) {
		this.causaService = causaService;

		this.authoritiesService = authoritiesService;
	}

	@GetMapping
	public String listadoCausas(final ModelMap modelMap) {
		String vista = "causas/listadoCausas";
		Iterable<Causa> causas = this.causaService.findAll();
		modelMap.addAttribute("causas", causas);
		return vista;
	}
	@GetMapping(value = "/propias")
	public String listadoCausasPorDonacion(final ModelMap modelMap) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Iterable<Causa> causas = this.causaService.findCausaByUsername(username);
		String vista = "causas/listadoCausas";
		modelMap.addAttribute("causas", causas);
		return vista;
	}

	@GetMapping(value = "/new")
	public String initCreationForm(final Map<String, Object> model) {
		Causa causa = new Causa();
		model.put("causa", causa);
		return "causas/createOrUpdateCausaForm";
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid final Causa causa, final BindingResult result) {
		if (result.hasErrors()) {
			return "causas/createOrUpdateCausaForm";
		} else {
			//creating causa, user and authorities
			//			Vet vet = (Vet) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<Authorities> collection = this.authoritiesService.findAll();
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			String a = collection.stream().filter(x -> x.getUsername() == username).map(x -> x.getAuthority()).findFirst().orElse(null);
			if (a != null) {
				if (a.equals("veterinarian")) {
					causa.setValido(true);
					//causa.setVet(this.vetService.findVets().stream().filter(x -> x.getId() == vet.getId()).findFirst().orElse(null));
				}
			} else {
				causa.setValido(false);
			}
			this.causaService.saveCausa(causa);
			;

			return "redirect:/causa/" + causa.getId();
		}
	}

	@GetMapping("/{id}")
	public ModelAndView showCausa(@PathVariable("id") final int id) {
		ModelAndView mav = new ModelAndView("causas/causaDetails");
		mav.addObject(this.causaService.findCausaById(id));
		return mav;
	}

	@GetMapping(value = "/{causaId}/edit")
	public String initUpdateOwnerForm(@PathVariable("causaId") final int causaId, final Model model) {
		Causa causa = this.causaService.findCausaById(causaId);
		model.addAttribute(causa);
		return "causas/createOrUpdateCausaForm";
	}

	@PostMapping(value = "/{causaId}/edit")
	public String processUpdateCausaForm(@Valid final Causa causa, final BindingResult result, @PathVariable("causaId") final int causaId) {
		if (result.hasErrors()) {
			return "causas/createOrUpdateCausaForm";
		} else {
			causa.setId(causaId);
			this.causaService.saveCausa(causa);
			return "redirect:/causa/" + causa.getId();
		}
	}

	@GetMapping(value = "/{causaId}/delete")
	public String processDeleteCausaForm(@PathVariable("causaId") final int causaId) {
		Causa causa = this.causaService.findCausaById(causaId);
		this.causaService.deleteCausa(causa);
		return "redirect:/causa";
	}

	@GetMapping(value = "/noValidas")
	public String listadoCausasNoValidas(final ModelMap modelMap) {
		String vista = "causas/listadoCausas";
		Iterable<Causa> causas = this.causaService.findCausaByValido();
		modelMap.addAttribute("causas", causas);
		return vista;
	}

}
