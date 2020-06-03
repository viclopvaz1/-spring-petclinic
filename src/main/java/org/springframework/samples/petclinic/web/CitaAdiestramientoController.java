package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adiestrador;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.TipoAdiestramiento;
import org.springframework.samples.petclinic.service.AdiestradorService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CitaAdiestramientoService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TipoAdiestramientoService;
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
public class CitaAdiestramientoController {

	final AuthoritiesService authoritiesService;

	private CitaAdiestramientoService citaAdiestramientoService;

	private OwnerService ownerService;

	private PetService petService;

	private TipoAdiestramientoService tipoAdiestramientoService;

	private AdiestradorService adiestradorService;

	private CitaAdiestramientoValidator citaAdiestramientoValidator = new CitaAdiestramientoValidator();
	
	@Autowired
	public CitaAdiestramientoController(final CitaAdiestramientoService citaAdiestramientoService,
			OwnerService ownerService, TipoAdiestramientoService tipoAdiestramientoService,
			AdiestradorService adiestradorService, PetService petService, final AuthoritiesService authoritiesService) {
		this.citaAdiestramientoService = citaAdiestramientoService;
		this.ownerService = ownerService;
		this.tipoAdiestramientoService = tipoAdiestramientoService;
		this.adiestradorService = adiestradorService;
		this.petService = petService;
		this.authoritiesService = authoritiesService;

	}
	
	public Boolean error(BindingResult result) {
		return result.hasErrors();
	}

	@ModelAttribute("tipoAdiestramiento")
	public Collection<TipoAdiestramiento> populateTiposAdiestramientos() {
		return (Collection<TipoAdiestramiento>) this.tipoAdiestramientoService.findAll();
	}

	@ModelAttribute("adiestrador")
	public Collection<Adiestrador> populateAdiestradores() {
		return (Collection<Adiestrador>) this.adiestradorService.findAll();
	}

	@GetMapping(value = "/citasAdiestramiento/{ownerId}")
	public String listadoCitasAdiestramientoPorOwnerId(final ModelMap modelMap,
			@PathVariable("ownerId") final int ownerId) {
		Collection<CitaAdiestramiento> citasAdiestramiento = this.citaAdiestramientoService
				.findCitaAdiestramientoByOwnerId(ownerId);
		String vista = "citasAdiestramiento/listadoCitasAdiestramientoOwnersId";
		modelMap.addAttribute("citasAdiestramiento", citasAdiestramiento);
		return vista;
	}

	@GetMapping(value = "/citasAdiestramiento/all")
	public String listadoCitasAdiestramiento(ModelMap modelMap) {

		String vista = "citasAdiestramiento/listadoCitasAdiestramiento";
		Iterable<CitaAdiestramiento> citasAdiestramiento = citaAdiestramientoService.findCitaAdiestramientoAll();
		modelMap.addAttribute("citasAdiestramiento", citasAdiestramiento);
		return vista;
	}

	@GetMapping(value = "/citasAdiestramiento/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("citaAdiestramiento", new CitaAdiestramiento());
		return "/citasAdiestramiento/findCitasAdiestramiento";
	}

	@GetMapping(value = "/citasAdiestramiento")
	public String processFindForm(CitaAdiestramiento citaAdiestramiento, BindingResult result,
			Map<String, Object> model) {

		if (citaAdiestramiento.getPet().getType().getName() == null) {
			citaAdiestramiento.getPet().getType().setName("");
		}

		String tipo = citaAdiestramiento.getPet().getType().getName();

		Collection<CitaAdiestramiento> results = this.citaAdiestramientoService.findCitaAdiestramientoByPet(tipo);
		if (results.isEmpty()) {

			result.rejectValue("pet.type.name", "notFound", "not found");
			return "citasAdiestramiento/findCitasAdiestramiento";
		} else {

			model.put("citasAdiestramiento", results);
			return "citasAdiestramiento/listadoCitasAdiestramientoFiltrado";
		}
	}


