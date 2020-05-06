
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
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.AdiestradorService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.samples.petclinic.service.DonacionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/causa")
public class CausaController {

	private CausaValidator		causaValidator	= new CausaValidator();

	private CausaService		causaService;

	private DonacionService		donacionService;

	private OwnerService		ownerService;

	private AdiestradorService	adiestradorService;

	private VetService			vetService;

	final AuthoritiesService	authoritiesService;


	@Autowired
	public CausaController(final CausaService causaService, final AuthoritiesService authoritiesService, final VetService vetService, final AdiestradorService adiestradorService, final DonacionService donacionService, final OwnerService ownerService) {
		this.causaService = causaService;
		this.adiestradorService = adiestradorService;
		this.ownerService = ownerService;
		this.donacionService = donacionService;
		this.vetService = vetService;
		this.authoritiesService = authoritiesService;
	}

	@GetMapping
	public String listadoCausas(final ModelMap modelMap) {
		String vista = "causas/listadoCausas";
		Collection<Causa> causas = this.causaService.findAll();
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
	public String processCreationForm(@Valid final Causa causa, final BindingResult result, final Map<String, Object> model) {
		if (result.hasErrors()) {
			return "causas/createOrUpdateCausaForm";
		} else {
			String mensaje = "";
			try {
				this.causaValidator.validateFechas(causa);
			} catch (FechasException e) {
				mensaje = "La fecha de incio debe ser anterior a la de fin. ";
				ObjectError errorFecha = new ObjectError("ErrorFecha", "La fecha de incio debe ser anterior a la de fin. ");
				result.addError(errorFecha);
			}
			try {
				this.causaValidator.validateObjetivo(causa);
			} catch (RecaudadoYObjetivoException e) {
				mensaje = mensaje.concat("El dinero recaudado debe ser menor al objetivo");
				ObjectError errorFecha = new ObjectError("ErrorDineroRecaudadoYObjetivo", "El dinero recaudado debe ser menor al objetivo");
				result.addError(errorFecha);
			}
			if (mensaje != "") {
				model.put("mensaje", mensaje);
				return "causas/createOrUpdateCausaForm";
			} else {
				Collection<Authorities> collection = this.authoritiesService.findAll();
				String username = SecurityContextHolder.getContext().getAuthentication().getName();
//				String a = collection.stream().filter(x -> x.getUsername() == username).map(x -> x.getAuthority()).findFirst().orElse(null);
				String a = collection.stream().filter(x -> x.getUsername().equals(username)).map(x -> x.getAuthority()).findFirst().orElse(null);
//				String a = null;
//				for(Authorities u : collection) {
//					if(u.getUsername().equals(username)) {
//						a = u.getAuthority();
//						System.out.println(u);
//						System.out.println(u.getUsername());
//						System.out.println(u.getAuthority());
//						break;
//					}
//				}
				if (a != null) {
					if (a.equals("veterinarian")) {
						causa.setValido(true);
						//causa.setVet(this.vetService.findVets().stream().filter(x -> x.getId() == vet.getId()).findFirst().orElse(null));
					} else {
						causa.setValido(false);
					}
				}
//				System.out.println(collection);
//				System.out.println(username);
//				System.out.println(a);
//				System.out.println(causa);
				this.causaService.saveCausa(causa);

				return "redirect:/causa/" + causa.getId();
			}

		}

	}

	@GetMapping("/{id}")
	public ModelAndView showCausa(@PathVariable("id") final int id, final Model model) {
		ModelAndView mav = new ModelAndView("causas/causaDetails");
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Collection<Authorities> collection = this.authoritiesService.findAll();
//		String a = collection.stream().filter(x -> x.getUsername() == username).map(x -> x.getAuthority()).findFirst().orElse(null);
		String a = collection.stream().filter(x -> x.getUsername().equals(username)).map(x -> x.getAuthority()).findFirst().orElse(null);
		boolean user = a.equals("owner");
		model.addAttribute("user", user);
		mav.addObject(this.causaService.findCausaById(id));
//		System.out.println(a);
//		System.out.println(this.causaService.findCausaById(id));
		return mav;
	}

	@GetMapping(value = "/{causaId}/edit")
	public String initUpdateOwnerForm(@PathVariable("causaId") final int causaId, final Model model) {
		Causa causa = this.causaService.findCausaById(causaId);
		model.addAttribute("edit", true);
		model.addAttribute(causa);
		return "causas/createOrUpdateCausaForm";
	}

	@PostMapping(value = "/{causaId}/edit")
	public String processUpdateCausaForm(@Valid final Causa causa, final BindingResult result, @PathVariable("causaId") final int causaId, final Model model) {
		if (result.hasErrors()) {
			return "causas/createOrUpdateCausaForm";
		} else {
			String mensaje = "";
			try {
				this.causaValidator.validateFechas(causa);
			} catch (FechasException e) {
				mensaje = "La fecha de incio debe ser anterior a la de fin. ";
				ObjectError errorFecha = new ObjectError("ErrorFecha", "La fecha de incio debe ser anterior a la de fin. ");
				result.addError(errorFecha);
			}
			try {
				this.causaValidator.validateObjetivo(causa);
			} catch (RecaudadoYObjetivoException e) {
				mensaje = mensaje.concat("El dinero recaudado debe ser menor al objetivo");
				ObjectError errorFecha = new ObjectError("ErrorDineroRecaudadoYObjetivo", "El dinero recaudado debe ser menor al objetivo");
				result.addError(errorFecha);
			}
			if (mensaje != "") {
				model.addAttribute("mensaje", mensaje);
				model.addAttribute(causa);
				model.addAttribute("edit", true);
				return "causas/createOrUpdateCausaForm";
			} else {
				causa.setId(causaId);
				this.causaService.saveCausa(causa);
				return "redirect:/causa/" + causa.getId();
			}
		}
	}

	@GetMapping(value = "/{causaId}/delete")
	public String processDeleteCausaForm(@PathVariable("causaId") final int causaId) {
		Causa causa = this.causaService.findCausaById(causaId);
		Collection<Donacion> donaciones = causa.getDonaciones();
		for (Donacion donacion : donaciones) {
			Collection<Authorities> collection = this.authoritiesService.findAll();
			String username = donacion.getUser().getUsername();
//			String a = collection.stream().filter(x -> x.getUsername() == username).map(x -> x.getAuthority()).findFirst().orElse(null);
			String a = collection.stream().filter(x -> x.getUsername().equals(username)).map(x -> x.getAuthority()).findFirst().orElse(null);
			if (a.equals("veterinarian")) {
				Vet vet = this.vetService.findVetByUser(username);
				vet.setMonedero(vet.getMonedero() + donacion.getCantidad());
//				this.vetService.monedero(vet.getMonedero() + donacion.getCantidad(), vet.getId());
			} else if (a.equals("owner")) {
				Owner owner = this.ownerService.findOwnerByUser(username);
				owner.setMonedero(owner.getMonedero() + donacion.getCantidad());
			} else if (a.equals("adiestrador")) {
				Adiestrador adiestrador = this.adiestradorService.findAdiestradorByUser(username);
				adiestrador.setMonedero(adiestrador.getMonedero() + donacion.getCantidad());
			}
		}
		this.donacionService.deleteDonacionAll(donaciones);
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
