package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Donacion;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.samples.petclinic.service.DonacionService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/donacion")
public class DonacionController {
	
	private static final String VIEWS_DONACION_CREATE_FORM = "causas/causaDetails";
	
	private static final String VIEWS_DONACION_NEW_FORM = "donaciones/createOrUpdateDonacion";
	
	@Autowired
	private CausaService causaService;
	
	@Autowired
	private DonacionService donacionService;
	
	@Autowired
	public DonacionController(final DonacionService donacionService, final UserService userService, final AuthoritiesService authoritiesService) {
		this.donacionService = donacionService;
	}
	
//	@InitBinder("donacion")
//	public void initDonacionBinder(WebDataBinder dataBinder) {
//		dataBinder.setValidator(new DonacionValidator());
//	}

	
	@GetMapping(value = "/{causaId}/new")
	public String initCreationForm(Map<String, Object> model) {
		Donacion donacion = new Donacion();
		model.put("donacion", donacion);
		return VIEWS_DONACION_NEW_FORM;
	}

	@PostMapping(value = "/{causaId}/new")
	public String processCreationForm(@Valid Donacion donacion, BindingResult result, 
			@PathVariable("causaId") int causaId, Map<String, Object> model) {
		if (result.hasErrors()) {
			model.put("donacion", donacion);
			return VIEWS_DONACION_CREATE_FORM;
		}
		else {
			Causa causa = causaService.findCausaById(causaId);
			causa.setDineroRecaudado(causa.getDineroRecaudado() + donacion.getCantidad());
			donacion.setCausa(causa);
			if(causa.getDineroRecaudado()>causa.getObjetivo()) {
				return VIEWS_DONACION_NEW_FORM;
			}
			this.donacionService.saveDonacion(donacion);
			this.causaService.saveCausa(causa);
			return "redirect:/";
		}
	}

}