	@GetMapping(value = "/citasAdiestramiento/new/{ownerId}/{petId}")
	public String initCreationForm(final Map<String, Object> model, @PathVariable("ownerId") final int ownerId,
			@PathVariable("petId") final int petId) {
		CitaAdiestramiento citaAdiestramiento = new CitaAdiestramiento();
		citaAdiestramiento.setOwner(this.ownerService.findOwnerById(ownerId));
		citaAdiestramiento.setPet(this.petService.findPetById(petId));
		String username = this.findUsername();
		citaAdiestramiento.setAdiestrador(this.adiestradorService.findAdiestradorByUser(username));
		model.put("citaAdiestramiento", citaAdiestramiento);
		return "citasAdiestramiento/createOrUpdateCitaAdiestramientoForm";
	}

	@PostMapping(value = "/citasAdiestramiento/new/{ownerId}/{petId}")
	public String processCreationForm(@Valid final CitaAdiestramiento citaAdiestramiento, final BindingResult result,
			@PathVariable("ownerId") final int ownerId, @PathVariable("petId") final int petId,
			final Map<String, Object> model) {
		if (this.error(result)) {
			return "citasAdiestramiento/createOrUpdateCitaAdiestramientoForm";
		} else {
			String mensaje = "";
			try {
				this.citaAdiestramientoValidator.validateFechas(citaAdiestramiento);
			} catch (FechasException e) {
				mensaje = "La fecha de inicio debe ser mayor a la fecha actual.";
				ObjectError errorFecha = new ObjectError("ErrorFechaInicio", "La fecha de inicio debe ser mayor a la fecha actual.");
				result.addError(errorFecha);
			}
			if (mensaje != "") {
				model.put("mensaje", mensaje);
				return "citasAdiestramiento/createOrUpdateCitaAdiestramientoForm";
			} else {
				String username = this.findUsername();
				citaAdiestramiento.setAdiestrador(this.adiestradorService.findAdiestradorByUser(username));
				citaAdiestramiento.setOwner(this.ownerService.findOwnerById(ownerId));
				citaAdiestramiento.setPet(this.petService.findPetById(petId));
				citaAdiestramiento.setPagado(false);
				this.citaAdiestramientoService.saveCitaAdiestramiento(citaAdiestramiento);

				return "redirect:/citaAdiestramiento/" + citaAdiestramiento.getId();
			}
		}
	}

	@GetMapping("/citaAdiestramiento/{id}")
	public ModelAndView showCita(@PathVariable("id") final int id) {
		ModelAndView mav = new ModelAndView("citasAdiestramiento/citaAdiestramientoDetails");
		mav.addObject(this.citaAdiestramientoService.findCitaAdiestramientoById(id));
		return mav;
	}

	@GetMapping(value = "/citaAdiestramiento/{citaAdiestramientoId}/edit/{ownerId}/{petId}")
	public String initUpdateCitaForm(@PathVariable("citaAdiestramientoId") final int citaAdiestramientoId,
			@PathVariable("ownerId") final int ownerId, @PathVariable("petId") final int petId, final Model model) {
		CitaAdiestramiento citaAdiestramiento = this.citaAdiestramientoService
				.findCitaAdiestramientoById(citaAdiestramientoId);
		model.addAttribute(citaAdiestramiento);
		return "citasAdiestramiento/createOrUpdateCitaAdiestramientoForm";
	}

