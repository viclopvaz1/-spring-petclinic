package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Adiestrador;
import org.springframework.samples.petclinic.model.CitaAdiestramiento;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.TipoAdiestramiento;
import org.springframework.samples.petclinic.service.AdiestradorService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CitaAdiestramientoService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TipoAdiestramientoService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 */

@WebMvcTest(value = CitaAdiestramientoController.class,
includeFilters = @ComponentScan.Filter(value = TipoAdiestramientoFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
@AutoConfigureTestDatabase(replace=Replace.NONE)
class CitaAdiestramientoControllerTests {

	private static final int TEST_CITA_ADIESTRAMIENTO_ID = 1;

	private static final int TEST_PET_ID = 1;

	private static final int TEST_ADIESTRADOR_ID = 1;

	private static final int TEST_OWNER_ID = 1;

	private static final int TEST_TIPO_ADIESTRAMIENTO_ID = 1;

	@Autowired
	private CitaAdiestramientoController CitaAdiestramientoController;

	@MockBean
	private CitaAdiestramientoService clinicService;

	@MockBean
	private UserService userService;

	@MockBean
	private OwnerService ownerService;

	@MockBean
	private PetService petService;

	@MockBean
	private AdiestradorService adiestradorService;

	@MockBean
	private TipoAdiestramientoService tipoAdiestramientoService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	private CitaAdiestramiento citaAdiestramiento;

	private Pet pet;

	private Owner george;
	private Adiestrador adiestrador;
	
	
	public CitaAdiestramiento createDummyCitaAdiestramiento(final Integer id) {
		CitaAdiestramiento citaAdiestramiento = new CitaAdiestramiento();
		citaAdiestramiento.setId(id);
		return citaAdiestramiento;
	}
	

	@BeforeEach
	void setup() {

		TipoAdiestramiento to = new TipoAdiestramiento();
		to.setId(TEST_TIPO_ADIESTRAMIENTO_ID);
		to.setName("hola");

		this.adiestrador = new Adiestrador();
		adiestrador.setFirstName("Alberto");
		adiestrador.setLastName("Carter");
		adiestrador.setId(1);
		adiestrador.setMonedero(1000);
		BDDMockito.given(this.adiestradorService.findAdiestradorById(1)).willReturn(adiestrador);
		
		
		PetType cat = new PetType();
		cat.setId(TEST_PET_ID);
		cat.setName("cat");
		
		 this.pet = new Pet();
		this.pet.setType(cat);
		pet.setId(TEST_PET_ID);

		citaAdiestramiento = new CitaAdiestramiento();
		citaAdiestramiento.setDuracion(45);
		citaAdiestramiento.setId(TEST_CITA_ADIESTRAMIENTO_ID);
		citaAdiestramiento.setPrecio(75.0);
		LocalDate fechaInicio = LocalDate.parse("2020/12/11", DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		citaAdiestramiento.setFechaInicio(fechaInicio);
		LocalTime hora = LocalTime.parse("15:00");
		citaAdiestramiento.setHora(hora);
		citaAdiestramiento.setTipoAdiestramiento(to);
		citaAdiestramiento.setPagado(false);

		CitaAdiestramiento cita2 = new CitaAdiestramiento();
		cita2.setDuracion(55);
		cita2.setId(3);
		cita2.setPrecio(85.0);

		george = new Owner();
		george.setId(TEST_OWNER_ID);
		george.setFirstName("George");
		george.setLastName("Franklin");
		george.setAddress("110 W. Liberty St.");
		george.setCity("Madison");
		george.setTelephone("6085551023");
		george.setMonedero(1000);
		george.addPet(pet);
		
		given(this.ownerService.findOwnerById(TEST_OWNER_ID)).willReturn(george);

		given(this.clinicService.findCitaAdiestramientoById(TEST_CITA_ADIESTRAMIENTO_ID)).willReturn(citaAdiestramiento);

		given(this.petService.findPetTypes()).willReturn(Lists.newArrayList(cat));
		
		given(this.tipoAdiestramientoService.findAll()).willReturn(Lists.newArrayList(to));
		
		given(this.clinicService.findCitaAdiestramientoById(2)).willReturn(cita2);
		
		given(this.petService.findPetTypes()).willReturn(Lists.newArrayList(cat));

		
		given(this.clinicService.findCitaAdiestramientoByOwnerId(TEST_OWNER_ID)).willReturn(Lists.newArrayList(citaAdiestramiento));
//		Collection<CitaAdiestramiento> citasAdiestramiento = this.citaAdiestramientoService
//		.findCitaAdiestramientoByOwnerId(owner.getId());

	}

	@WithMockUser(value = "spring")
	@Test
	void testShowAllCitasOperacion() throws Exception {
		mockMvc.perform(get("/citasAdiestramiento/all")).andExpect(status().isOk())
				.andExpect(model().attributeExists("citasAdiestramiento"))
				.andExpect(view().name("citasAdiestramiento/listadoCitasAdiestramiento"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testListadoCitasAdiestramientoPorOwnerId() throws Exception {
		mockMvc.perform(get("/citasAdiestramiento/{ownerId}", TEST_OWNER_ID)).andExpect(status().isOk())
		
                .andExpect(model().attributeExists("citasAdiestramiento"))
				.andExpect(view().name("citasAdiestramiento/listadoCitasAdiestramientoOwnersId"));
	}

	@WithMockUser(value = "spring")
	@Test
	void initFindForm() throws Exception {
		this.mockMvc.perform(get("/citasAdiestramiento/find")).andExpect(status().isOk())
				.andExpect(model().attributeExists("citaAdiestramiento"))
				.andExpect(view().name("/citasAdiestramiento/findCitasAdiestramiento"));
	}

	@WithMockUser(value = "spring")
	@Test
	void processFindFormFail() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/citasAdiestramiento").param("tipoAdiestramiento", "Unknown Tipo adiestramiento"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("citasAdiestramiento"))
				.andExpect(MockMvcResultMatchers.view().name("exception"));

	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormByTipoPet() throws Exception {		
		given(this.clinicService.findCitaAdiestramientoByPet(pet.getType().getName())).willReturn(Lists.newArrayList(citaAdiestramiento));
		mockMvc.perform(get("/citasAdiestramiento").param("pet.type.name", "cat")).
		andExpect(status().isOk())
		.andExpect(view().name("citasAdiestramiento/listadoCitasAdiestramientoFiltrado"));
		
		
	}

	
	@WithMockUser(value = "spring")
	@Test
	void processFindFormByTipoPetNotFound() throws Exception {
		//given(this.clinicService.findCitaAdiestramientoByPet(pet.getType().getName())).willReturn(Lists.newArrayList(cita));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/citasAdiestramiento")
				.param("pet.type.name", "Unknown Tipo"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("citasAdiestramiento"))
		.andExpect(MockMvcResultMatchers.view().name("citasAdiestramiento/findCitasAdiestramiento"));
	}
		
		

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateCitaForm() throws Exception {
		mockMvc.perform(get("/citaAdiestramiento/{citaAdiestramientoId}/edit/{ownerId}/{petId}",
				TEST_CITA_ADIESTRAMIENTO_ID, TEST_OWNER_ID, TEST_PET_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("citaAdiestramiento"))
				.andExpect(model().attribute("citaAdiestramiento", hasProperty("duracion", is(45))))
				.andExpect(
						model().attribute("citaAdiestramiento", hasProperty("fechaInicio", is(citaAdiestramiento.getFechaInicio()))))
				.andExpect(model().attribute("citaAdiestramiento", hasProperty("hora", is(citaAdiestramiento.getHora()))))
				// .andExpect(model().attribute("citaAdiestramiento", hasProperty("pagado",
				// is(false))))
				.andExpect(model().attribute("citaAdiestramiento", hasProperty("precio", is(75.0))))
				.andExpect(model().attribute("citaAdiestramiento",
						hasProperty("tipoAdiestramiento", is(citaAdiestramiento.getTipoAdiestramiento()))))
				.andExpect(view().name("citasAdiestramiento/createOrUpdateCitaAdiestramientoForm"));

		verify(clinicService).findCitaAdiestramientoById(TEST_CITA_ADIESTRAMIENTO_ID);
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateCitaFormSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/citaAdiestramiento/{citaAdiestramientoId}/edit/{ownerId}/{petId}", TEST_CITA_ADIESTRAMIENTO_ID, TEST_OWNER_ID, TEST_PET_ID)
		       .with(SecurityMockMvcRequestPostProcessors.csrf())		
			    .param("fechaInicio", "2020/12/11")
			    .param("hora",  "15:30")
			    .param("duracion", "45")
			//	.param("pagado", is(false))))
				.param("precio", "75.0")
				.param("tipoAdiestramiento", "hola"))
		        .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/citaAdiestramiento/"+ TEST_CITA_ADIESTRAMIENTO_ID));
	
}
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/citaAdiestramiento/{citaAdiestramientoId}/edit/{ownerId}/{petId}",TEST_CITA_ADIESTRAMIENTO_ID, TEST_OWNER_ID, TEST_PET_ID)
							.with(SecurityMockMvcRequestPostProcessors.csrf())
							.param("hora", "")
							.param("duracion", ""))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeHasErrors("citaAdiestramiento"))
		.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("citaAdiestramiento", "hora"))
		.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("citaAdiestramiento", "duracion"))
		.andExpect(MockMvcResultMatchers.view().name("citasAdiestramiento/createOrUpdateCitaAdiestramientoForm"));
		
	
}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationCitaForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/citasAdiestramiento/new/{ownerId}/{petId}", TEST_OWNER_ID,
						TEST_PET_ID))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("citaAdiestramiento")).andExpect(
						MockMvcResultMatchers.view().name("citasAdiestramiento/createOrUpdateCitaAdiestramientoForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationCitaFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/citasAdiestramiento/new/{ownerId}/{petId}", CitaAdiestramientoControllerTests.TEST_OWNER_ID, TEST_PET_ID)//
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("id", "1")
				.param("fechaInicio", "2020/12/11")
				.param("tipoAdiestramiento", "hola")//
                .param("precio", "100")
				.param("hora", "15:00")
				.param("duracion", "20"))//1
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/citaAdiestramiento/"+ TEST_CITA_ADIESTRAMIENTO_ID));

	}
	@WithMockUser(value = "spring")
	 @Test
	 void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/citasAdiestramiento/new/{ownerId}/{petId}", CitaAdiestramientoControllerTests.TEST_OWNER_ID, TEST_PET_ID)
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("fechaInicio", "2020/12/11")
				.param("duracion", "30")
				.param("precio", "100")
				.param("tipoOperacion", "hola"))
		        .andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("citaAdiestramiento"))
				.andExpect(model().attributeHasFieldErrors("citaAdiestramiento", "hora"))
				.andExpect(view().name("citasAdiestramiento/createOrUpdateCitaAdiestramientoForm"));
}

	@WithMockUser(value = "spring")
	 @Test
	 void testDeleteCitaForm() throws Exception {
		
		CitaAdiestramiento citaAd = this.createDummyCitaAdiestramiento(TEST_CITA_ADIESTRAMIENTO_ID);
		
		Mockito.when(this.clinicService.findCitaAdiestramientoById(TEST_CITA_ADIESTRAMIENTO_ID)).thenReturn(citaAd);
		Mockito.doNothing().when(this.clinicService).deleteCitaAdiestramiento(citaAd);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/citaAdiestramiento/{citaAdiestramientoId}/delete", CitaAdiestramientoControllerTests.TEST_CITA_ADIESTRAMIENTO_ID)).
		andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("welcome"));
		
				
		
	}
	@WithMockUser(value = "spring")
	 @Test
	 void testDeleteCitaNotFound() throws Exception {
	
		CitaAdiestramiento citaAd = this.createDummyCitaAdiestramiento(TEST_CITA_ADIESTRAMIENTO_ID);
	
	Mockito.when(this.clinicService.findCitaAdiestramientoById(TEST_CITA_ADIESTRAMIENTO_ID)).thenReturn(citaAd);
	
	Mockito.when(this.clinicService.findCitaAdiestramientoById(200)).thenThrow(NoSuchElementException.class);
	this.mockMvc.perform(MockMvcRequestBuilders.get("/citaAdiestramiento/{citaAdiestramientoId}/delete", 200)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attribute("message", "Cita not found"))
	.andExpect(MockMvcResultMatchers.view().name("exception"));
}
	
	
	@WithMockUser(value = "spring")
	@Test
	void testPayCitaOperacionSuccess() throws Exception {

given(this.clinicService.findCitaAdiestramientoByOwnerId(TEST_OWNER_ID)).willReturn(Lists.newArrayList(citaAdiestramiento));
	
		
		this.citaAdiestramiento.setAdiestrador(adiestrador);
		this.citaAdiestramiento.setPet(pet);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/citaAdiestramiento/{citaAdiestramientoId}/pay", TEST_CITA_ADIESTRAMIENTO_ID))
		.andExpect(MockMvcResultMatchers.model().attributeExists("citasAdiestramiento"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("pagado"))
		.andExpect(MockMvcResultMatchers.view().name("citasAdiestramiento/listadoCitasAdiestramientoOwnersId"));
	}
}
	
	




	