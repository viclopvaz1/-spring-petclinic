package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.CitaOperacion;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.TipoOperacion;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CitaOperacionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TipoOperacionService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CitaOperacionController {
	
	private CitaOperacionValidator		citaOperacionValidator	= new CitaOperacionValidator();
	
	private CitaOperacionService citaOperacionService;
	
	final AuthoritiesService	authoritiesService;
	
	private PetService petService;

	private VetService vetService;
	
	private TipoOperacionService tipoOperacionService;
	
	private OwnerService ownerService;

	@Autowired
	public CitaOperacionController(final CitaOperacionService citaOperacionService, final TipoOperacionService tipoOperacionService,
			final VetService vetService, final PetService petService, final AuthoritiesService authoritiesService,
			final OwnerService ownerService) {
		this.citaOperacionService = citaOperacionService;
		this.authoritiesService = authoritiesService;
		this.petService = petService;
		this.vetService = vetService;
		this.tipoOperacionService = tipoOperacionService;
		this.ownerService = ownerService;
	}
	
	@ModelAttribute("tipoOperacion")
	public Collection<TipoOperacion> populateTiposOperaciones() {
		return (Collection<TipoOperacion>) this.tipoOperacionService.findAll();
	}
	
	@GetMapping(value = "/citasOperaciones/{tipoOperacion}")
	public String listadoCitasOperacionesPorTipoDonacion(final ModelMap modelMap, @PathVariable("tipoOperacion") final String tipoOperacion) {
		Iterable<CitaOperacion> citasOperaciones = this.citaOperacionService.findCitaOperacionByTipoOperacion(tipoOperacion);
		String vista = "citasOperaciones/listadoCitasOperacionesFiltrado";
		modelMap.addAttribute("citasOperaciones", citasOperaciones);
		return vista;
	}
	
	@GetMapping(value = "/citasOperacionesPet/{petId}")
	public String listadoCitasOperacionesPorPet(final ModelMap modelMap, @PathVariable("petId") final int petId) {
		Collection<CitaOperacion> citasOperaciones = this.citaOperacionService.findCitaOperacionByPet(petId);
		String vista = "citasOperaciones/listadoCitasOperacionesPets";
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
		boolean conjuntoVacio = false;
		
//		String a = citaOperacion.getTipoOperacion().getName();
//		if(a.contains("+")) {
//				a.replace("+", " ");
//		}
//		citaOperacion.getTipoOperacion().setName(a);
		// allow parameterless GET request for /citasOperaciones to return all records
		if (citaOperacion.getTipoOperacion().getName() == null) {
			citaOperacion.getTipoOperacion().setName(""); // empty string signifies broadest possible search
		} else {
			String a = citaOperacion.getTipoOperacion().getName();
			if(a.contains("+")) {
					a.replace("+", " ");
			}
			citaOperacion.getTipoOperacion().setName(a);
		}
		try {
			model.put("conjuntoVacio", conjuntoVacio);
			// find Citas Operaciones by Tipo Operacon
			Collection<CitaOperacion> results = (Collection<CitaOperacion>) this.citaOperacionService.findCitaOperacionByTipoOperacion(citaOperacion.getTipoOperacion().getName());
				if (results.size() == 1) {
				// 1 owner found
				citaOperacion = results.iterator().next();
				return "redirect:/citaOperacion/" + citaOperacion.getId();
			}
			else {
				// multiple Citas Operaciones found
				model.put("citasOperaciones", results);
				return "citasOperaciones/listadoCitasOperacionesFiltrado";
			}
		} catch (NoSuchElementException e) {
			conjuntoVacio = true;
			model.put("conjuntoVacio", conjuntoVacio);
			return "citasOperaciones/listadoCitasOperacionesFiltrado";
		}
	}
	
	@GetMapping(value = "/citasOperaciones/new/{petId}")
	public String initCreationForm(final Map<String, Object> model, @PathVariable("petId") final int petId) {
		CitaOperacion citaOperacion = new CitaOperacion();
		citaOperacion.setPet(this.petService.findPetById(petId));
		String username = this.findUsername();
		citaOperacion.setVet(this.vetService.findVetByUser(username));
		model.put("citaOperacion", citaOperacion);
		return "citasOperaciones/createOrUpdateCitaOperacionForm";
	}

	@PostMapping(value = "/citasOperaciones/new/{petId}")
	public String processCreationForm(@Valid final CitaOperacion citaOperacion, final BindingResult result, @PathVariable("petId") final int petId, final Map<String, Object> model) {
		if (result.hasErrors()) {
			return "citasOperaciones/createOrUpdateCitaOperacionForm";
		} else {
			String mensaje = "";
			try {
				this.citaOperacionValidator.validateFechas(citaOperacion);
			} catch (FechasException e) {
				mensaje = "La fecha de inicio debe ser mayor a la fecha actual.";
				ObjectError errorFecha = new ObjectError("ErrorFechaInicio", "La fecha de inicio debe ser mayor a la fecha actual.");
				result.addError(errorFecha);
			}
			if (mensaje != "") {
				model.put("mensaje", mensaje);
				return "citasOperaciones/createOrUpdateCitaOperacionForm";
			} else {
				//creating Cita Operacion
				String username = this.findUsername();
				citaOperacion.setVet(this.vetService.findVetByUser(username));
				citaOperacion.setPet(this.petService.findPetById(petId));
				citaOperacion.setPagado(false);
				this.citaOperacionService.saveCitaOperacion(citaOperacion);
	
				return "redirect:/citaOperacion/" + citaOperacion.getId();
			}
		}
	}

	@GetMapping("/citaOperacion/{id}")
	public ModelAndView showCitaOperacion(@PathVariable("id") final int id) {
		ModelAndView mav = new ModelAndView("citasOperaciones/citaOperacionDetails");
		mav.addObject(this.citaOperacionService.findCitaOperacionById(id).get());
		return mav;
	}

	@GetMapping(value = "/citaOperacion/{citaOperacionId}/edit/{petId}")
	public String initUpdateCitaOperacionForm(@PathVariable("citaOperacionId") final int citaOperacionId, @PathVariable("petId") final int petId, final Model model) {
		CitaOperacion citaOperacion = this.citaOperacionService.findCitaOperacionById(citaOperacionId).get();
		model.addAttribute(citaOperacion);
		return "citasOperaciones/createOrUpdateCitaOperacionForm";
	}

	@PostMapping(value = "/citaOperacion/{citaOperacionId}/edit/{petId}")
	public String processUpdateCitaOperacionForm(@Valid CitaOperacion citaOperacion, final BindingResult result, @PathVariable("citaOperacionId") final int citaOperacionId,
			@PathVariable("petId") final int petId, Map<String, Object> model) {
		if (result.hasErrors()) {
			return "citasOperaciones/createOrUpdateCitaOperacionForm";
		} else {
			String mensaje = "";
			try {
				this.citaOperacionValidator.validateFechas(citaOperacion);
			} catch (FechasException e) {
				mensaje = "La fecha de inicio debe ser mayor a la fecha actual.";
				ObjectError errorFecha = new ObjectError("ErrorFechaInicio", "La fecha de inicio debe ser mayor a la fecha actual.");
				result.addError(errorFecha);
			}
			if (mensaje != "") {
				model.put("mensaje", mensaje);
				return "citasOperaciones/createOrUpdateCitaOperacionForm";
			} else {
				citaOperacion.setId(citaOperacionId);
				String username = this.findUsername();
				citaOperacion.setVet(this.vetService.findVetByUser(username));
				citaOperacion.setPet(this.petService.findPetById(petId));
				this.citaOperacionService.saveCitaOperacion(citaOperacion);
			}
			return "redirect:/citaOperacion/" + citaOperacion.getId();
		}
	}

	@GetMapping(value = "/citaOperacion/{citaOperacionId}/delete")
	public String processDeleteCitaOperacionForm(@PathVariable("citaOperacionId") final int citaOperacionId) {
		CitaOperacion citaOperacion = this.citaOperacionService.findCitaOperacionById(citaOperacionId).get();
		this.citaOperacionService.deleteCitaOperacion(citaOperacion);
		return "welcome";
	}
	
	@GetMapping(value = "/citaOperacion/{citaOperacionId}/pay")
	public String processPayCitaOperacionForm(@PathVariable("citaOperacionId") final int citaOperacionId,
			final Map<String, Object> model) {
		CitaOperacion citaOperacion = this.citaOperacionService.findCitaOperacionById(citaOperacionId).get();
		boolean noPuedePagar = false;
		try {
			this.citaOperacionValidator.validateDineroMonedero(citaOperacion);
		} catch (PagoException e) {
			noPuedePagar = true;
			model.put("noPuedePagar", noPuedePagar);
			model.put("pagado", citaOperacion.isPagado());
			model.put("pet", citaOperacion.getPet());
			return "citasOperaciones/listadoCitasOperacionesPets";
		}
			Owner owner = citaOperacion.getPet().getOwner();
			Integer monederoOwner = owner.getMonedero();
			Integer precio = citaOperacion.getPrecio().intValue();
			owner.setMonedero(monederoOwner - precio);
			this.ownerService.saveOwner(owner);
			Vet vet = citaOperacion.getVet();
			Integer monederoVet = vet.getMonedero();
			vet.setMonedero(monederoVet + precio);
			this.vetService.saveVet(vet);
			citaOperacion.setPagado(true);
			this.citaOperacionService.saveCitaOperacion(citaOperacion);
			model.put("pagado", citaOperacion.isPagado());
			model.put("citasOperaciones", this.citaOperacionService.findCitaOperacionByPet(citaOperacion.getPet().getId()));
			model.put("noPuedePagar", noPuedePagar);
		return "citasOperaciones/listadoCitasOperacionesPets";
	}
	
	public String findUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
}