	@PostMapping(value = "/citaAdiestramiento/{citaAdiestramientoId}/edit/{ownerId}/{petId}")
	public String processUpdateCitaForm(@Valid CitaAdiestramiento citaAdiestramiento, final BindingResult result,
			@PathVariable("citaAdiestramientoId") final int citaAdiestramientoId,
			@PathVariable("ownerId") final int ownerId, @PathVariable("petId") final int petId, Map<String, Object> model) {
		if (this.error(result)) {
			return "citasAdiestramiento/createOrUpdateCitaAdiestramientoForm";
		} else {
			String mensaje = "";
			try {
				this.citaAdiestramientoValidator.validateFechas(citaAdiestramiento);
			} catch (FechasException e) {
				mensaje = "La fecha de inicio debe ser mayor a la fecha actual.";
				ObjectError errorFecha = new ObjectError("ErrorFechaInicio", "La fecha de inicio debe ser mayor a la fecha actual.");
				result.addError(errorFecha);
			}
			if (mensaje != "") {
				model.put("mensaje", mensaje);
				return "citasAdiestramiento/createOrUpdateCitaAdiestramientoForm";
			} else {
				citaAdiestramiento.setId(citaAdiestramientoId);
				String username = this.findUsername();
				citaAdiestramiento.setAdiestrador(this.adiestradorService.findAdiestradorByUser(username));
				citaAdiestramiento.setPet(this.petService.findPetById(petId));
				citaAdiestramiento.setOwner(this.ownerService.findOwnerById(ownerId));
				this.citaAdiestramientoService.saveCitaAdiestramiento(citaAdiestramiento);
			}
			return "redirect:/citaAdiestramiento/" + citaAdiestramiento.getId();
		}
	}

	@GetMapping(value = "/citaAdiestramiento/{citaAdiestramientoId}/delete")
	public String processDeleteCitaForm(@PathVariable("citaAdiestramientoId") final int citaAdiestramientoId,
			ModelMap modelMap) {

		CitaAdiestramiento citaAdiestramiento = this.citaAdiestramientoService
				.findCitaAdiestramientoById(citaAdiestramientoId);
		this.citaAdiestramientoService.deleteCitaAdiestramiento(citaAdiestramiento);

		return "welcome";

	}

	@GetMapping(value = "/citaAdiestramiento/{citaAdiestramientoId}/pay")
	public String processPayCitaAdiestramientoForm(@PathVariable("citaAdiestramientoId") final int citaAdiestramientoId,
			final Map<String, Object> model) {
		CitaAdiestramiento citaAdiestramiento = this.citaAdiestramientoService
				.findCitaAdiestramientoById(citaAdiestramientoId);

		boolean noPuedePagar = false;
		try {
			this.citaAdiestramientoValidator.validateDineroMonedero(citaAdiestramiento);
		} catch (PagoException e) {
			noPuedePagar = true;
			model.put("noPuedePagar", noPuedePagar);
			model.put("pagado", citaAdiestramiento.isPagado());
			Owner owner = citaAdiestramiento.getPet().getOwner();

			Collection<CitaAdiestramiento> citasAdiestramiento = this.citaAdiestramientoService
					.findCitaAdiestramientoByOwnerId(owner.getId());
			model.put("citasAdiestramiento", citasAdiestramiento);
			return "citasAdiestramiento/listadoCitasAdiestramientoOwnersId";
		}

		Owner owner = citaAdiestramiento.getPet().getOwner();
		Integer monederoOwner = owner.getMonedero();
		Integer precio = citaAdiestramiento.getPrecio().intValue();
		owner.setMonedero(monederoOwner - precio);
		this.ownerService.saveOwner(owner);
		Adiestrador adiestrador = citaAdiestramiento.getAdiestrador();
		Integer monederoAdiestrador = adiestrador.getMonedero();

		adiestrador.setMonedero(monederoAdiestrador + precio);
		this.adiestradorService.saveAdiestrador(adiestrador);
		citaAdiestramiento.setPagado(true);
		this.citaAdiestramientoService.saveCitaAdiestramiento(citaAdiestramiento);
		model.put("pagado", citaAdiestramiento.isPagado());
		Collection<CitaAdiestramiento> citasAdiestramiento = this.citaAdiestramientoService
				.findCitaAdiestramientoByOwnerId(owner.getId());
		model.put("citasAdiestramiento", citasAdiestramiento);

		return "citasAdiestramiento/listadoCitasAdiestramientoOwnersId";
	}
	
	public String findUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
