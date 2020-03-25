package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.model.TipoOperacion;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CitaOperacionService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TipoOperacionService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CitaOperacionController {

	@Autowired
	private CitaOperacionService citaOperacionService;
	
	final AuthoritiesService	authoritiesService;
	
	@Autowired
	private PetService petService;

	@Autowired
	private VetService vetService;
	
	@Autowired
	private TipoOperacionService tipoOperacionService;


	@Autowired
	public CitaOperacionController(final CitaOperacionService citaOperacionService, TipoOperacionService tipoOperacionService, VetService vetService, PetService petService, final AuthoritiesService authoritiesService) {
		this.citaOperacionService = citaOperacionService;
		this.authoritiesService = authoritiesService;
		this.petService = petService;
		this.vetService = vetService;
		this.tipoOperacionService = tipoOperacionService;
	}
	
	@ModelAttribute("tipoOperacion")
	public Collection<TipoOperacion> populateTiposOperaciones() {
//		return this.tipoOperacionService.findTiposOperaciones();
		List<TipoOperacion> tipoOperacion = new ArrayList<TipoOperacion>();
		Collection<CitaOperacion> citasOperaciones = (Collection<CitaOperacion>) this.citaOperacionService.findAll();
		for(CitaOperacion co : citasOperaciones) {
			tipoOperacion.add(co.getTipoOperacion());
		}
		return tipoOperacion;
		
	}
	
	@GetMapping(value = "/citasOperaciones/{tipoOperacion}")
	public String listadoCitasOperacionesPorTipoDonacion(final ModelMap modelMap, @PathVariable("tipoOperacion") final String tipoOperacion) {
		Iterable<CitaOperacion> citasOperaciones = this.citaOperacionService.findCitaOperacionByTipoOperacion(tipoOperacion);
		String vista = "citasOperaciones/listadoCitasOperacionesFiltrado";
		modelMap.addAttribute("citasOperaciones", citasOperaciones);
		return vista;
	}
	
	@GetMapping(value = "/citasOperaciones/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("citaOperacion", new CitaOperacion());
		return "/citasOperaciones/findCitasOperaciones";
	}
	
	@GetMapping(value = "/citasOperaciones")
	public String processFindForm(CitaOperacion citaOperacion, BindingResult result, Map<String, Object> model) {

		String a = citaOperacion.getTipoOperacion().getName();
		if(a.contains("+")) {
			a.replace("+", " ");
		}
		citaOperacion.getTipoOperacion().setName(a);
		// allow parameterless GET request for /citasOperaciones to return all records
		if (citaOperacion.getTipoOperacion().getName() == null) {
			citaOperacion.getTipoOperacion().setName(""); // empty string signifies broadest possible search
		}

		// find Citas Operaciones by Tipo Operacon
		Collection<CitaOperacion> results = this.citaOperacionService.findCitaOperacionByTipoOperacion(citaOperacion.getTipoOperacion().getName());
		if (results.isEmpty()) {
			// no Citas operaciones found
			result.rejectValue("tipoOperacion", "notFound", "not found");
			return "citasOperaciones/findCitasOperaciones";
		}
		else {
			// multiple Citas Operaciones found
			model.put("citasOperaciones", results);
			return "citasOperaciones/listadoCitasOperacionesFiltrado";
		}
	}
	
	@GetMapping(value = "/citasOperaciones/new/{petId}")
	public String initCreationForm(final Map<String, Object> model, @PathVariable("petId") final int petId) {
		CitaOperacion citaOperacion = new CitaOperacion();
		citaOperacion.setPet(this.petService.findPetById(petId));
//		Collection<Authorities> collection = this.authoritiesService.findAll();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//		String a = collection.stream().filter(x -> x.getUsername() == username).map(x -> x.getAuthority()).findFirst().orElse(null);
		citaOperacion.setVet(this.vetService.findVetByUser(username));
		model.put("citaOperacion", citaOperacion);
		return "citasOperaciones/createOrUpdateCitaOperacionForm";
	}

	@PostMapping(value = "/citasOperaciones/new/{petId}")
	public String processCreationForm(@Valid final CitaOperacion citaOperacion, final BindingResult result, @PathVariable("petId") final int petId) {
		if (result.hasErrors()) {
			return "citasOperaciones/createOrUpdateCitaOperacionForm";
		} else {
			//creating Cita Operacion
//			Collection<Authorities> collection = this.authoritiesService.findAll();
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
//			String a = collection.stream().filter(x -> x.getUsername() == username).map(x -> x.getAuthority()).findFirst().orElse(null);
			citaOperacion.setVet(this.vetService.findVetByUser(username));
			citaOperacion.setPet(this.petService.findPetById(petId));
			citaOperacion.setPagado(false);
			this.citaOperacionService.saveCitaOperacion(citaOperacion);

			return "redirect:/citaOperacion/" + citaOperacion.getId();
		}
	}

	@GetMapping("/citaOperacion/{id}")
	public ModelAndView showCausa(@PathVariable("id") final int id) {
		ModelAndView mav = new ModelAndView("citasOperaciones/citaOperacionDetails");
		mav.addObject(this.citaOperacionService.findCitaOperacionById(id));
		return mav;
	}

	@GetMapping(value = "/citaOperacion/{citaOperacionId}/edit")
	public String initUpdateOwnerForm(@PathVariable("citaOperacionId") final int citaOperacionId, final Model model) {
		CitaOperacion citaOperacion = this.citaOperacionService.findCitaOperacionById(citaOperacionId);
		model.addAttribute(citaOperacion);
		return "citasOperaciones/createOrUpdateCitaOperacionForm";
	}

	@PostMapping(value = "/citaOperacion/{citaOperacionId}/edit")
	public String processUpdateCausaForm(@Valid CitaOperacion citaOperacion, final BindingResult result, @PathVariable("citaOperacionId") final int citaOperacionId) {
		if (result.hasErrors()) {
			return "citasOperaciones/createOrUpdateCitaOperacionForm";
		} else {
//			citaOperacion.setId(citaOperacionId);
			citaOperacion = this.citaOperacionService.findCitaOperacionById(citaOperacionId);
			this.citaOperacionService.saveCitaOperacion(citaOperacion);
			return "redirect:/citaOperacion/" + citaOperacion.getId();
		}
	}

	@GetMapping(value = "/citaOperacion/{citaOperacionId}/delete")
	public String processDeleteCausaForm(@PathVariable("citaOperacionId") final int citaOperacionId) {
		CitaOperacion citaOperacion = this.citaOperacionService.findCitaOperacionById(citaOperacionId);
		this.citaOperacionService.deleteCitaOperacion(citaOperacion);
		return "welcome";
	}
	
}
