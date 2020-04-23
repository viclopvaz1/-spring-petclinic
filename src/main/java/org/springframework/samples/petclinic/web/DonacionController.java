
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adiestrador;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Donacion;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.AdiestradorService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.samples.petclinic.service.DonacionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/donacion")
public class DonacionController {

	private static final String	VIEWS_DONACION_CREATE_FORM	= "causas/causaDetails";

	private static final String	VIEWS_DONACION_NEW_FORM		= "donaciones/createOrUpdateDonacion";

	private UserService			userService;

	private CausaService		causaService;

	private DonacionService		donacionService;

	private VetService			vetService;

	private OwnerService		ownerService;

	private AdiestradorService	adiestradorService;

	private AuthoritiesService	authoritiesService;


	@Autowired
	public DonacionController(final CausaService causaService, final VetService vetService, final AdiestradorService adiestradorService, final DonacionService donacionService, final OwnerService ownerService, final UserService userService,
		final AuthoritiesService authoritiesService) {
		this.donacionService = donacionService;
		this.adiestradorService = adiestradorService;
		this.authoritiesService = authoritiesService;
		this.causaService = causaService;
		this.vetService = vetService;
		this.userService = userService;
		this.ownerService = ownerService;
	}

	//	@InitBinder("donacion")
	//	public void initDonacionBinder(WebDataBinder dataBinder) {
	//		dataBinder.setValidator(new DonacionValidator());
	//	}

	@GetMapping(value = "/{causaId}/new")
	public String initCreationForm(final Map<String, Object> model, @PathVariable("causaId") final int causaId) {
		Causa causa = this.causaService.findCausaById(causaId);
		if (!causa.isValido()) {
			return "exception";
		} else {
			Donacion donacion = new Donacion();
			model.put("donacion", donacion);
			return DonacionController.VIEWS_DONACION_NEW_FORM;
		}
	}

	@PostMapping(value = "/{causaId}/new")
	public String processCreationForm(@Valid final Donacion donacion, final BindingResult result, @PathVariable("causaId") final int causaId, final Map<String, Object> model, final ModelMap modelMap) {
		Causa causa = this.causaService.findCausaById(causaId);
		if (result.hasErrors() || !causa.isValido()) {
			model.put("donacion", donacion);
			return DonacionController.VIEWS_DONACION_CREATE_FORM;
		} else {

			User user = this.userService.findUserById(SecurityContextHolder.getContext().getAuthentication().getName());
			donacion.setUser(user);

			Collection<Authorities> collection = this.authoritiesService.findAll();
			String username = donacion.getUser().getUsername();
			String a = collection.stream().filter(x -> x.getUsername() == username).map(x -> x.getAuthority()).findFirst().orElse(null);
			if (a.equals("veterinarian")) {
				Vet vet = this.vetService.findVetByUser(username);
				if (donacion.getCantidad() > vet.getMonedero()) {
					modelMap.addAttribute("message", "Dinero superior a su monedero");
					return DonacionController.VIEWS_DONACION_NEW_FORM;
				}
				vet.setMonedero(vet.getMonedero() - donacion.getCantidad());
//				this.vetService.monedero(vet.getMonedero() - donacion.getCantidad(), vet.getId());
			} else if (a.equals("owner")) {
				Owner owner = this.ownerService.findOwnerByUser(username);
				if (donacion.getCantidad() > owner.getMonedero()) {
					modelMap.addAttribute("message", "Dinero superior a su monedero");
					return DonacionController.VIEWS_DONACION_NEW_FORM;
				}
				owner.setMonedero(owner.getMonedero() - donacion.getCantidad());
			} else if (a.equals("adiestrador")) {
				Adiestrador adiestrador = this.adiestradorService.findAdiestradorByUser(username);
				if (donacion.getCantidad() > adiestrador.getMonedero()) {
					modelMap.addAttribute("message", "Dinero superior a su monedero");
					return DonacionController.VIEWS_DONACION_NEW_FORM;
				}
				adiestrador.setMonedero(adiestrador.getMonedero() - donacion.getCantidad());
			}
			if (causa.getDineroRecaudado() > causa.getObjetivo()) {
				return DonacionController.VIEWS_DONACION_NEW_FORM;
			}
			causa.setDineroRecaudado(causa.getDineroRecaudado() + donacion.getCantidad());
			donacion.setCausa(causa);
			this.donacionService.saveDonacion(donacion);
			this.causaService.saveCausa(causa);

			return "redirect:/causa/" + causa.getId();
		}
	}

}
